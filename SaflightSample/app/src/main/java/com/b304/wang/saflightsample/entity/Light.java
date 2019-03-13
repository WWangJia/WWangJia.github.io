package com.b304.wang.saflightsample.entity;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * @author  wj
 * @date 2019/01/22
 */
public class Light extends RealmObject  {
    /**
     * 序号
     */
    private int num;
    /**
     * 是否虚晃  默认false
     */
    private boolean feint;
    /**
     * 延时时间  若虚晃，则为虚晃时间 ms
     */
    private int delayTime;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isFeint() {
        return feint;
    }

    public void setFeint(boolean feint) {
        this.feint = feint;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public String toString() {
        return "Light{"+
                "num="+num+
                ",feint="+feint+
                ",delaytime="+delayTime+
                "}";
    }
}
