package com.b304.wang.saflightsample.utils;


import com.b304.wang.saflightsample.entity.DbLight;
import com.b304.wang.saflightsample.order.OrderEntity;

import java.util.Comparator;

/**
 * ComparatorUtils
 *
 * @author
 * @date 2018/11/28
 */
public class ComparatorUtils {
    public static class LightComparator implements Comparator<DbLight> {
        @Override
        public int compare(DbLight o1, DbLight o2) {
                return o1.getName().compareTo(o2.getName());
        }
    }

    public static class OrderEntityComparator implements Comparator<OrderEntity> {
        @Override
        public int compare(OrderEntity o1, OrderEntity o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
