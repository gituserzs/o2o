package com.zs.o2o.dao;

import com.zs.o2o.entity.Product;

import java.util.List;

public interface ProductDao {
    int insertProduct(Product product);

    Product queryProductById(long productId);

    int updateProductCategoryIdToNull(long productCategoryId);

    int updateProduct(Product product);

    /**
     * 查询商品列表
     * @param productCondition 商品信息
     * @return 商品列表
     */
    List<Product> queryProductList(Product productCondition);
}
