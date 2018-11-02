package com.zs.o2o.dao;

import com.zs.o2o.BaseTest;
import com.zs.o2o.entity.Product;
import com.zs.o2o.entity.ProductCategory;
import com.zs.o2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;

    @Test
    public void testInsertProduct() {
        Product product = new Product();
        product.setProductName("手机");
        product.setProductDesc("asdsasdasd");
        product.setImgAddr("地球");
        product.setNormalPrice("12321");
        product.setPromotionPrice("121212");
        product.setPriority(123);
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setEnableStatus(1);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(9L);
        product.setProductCategory(productCategory);
        Shop shop = new Shop();
        shop.setShopId(15L);
        product.setShop(shop);
        int i = productDao.insertProduct(product);
        assertEquals(i,1);
    }

    @Test
    public void testQueryProductById(){
        Product product = productDao.queryProductById(16L);
        product.getProductImgList().stream().map(e->e.getProductImgId()).forEach(System.out::println);
    }

    @Test
    public void testUpdateProduct(){
        Product product = productDao.queryProductById(16L);
        product.setProductDesc("casdascasdawd");
        Shop shop = new Shop();
        shop.setShopId(15L);
        product.setShop(shop);
        productDao.updateProduct(product);
    }

    @Test
    public void testQueryProductList(){
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(20L);
        shop.setOwnerId(8L);
        product.setShop(shop);
        List<Product> productList = productDao.queryProductList(product);
        productList.stream().map(e->e.getProductName()).forEach(System.out::println);
    }

    @Test
    public void testUpdateProductCategoryIdToNull(){
        int i = productDao.updateProductCategoryIdToNull(27);
        System.out.println(i);
    }
}
