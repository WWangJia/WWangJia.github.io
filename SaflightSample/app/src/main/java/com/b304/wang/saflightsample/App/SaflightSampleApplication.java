package com.b304.wang.saflightsample.App;

import android.app.Activity;
import android.app.Application;

import com.b304.wang.saflightsample.usbUtils.UsbConfig;
import com.b304.wang.saflightsample.utils.Utils;

import java.util.LinkedList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SaflightSampleApplication extends Application {
    public List<Activity> mActivityList = new LinkedList<>();
    public static SaflightSampleApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                //文件名
                .name("SafLightSample.realm")
                //版本号
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        UsbConfig.getInstance().init(this);
    }


    /**
     * 获取唯一的对象实例
     */
    public static SaflightSampleApplication getInstance() {
        if (null == instance) {
            instance = new SaflightSampleApplication();
        }
        return instance;
    }
    /**
     * 添加Activity到容器中
     */
    public void addActivityOrder(Activity activity) {
        mActivityList.add(activity);
    }

    /**
     * 遍历所有的activity并finish
     */
    public void exitOrder() {
        for (Activity activity : mActivityList) {
            activity.finish();
        }
        mActivityList.clear();
    }

    /**
     * 关闭最后一个页面
     */
    public void finish() {
        mActivityList.get(mActivityList.size() - 1).finish();
        mActivityList.remove(mActivityList.size() - 1);
    }
}
