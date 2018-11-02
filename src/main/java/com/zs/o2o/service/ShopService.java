package com.zs.o2o.service;

import com.zs.o2o.dto.ShopExecution;
import com.zs.o2o.entity.Shop;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;

public interface ShopService {
    /**
     * 根据shopCondition分页返回相应的店铺列表数据
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
    /**
     * 根据shopId获得shop信息
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * 更新店铺信息
     * @param shop
     * @param shopImg
     * @return
     */
    ShopExecution modifyShop(Shop shop,CommonsMultipartFile shopImg);
    /**
     * 注册店铺
     * @param shop
     * @param shopImg
     * @return
     */
    ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg);
}
