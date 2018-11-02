package com.zs.o2o.enums;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum ShopStateEnum {
    CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),SUCCESS(1,"操作成功"),
    PASS(2,"通过认证"),INNER_ERROR(-1001,"内部系统错误"),NULL_SHOPID(-1002,"ShopId为空"),NULL_SHOP(-1003,"shop信息为空");

    private int state;
    private String stateInfo;

    private ShopStateEnum(int state,String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ShopStateEnum stateOf(int state){
        ShopStateEnum shopStateEnum = Arrays.stream(ShopStateEnum.values())
                .filter(e -> e.state == state).findAny().orElse(null);
        return shopStateEnum;
    }


    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
// public static void main(String[] args) {
    //     ShopStateEnum shopStateEnum = stateOf(0);
    //     System.out.println(shopStateEnum.stateInfo);
    // }
}
