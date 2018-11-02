package com.zs.o2o.enums;

import java.util.Arrays;

public enum ProductStateEnum {
    CHECK(0,"审核中"),SUCCESS(1,"操作成功"),
    PASS(2,"通过认证"),INNER_ERROR(-1001,"内部系统错误"),EMPTY(-1002,"商品为空");

    private int state;
    private String stateInfo;

    private ProductStateEnum(int state, String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static ProductStateEnum stateOf(int state){
        ProductStateEnum productStateEnum = Arrays.stream(ProductStateEnum.values())
                .filter(e -> e.state == state).findAny().orElse(null);
        return productStateEnum;
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
