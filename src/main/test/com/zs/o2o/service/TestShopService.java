package com.zs.o2o.service;

import com.zs.o2o.BaseTest;
import com.zs.o2o.dto.ShopExecution;
import com.zs.o2o.entity.Area;
import com.zs.o2o.entity.PersonInfo;
import com.zs.o2o.entity.Shop;
import com.zs.o2o.entity.ShopCategory;
import com.zs.o2o.enums.ShopStateEnum;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.security.acl.Owner;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TestShopService extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testGetShopList() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(8L);
        shop.setOwner(owner);
        ShopExecution shopExecution = shopService.getShopList(shop, 1, 3);
        shopExecution.getShopList().stream().map(e -> e.getShopName()).forEach(System.out::println);
        System.out.println(shopExecution.getCount());
    }

    @Ignore
    @Test
    public void testAddShop() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(11L);
        area.setAreaId(3);
        shopCategory.setShopCategoryId(11L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试店铺1");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setAdvice("审核中");
    }
}
