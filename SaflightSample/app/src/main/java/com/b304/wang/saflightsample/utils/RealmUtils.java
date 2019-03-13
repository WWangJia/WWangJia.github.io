package com.b304.wang.saflightsample.utils;



import android.util.Log;

import com.b304.wang.saflightsample.entity.DbDevice;
import com.b304.wang.saflightsample.entity.DbLight;
import com.b304.wang.saflightsample.entity.GroupOfUnit;
import com.b304.wang.saflightsample.entity.Light;
import com.b304.wang.saflightsample.entity.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * @author lenovo
 * @date 2018/2/2
 */

public class RealmUtils {


    /**
     * 查询协调器是否保存过
     *
     * @param realm
     * @param address
     * @param onIsHaveListener
     */
    public static void queryDevice(final Realm realm, String address, final SetOnIsHaveListener onIsHaveListener) {
        RealmResults<DbDevice> deviceRealmResults = realm.where(DbDevice.class).equalTo("mPanId", address).findAllAsync();
        deviceRealmResults.addChangeListener(new RealmChangeListener<RealmResults<DbDevice>>() {
            @Override
            public void onChange(RealmResults<DbDevice> dbDevices) {
                dbDevices.removeAllChangeListeners();
                if (dbDevices != null && dbDevices.size() > 0) {
                    DbDevice dbDevice = realm.copyFromRealm(dbDevices.get(0));
                    RealmList<DbLight> dbLights = dbDevice.getDbLights();
                    for (DbLight dbLight : dbLights) {
                        dbLight.setPower("0000");
                    }
                    AppConfig.sDbLights.clear();
                    AppConfig.sDbLights.addAll(dbDevice.getDbLights());
                    Collections.sort(AppConfig.sDbLights, new ComparatorUtils.LightComparator());
                    onIsHaveListener.onIsHaveListener(true);
                } else {
                    AppConfig.sDbLights.clear();
                    onIsHaveListener.onIsHaveListener(false);
                }
            }
        });
    }

