package com.zs.o2o.dao;

import com.zs.o2o.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {
    int batchInsertProductImg(List<ProductImg> productImgList);

    List<ProductImg> queryProductImg(long productId);

    int deleteProductImg(long productId);
}
