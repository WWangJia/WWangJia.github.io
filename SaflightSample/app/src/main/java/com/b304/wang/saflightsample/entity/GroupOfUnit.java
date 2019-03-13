package com.b304.wang.saflightsample.entity;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import java.io.Serializable;

public class GroupOfUnit extends RealmObject implements Serializable {
    @PrimaryKey
    private long id;
    /**
     * 训练形式
     */
    private String trainStyle;
    /**
     * 组合名称
     */
    private String groupName;
    /**
     * 亮灯数量
     */
    private int light_num;
    /**
     * 组合次数
     */
    private int unit_num;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private String date;
    /**
     *单元集合
     */
    private RealmList<Unit> unitsList;


    public GroupOfUnit(){
        unitsList=new RealmList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getLight_num() {
        return light_num;
    }

    public void setLight_num(int light_num) {
        this.light_num = light_num;
    }

    public int getUnit_num() {
        return unit_num;
    }

    public void setUnit_num(int unit_num) {
        this.unit_num = unit_num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTrainStyle() {
        return trainStyle;
    }

    public void setTrainStyle(String trainStyle) {
        this.trainStyle = trainStyle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RealmList<Unit> getUnitsList() {
        return unitsList;
    }

    public void setUnitsList(RealmList<Unit> unitsList) {
        this.unitsList = unitsList;
    }


    @Override
    public String toString() {
        return "GroupOfUnit{" +
                ",id="+id+
                ",trainStyle"+trainStyle+
                ",groupName"+groupName+
                ",light_num"+light_num+
                ",unit_num"+unit_num+
                ",remark"+remark+
                ",date"+date+
                ",unitsList.size"+unitsList.size()+
                "}";
    }
}
