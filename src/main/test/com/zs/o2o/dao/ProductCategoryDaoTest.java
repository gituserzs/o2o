package com.zs.o2o.dao;

import com.zs.o2o.BaseTest;
import com.zs.o2o.entity.ProductCategory;
import com.zs.o2o.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testBQueryProductCategoryByShopId(){
        List<ProductCategory> productCategories = productCategoryDao.queryProductCategoryList(15L);
        productCategories.stream().map(e->e.getProductCategoryName()).forEach(System.out::println);
    }

    @Test
    public void testABatchInsert(){
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setProductCategoryName("测试类别1");
        productCategory1.setProductCategoryDesc("alskdj");
        productCategory1.setPriority(80);
        productCategory1.setCreateTime(new Date());
        productCategory1.setLastEditTime(new Date());
        productCategory1.setShopId(15L);
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("测试类别2");
        productCategory2.setProductCategoryDesc("alskdj");
        productCategory2.setPriority(80);
        productCategory2.setCreateTime(new Date());
        productCategory2.setLastEditTime(new Date());
        productCategory2.setShopId(15L);
        List<ProductCategory> list = new ArrayList<>();
        list.add(productCategory1);
        list.add(productCategory2);
        int i = productCategoryDao.batchInsertProductCategory(list);
        System.out.println(i);
        list.stream().map(e->{e.setProductCategoryName("ad");return list.stream();});
    }

    @Test
    public void testCDeleteProductCategory(){
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(15L);
        for (ProductCategory p: productCategoryList) {
            if("测试类别1".equals(p.getProductCategoryName())||"测试类别2".equals(p.getProductCategoryName())){
                int i = productCategoryDao.deleteProductCategory(p.getProductCategoryId(), 15L);
                assertEquals(1,i);
            }
        }
    }
}
