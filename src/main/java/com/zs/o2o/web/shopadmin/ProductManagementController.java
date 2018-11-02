package com.zs.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zs.o2o.dto.ProductExecution;
import com.zs.o2o.entity.PersonInfo;
import com.zs.o2o.entity.Product;
import com.zs.o2o.entity.ProductCategory;
import com.zs.o2o.entity.Shop;
import com.zs.o2o.enums.ProductStateEnum;
import com.zs.o2o.service.ProductCategoryService;
import com.zs.o2o.service.ProductService;
import com.zs.o2o.util.CaptchaUtil;
import com.zs.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/productadmin")
public class ProductManagementController {
    public static final int IMAGE_MAX_COUNT = 6;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "getproductinitinfo")
    @ResponseBody
    public Map<String, Object> getInitProductInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        try {
            List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(currentShop.getShopId());
            if (productCategoryList != null && productCategoryList.size() > 0) {
                modelMap.put("productCategoryList", productCategoryList);
                modelMap.put("success", true);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CaptchaUtil.checkCaptcha(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        CommonsMultipartFile productImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        List<CommonsMultipartFile> productImgList = new ArrayList<>();
        try {
            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                for (int i = 0; i < IMAGE_MAX_COUNT; i++) {
                    productImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
                    if (productImg != null) {
                        productImgList.add(productImg);
                    } else {
                        break;
                    }
                }
                productImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg");
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "必须上传图片");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        try {
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        //若商品信息，缩略图，详情图都存在，则插入信息
        if (product != null && productImg != null && productImgList.size() > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                ProductExecution productExecution = productService.addProduct(product, productImg, productImgList);
                if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", productExecution.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    @RequestMapping(value = "/getproductbyid")
    @ResponseBody
    public Map<String, Object> getProductById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        long productId = HttpServletRequestUtil.getLong(request, "productId");
        try {
            if (productId > -1) {
                Product product = productService.getProductById(productId);
                List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(product.getShop().getShopId());
                modelMap.put("success", true);
                modelMap.put("product", product);
                modelMap.put("productCategoryList", productCategoryList);
            } else {
                modelMap.put("success", false);
                modelMap.put("essMsg", "productId不存在");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("essMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (!CaptchaUtil.checkCaptcha(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }
        ObjectMapper mapper = new ObjectMapper();
        //获取商品信息
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        Product product = null;
        try {
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        //获取图信息
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        CommonsMultipartFile productImg = null;
        List<CommonsMultipartFile> productImgList = new ArrayList<>();
        try {
            if (commonsMultipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                for (int i = 0; i < IMAGE_MAX_COUNT; i++) {
                    productImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg" + i);
                    if (productImg != null) {
                        productImgList.add(productImg);
                    }
                }
                productImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("productImg");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        if (product != null) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                ProductExecution productExecution = productService.modifyProduct(product, productImg, productImgList);
                if (productExecution.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", productExecution.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    @RequestMapping("getproductlist")
    @ResponseBody
    public Map<String, Object> getProductList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        int pageNum = HttpServletRequestUtil.getInt(request, "pageNum");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        if (user != null && user.getUserId() != null && currentShop != null && currentShop.getShopId() != null) {
            try {
                Shop shopCondition = new Shop();
                shopCondition.setOwnerId(user.getUserId());
                shopCondition.setShopId(currentShop.getShopId());
                Product productCondition = new Product();
                productCondition.setShop(shopCondition);
                ProductExecution productList = productService.getProductList(productCondition,pageNum,pageSize);
                modelMap.put("productList", productList.getProductList());
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }
        return modelMap;
    }

    @RequestMapping(value = "changeenablestatus",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changeEnableStatus(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        long productId = HttpServletRequestUtil.getLong(request, "productId");
        int enableStatus = HttpServletRequestUtil.getInt(request, "enableStatus");
        if (user != null && user.getUserId() != null && currentShop != null && currentShop.getShopId() != null) {
            try {
                Shop shopCondition = new Shop();
                shopCondition.setShopId(currentShop.getShopId());
                Product productCondition = new Product();
                productCondition.setProductId(productId);
                productCondition.setEnableStatus(enableStatus);
                productCondition.setShop(shopCondition);
                productService.modifyProduct(productCondition, null, null);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }
        return modelMap;
    }
}
