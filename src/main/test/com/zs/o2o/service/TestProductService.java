package com.zs.o2o.service;

import com.zs.o2o.BaseTest;
import com.zs.o2o.dto.ProductExecution;
import com.zs.o2o.entity.Product;
import com.zs.o2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TestProductService extends BaseTest {
    @Autowired
    private ProductService productService;

    @Test
    public void testGetProductList(){
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(20L);
        shop.setOwnerId(8L);
        product.setShop(shop);
        ProductExecution productList = productService.getProductList(product,2,5);
        List<Product> productList1 = productList.getProductList();
        productList1.stream().map(e->e.getProductName()).forEach(System.out::println);
    }
}
