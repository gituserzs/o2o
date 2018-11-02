package com.zs.o2o.dao;

import com.zs.o2o.entity.ProductCategory;
import com.zs.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Locale;

public interface ProductCategoryDao {
    List<ProductCategory> queryProductCategoryList(Long shopId);

    /**
     * 批量新增商品类别
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    int deleteProductCategory(@Param("productCategoryId")long productCategoryId,@Param("shopId")long shopId);
}
