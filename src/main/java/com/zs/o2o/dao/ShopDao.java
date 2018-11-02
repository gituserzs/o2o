package com.zs.o2o.dao;

import com.zs.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {
    int queryShopCount(@Param("shopCondition")Shop shopCondition);
    /**
     * 根据条件查询商店列表
     * @param shopCondition
     * @param rowIndex 从第几行开始取数据
     * @param pageSize 返回的条数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,@Param("rowIndex")int rowIndex,
                             @Param("pageSize")int pageSize);
    /**
     * 根据shopId查询商店
     * @param shopId
     * @return
     */
    Shop queryByShopId(long shopId);
    /**
     * 新增商店
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新商店
     * @param shop
     * @return
     */
    int updateShop(Shop shop);
}
