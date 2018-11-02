package com.zs.o2o.dao;

import com.zs.o2o.BaseTest;
import com.zs.o2o.entity.ProductImg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ProductImgDaoTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testBatchInsertProductImg(){
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("asd");
        productImg1.setImgDesc("pic1");
        productImg1.setPriority(11);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(16L);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("adsa");
        productImg2.setImgDesc("pic2");
        productImg2.setPriority(11);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(16L);
        List<ProductImg> productImgList =  new ArrayList<>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int i = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(i,2);
    }

    @Test
    public void testQueryProductImg(){
        List<ProductImg> productImgList = productImgDao.queryProductImg(4L);
        productImgList.stream().map(e->e.getProductImgId()).forEach(System.out::println);
    }

    @Test
    public void testDeleteProductImg(){
        productImgDao.deleteProductImg(4L);
    }
}
