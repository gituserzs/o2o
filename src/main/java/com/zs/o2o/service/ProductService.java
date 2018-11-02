package com.zs.o2o.service;

import com.zs.o2o.dto.ProductExecution;
import com.zs.o2o.entity.Product;
import com.zs.o2o.exceptions.ProductOperationException;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface ProductService {
    ProductExecution addProduct(Product product, CommonsMultipartFile productImg, List<CommonsMultipartFile> productImgList) throws ProductOperationException;

    Product getProductById(long productId);

    /**
     * 修改商品
     * @param product 商品信息类
     * @param productImg 缩略图
     * @param productImgList 详情图列表
     * @return 商品中间类
     */
    ProductExecution modifyProduct(Product product, CommonsMultipartFile productImg, List<CommonsMultipartFile> productImgList);

    /**
     * 查询商品列表
     * @param product 商品信息类
     * @return 商品中间类
     */
    ProductExecution getProductList(Product product, int pageNum,int pageSize);
}
