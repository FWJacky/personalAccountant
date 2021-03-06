package com.liu.common;

import lombok.Getter;
import lombok.ToString;

/**
 * @ClassName OrderStatus
 * @Description TODO
 * @Author L
 * @Date 2019/8/22 15:31
 * @Version 1.0
 **/

@Getter
public enum OrderStatus {

    PLAYING(1, "待支付"), OK(2, "支付完成");

    private int flag;
    private String desc;

    OrderStatus(int flag, String desc) {
        this.flag = flag;
        this.desc = desc;
    }

    public static OrderStatus valueOf(int flag) {
        for (OrderStatus orderStatus : values()) {
            if (orderStatus.getFlag() == flag) {
                return orderStatus;
            }
        }
        throw new RuntimeException("orderStatus.flag " + flag + " not found!");
    }
}
