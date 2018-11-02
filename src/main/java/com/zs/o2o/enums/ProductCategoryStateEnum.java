package com.zs.o2o.enums;

import java.util.Arrays;

public enum ProductCategoryStateEnum {
    SUCCESS(1, "创建成功"), INNER_ERROR(-1001, "操作失败"), EMPTY_LIST(-1002, "添加数量少于1");
    private int state;
    private String stateInfo;

    private ProductCategoryStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ProductCategoryStateEnum stateOf(int index){
        ProductCategoryStateEnum productCategoryStateEnum =
                Arrays.stream(values()).filter(e -> e.state == index).findAny().orElse(null);
        return productCategoryStateEnum;
    }

}