    /**
     * 保存协调器
     *
     * @param realm
     * @param device
     */
    public static void saveDevice(Realm realm, final DbDevice device, final SetOnSaveListener onSaveListener) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                device.setId(generateNewPrimaryKey(realm, 10));
                realm.insert(device);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("saveDevice", "保存协调器成功");
                onSaveListener.onSaveListener(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.i("saveDevice", "保存协调器失败");
                onSaveListener.onSaveListener(true);
            }
        });

    }

    /**
     * 查询光标编号是否储存过
     *
     * @param realm
     * @param light
     * @param name
     * @param onIsHaveListener
     */
    public static void checkLight(Realm realm, DbLight light, String name, final SetOnIsHaveListener onIsHaveListener) {
        RealmResults<DbLight> realmResults = realm.where(DbLight.class)
                .equalTo("mName", name)
                .equalTo("mPanId", light.getPanId())
                .findAllAsync();
        realmResults.addChangeListener(new RealmChangeListener<RealmResults<DbLight>>() {
            @Override
            public void onChange(RealmResults<DbLight> dbLights) {
                dbLights.removeAllChangeListeners();
                if (dbLights != null && dbLights.size() > 0) {
                    onIsHaveListener.onIsHaveListener(true);
                } else {
                    onIsHaveListener.onIsHaveListener(false);
                }

            }
        });
    }

    /**
     * 保存光标
     *
     * @param realm
     * @param light
     * @param onSaveListener
     */
    public static void saveLight(Realm realm, final DbLight light, final SetOnSaveListener onSaveListener) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                DbDevice device = realm.where(DbDevice.class)
                        .equalTo("mPanId", light.getPanId())
                        .findFirst();
                long primaryKey = generateNewPrimaryKey(realm, 11);
                light.setId(primaryKey);
                device.getDbLights().add(light);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                onSaveListener.onSaveListener(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                onSaveListener.onSaveListener(false);

            }
        });

    }

    /**
     * 移除光标
     *
     * @param realm
     * @param light
     * @param onDeleteListener
     */
    public static void removeLight(final Realm realm, DbLight light, final SetOnDeleteListener onDeleteListener) {
        RealmResults<DbLight> dbLights = realm.where(DbLight.class)
                .equalTo("mPanId", light.getPanId())
                .equalTo("mAddress", light.getAddress())
                .equalTo("mName", light.getName()).findAllAsync();
        dbLights.addChangeListener(new RealmChangeListener<RealmResults<DbLight>>() {
            @Override
            public void onChange(RealmResults<DbLight> dbLights) {
                dbLights.removeAllChangeListeners();
                if (dbLights != null && dbLights.size() > 0) {
                    realm.beginTransaction();
                    dbLights.deleteAllFromRealm();
                    realm.commitTransaction();
                    onDeleteListener.onDeleteListener(true);
                } else {
                    onDeleteListener.onDeleteListener(false);
                }
            }
        });
    }

    /**
     * 删除协调器
     *
     * @param realm
     * @param device
     * @param onDeleteListener
     */
    public static void deleteDevice(final Realm realm, DbDevice device, final SetOnDeleteListener onDeleteListener) {
        RealmResults<DbDevice> deviceRealmResults = realm.where(DbDevice.class).equalTo("mPanId", device.getPanId()).findAllAsync();
        deviceRealmResults.addChangeListener(new RealmChangeListener<RealmResults<DbDevice>>() {
            @Override
            public void onChange(RealmResults<DbDevice> dbDevices) {
                dbDevices.removeAllChangeListeners();
                if (dbDevices != null && dbDevices.size() > 0) {
                    DbDevice dbDevice = dbDevices.get(0);
                    realm.beginTransaction();
                    dbDevice.getDbLights().deleteAllFromRealm();
                    dbDevice.deleteFromRealm();
                    realm.commitTransaction();
                    long l = generateNewPrimaryKey(realm, 11);
                    onDeleteListener.onDeleteListener(true);
                } else {
                    onDeleteListener.onDeleteListener(false);
                }
            }
        });
    }

    /**
     * 修改光标
     *
     * @param realm
     * @param light
     * @param onAlterListener
     */
    public static void alterLight(final Realm realm, DbLight light, final String name, final SetOnAlterListener onAlterListener) {
        RealmResults<DbLight> dbLights = realm.where(DbLight.class)
                .equalTo("mPanId", light.getPanId())
                .equalTo("mAddress", light.getAddress())
                .equalTo("mName", light.getName()).findAllAsync();
        dbLights.addChangeListener(new RealmChangeListener<RealmResults<DbLight>>() {
            @Override
            public void onChange(RealmResults<DbLight> dbLights) {
                dbLights.removeAllChangeListeners();
                if (dbLights != null && dbLights.size() > 0) {
                    DbLight dbLight = dbLights.get(0);
                    realm.beginTransaction();
                    dbLight.setName(name);
                    realm.commitTransaction();

                    onAlterListener.onAlterListener(true);
                } else {
                    onAlterListener.onAlterListener(false);
                }
            }
        });
    }
    /**
     * 获取最大的PrimaryKey并加一
     */
    private static long generateNewPrimaryKey(Realm realm, int type) {
        long primaryKey = 0;
        switch (type) {
            case 10:
                RealmResults<DbDevice> realmResults = realm.where(DbDevice.class).findAll().sort("id", Sort.ASCENDING);
                if (realmResults != null && realmResults.size() > 0) {
                    DbDevice last = realmResults.last();
                    primaryKey = last.getId() + 1;
                }
                break;
            case 11:
                RealmResults<DbLight> lights = realm.where(DbLight.class).findAll().sort("id", Sort.ASCENDING);
                if (lights != null && lights.size() > 0) {
                    DbLight last = lights.last();
                    primaryKey = last.getId() + 1;
                }
                break;
            case 12:
                RealmResults<Unit> unitList=realm.where(Unit.class).findAll().sort("Id",Sort.ASCENDING);
                if(unitList!=null&&unitList.size()!=0) {
                    Unit last = unitList.last();
                    primaryKey=last.getId()+1;
                }else{
                    primaryKey=1;
                }
                break;
            case 13:
                RealmResults<GroupOfUnit> groupOfUnitsList=realm.where(GroupOfUnit.class).findAll().sort("id",Sort.ASCENDING);
                if(groupOfUnitsList!=null&&groupOfUnitsList.size()!=0) {
                    GroupOfUnit last = groupOfUnitsList.last();
                    primaryKey=last.getId()+1;
                }else{
                    primaryKey=1;
                }
                break;
        }
        return primaryKey;
    }

    /**
     * 查询单元
     */
    public static ArrayList<Unit> queryUnit(Realm realm){
        RealmResults<Unit> unitResultList=realm.where(Unit.class)
                .equalTo("action",1)
                .findAll();
        return (ArrayList<Unit>) realm.copyFromRealm(unitResultList);
    }

    /**
     * 存储单元
     * @param realm
     * @param unit
     * @param onSaveListener
     */
    public static void saveUnit(Realm realm, final Unit unit, final SetOnSaveListener onSaveListener) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(unit.getId()==0) {
                    long primaryKey = generateNewPrimaryKey(realm, 12);
                    unit.setId(primaryKey);
                }
                realm.copyToRealmOrUpdate(unit);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                onSaveListener.onSaveListener(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                onSaveListener.onSaveListener(false);

            }
        });

    }

    /**
     * 删除单元
     * @param realm
     * @param unit
     */
    public static void deleteUnit(Realm realm, final Unit unit){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final RealmResults<Unit> tempUnit = realm.where(Unit.class)
                        .equalTo("Id",unit.getId())
                        .findAll();
                if(tempUnit.size()!=0)
                    tempUnit.deleteFromRealm(0);
            }
        });

    }

    /**
     * 删除单元
     * @param realm
     * @param unit
     * @param onDeleteListener
     */
    public static void deleteUnit(Realm realm, final Unit unit, final SetOnDeleteListener onDeleteListener) {
        Log.i("wj:readLight",unit.toString());
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final RealmResults<Unit> tempUnit = realm.where(Unit.class)
                        .equalTo("Id",unit.getId())
                        .findAll();
                tempUnit.deleteFromRealm(0);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
             onDeleteListener.onDeleteListener(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
              onDeleteListener.onDeleteListener(false);
            }
        });
    }

    /**
     * 存储组合单元
     * @param realm
     * @param groupOfUnit
     * @param onSaveListener
     */
    public static void saveGroupOfUnit(Realm realm, final GroupOfUnit groupOfUnit, final SetOnSaveListener onSaveListener) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if(groupOfUnit.getId()==0) {
                    long primaryKey = generateNewPrimaryKey(realm, 13);
                    groupOfUnit.setId(primaryKey);
                }
                long unitKey = generateNewPrimaryKey(realm, 12);
                for(int i=0;i<groupOfUnit.getUnitsList().size();i++){
                    if(groupOfUnit.getUnitsList().get(i).getId()==0)
                       groupOfUnit.getUnitsList().get(i).setId(++unitKey);
                }
                realm.copyToRealmOrUpdate(groupOfUnit);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                onSaveListener.onSaveListener(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                onSaveListener.onSaveListener(false);

            }
        });

    }

    /**
     * 查询组合单元
     */
    public static List<GroupOfUnit> queryGroupOfUnit(Realm realm,String trainStyle){
        RealmResults<GroupOfUnit> unitResultList=realm.where(GroupOfUnit.class)
                .equalTo("trainStyle",trainStyle)
                .findAll();
        return realm.copyFromRealm(unitResultList);
    }

    /**
     * 删除单元
     * @param realm
     * @param groupOfUnit
     * @param onDeleteListener
     */
    public static void deleteGroupOfUnit(Realm realm, final GroupOfUnit groupOfUnit, final SetOnDeleteListener onDeleteListener) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(int i=0;i<groupOfUnit.getUnitsList().size();i++){
                    deleteUnit(realm,groupOfUnit.getUnitsList().get(i));
                }
                final RealmResults<GroupOfUnit> tempUnit = realm.where(GroupOfUnit.class)
                        .equalTo("id",groupOfUnit.getId())
                        .findAll();
                tempUnit.deleteFromRealm(0);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                onDeleteListener.onDeleteListener(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                onDeleteListener.onDeleteListener(false);
            }
        });
    }

    public interface SetOnIsHaveListener {
        /**
         * 是否存在监听
         *
         * @param flag
         * @return
         */
        void onIsHaveListener(Boolean flag);
    }

    public interface SetOnAlterListener {
        /**
         * 修改监听
         *
         * @param flag
         * @return
         */
        void onAlterListener(Boolean flag);
    }

    public interface SetOnSaveListener {
        /**
         * 是否保存成功监听
         *
         * @param flag
         * @return
         */
        void onSaveListener(Boolean flag);
    }

    public interface SetOnDeleteListener {
        /**
         * 是否删除成功监听
         *
         * @param flag
         * @return
         */
        void onDeleteListener(Boolean flag);
    }
}
