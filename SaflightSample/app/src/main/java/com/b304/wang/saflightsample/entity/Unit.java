package com.b304.wang.saflightsample.entity;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author wj
 * @date 2019/01/22
 */
public class Unit extends RealmObject implements Serializable {
    @PrimaryKey
    private long Id;
    /**
     * 用于表示作用
     * 0：不会展示出来
     * 1：展示作用，其他修改的基础
     */
    private int action;
    /**
     * 名称
     */
    private String name;
    /**
     * 次数
     */
    private int times;
    /**
     * 创建时间
     */
    private String date;
    /**
     * light 集合
     */
    private RealmList<Light> lightList;

    /**
     * 备注
     */
    private String remark;

    /**
     * 间歇时间  单位s
     */
    private int delayTime;
    public Unit(){
        lightList=new RealmList<>();
    }

    //此处数组并没有深拷贝
    public Unit(Unit unit){
        //this.Id=unit.getId();
        this.name=unit.getName();
        this.times=unit.getTimes();
        this.date=unit.getDate();
        lightList=new RealmList<>();
        lightList.addAll(unit.getLightList());
        this.remark=unit.getRemark();
        this.delayTime=unit.getDelayTime();
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RealmList getLightList() {
        return lightList;
    }
    public void setLightList(RealmList<Light> lightList) {
        this.lightList = lightList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public String toString() {
        return "Unit{"+
                "Id="+Id+
                ",name="+name+
                ",times="+times+
                ",date="+date+
                ",delayTime="+delayTime+
                ",remark="+remark+
                ",lightlist.size="+lightList.size()+
                "}";
    }
}
