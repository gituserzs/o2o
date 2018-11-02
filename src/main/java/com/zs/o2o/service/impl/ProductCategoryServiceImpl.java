package com.zs.o2o.service.impl;

import com.zs.o2o.dao.ProductCategoryDao;
import com.zs.o2o.dao.ProductDao;
import com.zs.o2o.dto.ProductCategoryExecution;
import com.zs.o2o.entity.Product;
import com.zs.o2o.entity.ProductCategory;
import com.zs.o2o.enums.ProductCategoryStateEnum;
import com.zs.o2o.exceptions.ProductCategoryOperationException;
import com.zs.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductCategory> getProductCategoryList(Long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if (productCategoryList != null && productCategoryList.size() > 0) {
            int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
            if (effectedNum > 0) {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            } else {
                throw new ProductCategoryOperationException("新增商品类别失败");
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {
        try {
            int effectNum = productDao.updateProductCategoryIdToNull(productCategoryId);
            if(effectNum<0){
                throw new ProductCategoryOperationException("商品类别更新失败");
            }
        }catch (Exception e){
            throw new ProductCategoryOperationException("删除商品类别错误："+e.getMessage());
        }
        try {
            int effectNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectNum<=0){
                throw new ProductCategoryOperationException("删除商品类别失败");
            }else{
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        }catch (Exception e){
            throw new ProductCategoryOperationException("删除商品类别错误:"+e.getMessage());
        }
    }
}
