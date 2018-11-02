package com.zs.o2o.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zs.o2o.dao.ProductDao;
import com.zs.o2o.dao.ProductImgDao;
import com.zs.o2o.dto.ProductExecution;
import com.zs.o2o.entity.Product;
import com.zs.o2o.entity.ProductImg;
import com.zs.o2o.enums.ProductStateEnum;
import com.zs.o2o.exceptions.ProductOperationException;
import com.zs.o2o.service.ProductService;
import com.zs.o2o.util.ImageUtil;
import com.zs.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Override
    @Transactional
    public ProductExecution addProduct(Product product, CommonsMultipartFile productImg, List<CommonsMultipartFile> productImgList) throws ProductOperationException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            if (productImg != null) {
                addProductImg(product, productImg);
            }
            try {
                int effectNum = productDao.insertProduct(product);
                if (effectNum <= 0) {
                    throw new ProductOperationException("商品添加失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("商品添加失败" + e.toString());
            }
            if (productImgList != null && productImgList.size() > 0) {
                addProductImgList(product, productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    private void addProductImg(Product product, CommonsMultipartFile productImg) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String productImgAddr = ImageUtil.generateThumbnail(productImg, dest);
        product.setImgAddr(productImgAddr);
    }

    private void addProductImgList(Product product, List<CommonsMultipartFile> productImgList) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList1 = new ArrayList<>();
        for (CommonsMultipartFile commonsMultipartFile : productImgList) {
            String imgAddr = ImageUtil.generateNormalThumbnail(commonsMultipartFile, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList1.add(productImg);
        }
        if (productImgList1.size() > 0) {
            try {
                int effectNum = productImgDao.batchInsertProductImg(productImgList1);
                if (effectNum <= 0) {
                    throw new ProductOperationException("创建商品详情图失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品详情图失败" + e.toString());
            }
        }
    }

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, CommonsMultipartFile productImg, List<CommonsMultipartFile> productImgList) {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setLastEditTime(new Date());
            if(productImg!=null){
                Product product1 = productDao.queryProductById(product.getProductId());
                if (product1.getImgAddr()!=null){
                    ImageUtil.deleteFileOrPath(product1.getImgAddr());
                }
                addProductImg(product,productImg );
            }
            try {
                int effectNum = productDao.updateProduct(product);
                if (effectNum <= 0) {
                    throw new ProductOperationException("商品修改失败");
                }
                if (productImgList != null && productImgList.size() > 0) {
                    deleteProductImgList(product.getProductId());
                    addProductImgList(product, productImgList);
                }
            } catch (Exception e) {
                throw new ProductOperationException("商品修改失败" + e.toString());
            }
            return new ProductExecution(ProductStateEnum.SUCCESS);
        }else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    @Override
    public ProductExecution getProductList(Product product,int pageNum,int pageSize) {
        Page<Product> page = PageHelper.startPage(pageNum,pageSize );
        List<Product> productList = productDao.queryProductList(product);
        ProductExecution productExecution = new ProductExecution();
        if(productExecution!= null){
            productExecution.setProductList(productList);
            productExecution.setState(ProductStateEnum.SUCCESS.getState());
        }else {
            productExecution.setState(ProductStateEnum.INNER_ERROR.getState());
        }
        return productExecution;
    }

    private void deleteProductImgList(Long productId) {
        List<ProductImg> productImgList = productImgDao.queryProductImg(productId);
        for (ProductImg pi: productImgList) {
            ImageUtil.deleteFileOrPath(pi.getImgAddr());
        }
        productImgDao.deleteProductImg(productId);
    }
}
