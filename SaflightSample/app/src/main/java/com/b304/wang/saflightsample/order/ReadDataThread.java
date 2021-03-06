package com.b304.wang.saflightsample.order;

import android.os.SystemClock;
import android.util.Log;

import com.b304.wang.saflightsample.utils.AnalysisUtils;
import com.b304.wang.saflightsample.utils.AppConfig;


/**
 * @author Skx
 * @date 18/11/23
 * Description：
 */
public class ReadDataThread extends Thread {
    private boolean mIsStop = true;


    public ReadDataThread() {

    }

    @Override
    public void run() {
        while (mIsStop) {
            SystemClock.sleep(50);
            readData();
        }
    }

    public void stopRead() {
        mIsStop = false;
    }

    /**
     * 读取数据
     */
    public void readData() {
        String strData = OrderUtils.getInstance().getData();
        if (!"".equals(strData)) {
            strData += AppConfig.sData;
            Log.d("wjreadData", strData);
            AnalysisUtils.AnalysisData(strData);
        }
    }
}
