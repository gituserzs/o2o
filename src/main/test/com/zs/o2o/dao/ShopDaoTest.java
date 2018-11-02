package com.zs.o2o.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zs.o2o.BaseTest;
import com.zs.o2o.entity.Area;
import com.zs.o2o.entity.PersonInfo;
import com.zs.o2o.entity.Shop;
import com.zs.o2o.entity.ShopCategory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.BeansDtdResolver;
import org.springframework.beans.factory.xml.DefaultDocumentLoader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    public void testQueryShopList(){
        Shop shopCondition = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(8L);
        shopCondition.setOwner(owner);
        // Area area = new Area();
        // area.setAreaId(6);
        // shopCondition.setArea(area);
        Page<Shop> page = PageHelper.startPage(0, 5);
        List<Shop> shopList = shopDao.queryShopList(shopCondition,0,0);
        shopList.stream().map(e->e.getShopName()).forEach(System.out::println);
        // int count = shopDao.queryShopCount(shopCondition);
        // System.out.println(count);
        System.out.println(page.getTotal());
    }

    @Test
    @Ignore
    public void testQueryByShopId(){
        long shopId = 20L;
        Shop shop = shopDao.queryByShopId(shopId);
        System.out.println(shop.getArea().getAreaName());
    }
    @Test
    @Ignore
    public void testInsertShop(){
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
        shop.setShopName("测试店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1,effectedNum);
    }

    @Test
    @Ignore
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(29l);
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1,effectedNum);
    }

    class A{
        public void a(){
            System.out.println("aaa");
        }
    }
    class B extends A{
        public void b(){
            System.out.println("bbbb");
        }
    }

    @Test
    public void test() {
        A a = new B();
        System.out.println();
    }
}
