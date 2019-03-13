package com.b304.wang.saflightsample.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.b304.wang.saflightsample.App.SaflightSampleApplication;
import com.b304.wang.saflightsample.R;
import com.b304.wang.saflightsample.adapter.GroupOfUnitAdapter;
import com.b304.wang.saflightsample.adapter.UnitAdapter3;
import com.b304.wang.saflightsample.adapter.UnitLightListAdapter;
import com.b304.wang.saflightsample.adapter.UnitLightListAdapter2;
import com.b304.wang.saflightsample.dialog.CustomTimeSetDialog;
import com.b304.wang.saflightsample.dialog.FientTimeSetDialog;
import com.b304.wang.saflightsample.dialog.SpinnerPopWindow;
import com.b304.wang.saflightsample.dialog.lightDialog;
import com.b304.wang.saflightsample.entity.GroupOfUnit;
import com.b304.wang.saflightsample.entity.Light;
import com.b304.wang.saflightsample.entity.Unit;
import com.b304.wang.saflightsample.utils.RealmUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;

import static android.widget.Toast.LENGTH_SHORT;

public class GroupShowSetActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.backName)
    TextView mBackName;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.train_name)
    TextView mTrainName;
    @BindView(R.id.drop_button)
    ImageButton mDropButton;
    @BindView(R.id.group_of_unit_list_view)
    ListView mGroupOfUnitListView;
    @BindView(R.id.add_unit)
    Button mAddUnit;
    @BindView(R.id.group_list_view)
    ListView mGroupListView;
    @BindView(R.id.light_list_view)
    ListView mLightListView;
    @BindView(R.id.exchange)
    Button mExchange;
    @BindView(R.id.group_name)
    TextView mGroupName;
    @BindView(R.id.unit_name)
    TextView mUnitName;
    @BindView(R.id.button1)
    Button mButton1;
    @BindView(R.id.button2)
    Button mButton2;
    @BindView(R.id.button3)
    Button mButton3;
    @BindView(R.id.button4)
    Button mButton4;
    @BindView(R.id.button5)
    Button mButton5;
    @BindView(R.id.button6)
    Button mButton6;
    @BindView(R.id.clear)
    Button mClear;
    @BindView(R.id.save)
    Button mSave;

    private Realm mRealm;
    //下拉列表框
    private SpinnerPopWindow mSpinnerPopWindow;
    //训练形式数据
    List<String> dataList = new ArrayList<>();
    //单元列表
    private List<GroupOfUnit> mGroupOfUnits;
    //单元组合adapter
    private GroupOfUnitAdapter mGroupOfUnitAdapter;
    //mGroupListView列表adapter
    private UnitAdapter3 mUnitAdapter3;
    //具体单元列表亮灯顺序adapter
    private UnitLightListAdapter mUnitLightListAdapter;
    //训练形势下的某个数据
    private GroupOfUnit tempGroupOfunit;
    //存储组unit
    private List<Unit> groupOfUnit;
    //当前选中的单元
    private Unit unit;
    //具体单元列表亮灯顺序list
    private List<Light> mLightList;
    //右侧设置延时弹窗
    lightDialog mLightDialog;
    //右侧设置虚晃弹窗
    FientTimeSetDialog mFientTimeSetDialog;
    //自定义时间弹窗
    private CustomTimeSetDialog mCustomTimeSetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_show_set);
        ButterKnife.bind(this);
        SaflightSampleApplication.getInstance().addActivityOrder(this);
        mRealm = Realm.getDefaultInstance();
        initData();
        initView();
        setListner();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        getData();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        //设置标题
        mTitle.setText("组合展示");
    }

    /**
     * 设置监听
     */
    private void setListner() {
        // mGroupOfUnitListView 点击监听
        mGroupOfUnitListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGroupName.setText(mGroupOfUnits.get(position).getGroupName());
                tempGroupOfunit=mGroupOfUnits.get(position);
                groupOfUnit=mGroupOfUnits.get(position).getUnitsList();
                //更换groupofunit
                mUnitName.setText("");
                if(mLightList!=null) {
                    mLightList=null;
                    mUnitLightListAdapter.clear();
                }

                mUnitAdapter3 = new UnitAdapter3(2,groupOfUnit, new UnitAdapter3.Interfacer() {
                    @Override
                    public void deleteClick(int position) {
                    RealmUtils.deleteUnit(mRealm,groupOfUnit.get(position));
                    groupOfUnit.remove(position);
                    mUnitAdapter3.notifyDataSetChanged();
                    if(mUnitLightListAdapter!=null)
                       mUnitLightListAdapter.notifyDataSetChanged();
                    Toast.makeText(GroupShowSetActivity.this,"已删除",Toast.LENGTH_SHORT).show();
                    }
                });
                mGroupListView.setAdapter(mUnitAdapter3);
            }
        });

        // mGroupListView点击监听
        mGroupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                unit = groupOfUnit.get(position);
                mUnitName.setText("单元"+(position+1));
                //右侧listview更新
                //mLightList.clear();
                mLightList=unit.getLightList();

                mUnitLightListAdapter = new UnitLightListAdapter(mLightList,2);
                mLightListView.setAdapter(mUnitLightListAdapter);
            }
        });

        //mLightListView点击监听
        mLightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mLightList.get(position).isFeint()) {  //设置为延时
                    mLightDialog = new lightDialog(GroupShowSetActivity.this, mLightList.get(position), new lightDialog.DialogOperation() {
                        @Override
                        public void sureClick() {
                            mLightDialog.dismiss();
                            mUnitLightListAdapter.notifyDataSetChanged();
                        }
                    });
                    mLightDialog.show();
                } else {//设置为虚晃
                    mFientTimeSetDialog = new FientTimeSetDialog(GroupShowSetActivity.this, mLightList.get(position), new FientTimeSetDialog.DialogOperation() {
                        @Override
                        public void sureClick() {
                            mFientTimeSetDialog.dismiss();
                            mUnitLightListAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void cancelClick() {
                            mFientTimeSetDialog.dismiss();
                        }
                    });
                    mFientTimeSetDialog.show();
                }
            }
        });

        mLightListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mLightList.remove(position);
                unit.setTimes(unit.getTimes()-1);
                mUnitAdapter3.notifyDataSetChanged();
                mUnitLightListAdapter.notifyDataSetChanged();
                Toast.makeText(GroupShowSetActivity.this,"已删除",Toast.LENGTH_SHORT).show();

                return true;
            }
        });
    }

    @OnClick({R.id.drop_button, R.id.train_name,R.id.exchange,R.id.add_unit,R.id.save,R.id.back,R.id.backName,
            R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6})
    public void OnViewClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                add_light(1);
                break;
            case R.id.button2:
                add_light(2);
                break;
            case R.id.button3:
                add_light(3);
                break;
            case R.id.button4:
                add_light(4);
                break;
            case R.id.button5:
                add_light(5);
                break;
            case R.id.button6:
                add_light(6);
                break;
            case R.id.exchange:
                Collections.shuffle(mLightList);
                mUnitLightListAdapter.notifyDataSetChanged();
                break;
            case R.id.add_unit:
                add_unit();
                break;
            case R.id.back:
            case R.id.backName:
                SaflightSampleApplication.getInstance().finish();
                break;
            case R.id.save:
                if(tempGroupOfunit==null){
                    Toast.makeText(GroupShowSetActivity.this, "无数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                RealmUtils.saveGroupOfUnit(mRealm, tempGroupOfunit, new RealmUtils.SetOnSaveListener() {
                    @Override
                    public void onSaveListener(Boolean flag) {
                        if (flag)
                            Toast.makeText(GroupShowSetActivity.this, "保存成功！", LENGTH_SHORT).show();
                        else
                            Toast.makeText(GroupShowSetActivity.this, "保存失败！", LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.drop_button:
            case R.id.train_name:
                if (mSpinnerPopWindow == null)
                    mSpinnerPopWindow = new SpinnerPopWindow();
                mSpinnerPopWindow.show(GroupShowSetActivity.this, mTrainName, dataList, new SpinnerPopWindow.onClickListener() {
                    @Override
                    public void onClick(int position) {
                        mTrainName.setText(dataList.get(position));
                        //Toast.makeText(GroupSetActivity.this,list.get(position),Toast.LENGTH_SHORT).show();
                        mGroupOfUnits = RealmUtils.queryGroupOfUnit(mRealm, dataList.get(position));

                        mGroupOfUnitAdapter = new GroupOfUnitAdapter(mGroupOfUnits, new GroupOfUnitAdapter.DeleteClick() {
                            @Override
                            public void delete(final int position) {
                                RealmUtils.deleteGroupOfUnit(mRealm, mGroupOfUnits.get(position), new RealmUtils.SetOnDeleteListener() {
                                    @Override
                                    public void onDeleteListener(Boolean flag) {
                                        if (flag) {
                                            mGroupOfUnits.remove(mGroupOfUnits.get(position));
                                            mGroupOfUnitAdapter.notifyDataSetChanged();
                                            Toast.makeText(GroupShowSetActivity.this, "删除成功！", LENGTH_SHORT).show();
                                            mGroupName.setText("");
                                        } else
                                            Toast.makeText(GroupShowSetActivity.this, "删除完成！", LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                        mGroupOfUnitListView.setAdapter(mGroupOfUnitAdapter);
                    }
                });
                break;
        }
    }

    public void getData() {
        //步骤1：创建一个SharedPreferences接口对象
        SharedPreferences read = getSharedPreferences("GroupSet", MODE_PRIVATE);
        //步骤2：获取文件中的值
        String value = read.getString("trainStyle", "");
        String[] str = value.split(",");
        for (int i = 0; i < str.length; i++) {
            dataList.add(str[i]);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SaflightSampleApplication.getInstance().finish();
    }

    /**
     * 增加unit
     */
    private void add_unit(){
        if(mTrainName.getText()==null||mTrainName.getText().toString().trim().equals("")) {
            Toast.makeText(GroupShowSetActivity.this, "请选择训练形式", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mGroupOfUnits==null||mGroupOfUnits.size()==0){
            Toast.makeText(GroupShowSetActivity.this, "无数据", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mGroupOfUnits.size()!=0&&groupOfUnit==null){
            Toast.makeText(GroupShowSetActivity.this, "请选择训练项目", Toast.LENGTH_SHORT).show();
            return;
        }
        final Unit newUnit=new Unit();
        mCustomTimeSetDialog=new CustomTimeSetDialog(GroupShowSetActivity.this, newUnit, new CustomTimeSetDialog.DialogOperation() {
            @Override
            public void sureClick() {
                mCustomTimeSetDialog.dismiss();
                groupOfUnit.add(newUnit);
                mUnitAdapter3.notifyDataSetChanged();
            }

            @Override
            public void cancelClick() {
                mCustomTimeSetDialog.dismiss();
            }
        });
        mCustomTimeSetDialog.show();

    }
    /**
     * 增加light
     */
    private void add_light(int num) {
        if(mLightList==null)
            return;
        Light light = new Light();
        light.setNum(num);
        light.setDelayTime(100);

        mLightList.add(light);
        unit.setTimes(unit.getTimes()+1);
        mUnitAdapter3.notifyDataSetChanged();
        mUnitLightListAdapter.notifyDataSetChanged();
    }

}
