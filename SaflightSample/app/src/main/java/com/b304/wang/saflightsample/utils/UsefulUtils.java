package com.b304.wang.saflightsample.utils;

import com.b304.wang.saflightsample.entity.Unit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class UsefulUtils {
    public static Unit deepClone(Unit in){
        try{
        //将对象写到流里
        ByteArrayOutputStream bo=new ByteArrayOutputStream();
        ObjectOutputStream oo=new ObjectOutputStream(bo);
        oo.writeObject(in);
        //从流里读出来
         ByteArrayInputStream bi=new ByteArrayInputStream(bo.toByteArray());
         ObjectInputStream oi=new ObjectInputStream(bi);
         return (Unit) oi.readObject();
        }catch (Exception e){
            e.printStackTrace();
             return (new Unit());
        }
    }
}
