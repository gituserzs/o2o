package com.zs.o2o.dao;

import com.zs.o2o.BaseTest;
import com.zs.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testShopCategoryDao(){
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
        ShopCategory shopCategory = new ShopCategory();
        ShopCategory parentShopCategory = new ShopCategory();
        parentShopCategory.setShopCategoryId(10L);
        shopCategory.setParent(parentShopCategory);
        List<ShopCategory> shopCategoryList1 = shopCategoryDao.queryShopCategory(shopCategory);
        shopCategoryList1.stream().map(e->e.getShopCategoryName()).forEach(System.out::println);

    }
}
