package com.zs.o2o.web.shopadmin;

import com.zs.o2o.dto.ProductCategoryExecution;
import com.zs.o2o.entity.ProductCategory;
import com.zs.o2o.entity.Shop;
import com.zs.o2o.enums.ProductCategoryStateEnum;
import com.zs.o2o.exceptions.ProductCategoryOperationException;
import com.zs.o2o.service.ProductCategoryService;
import com.zs.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "/getproductcategorylist")
    @ResponseBody
    private Map<String, Object> getProductCategoryList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (currentShop != null && currentShop.getShopId() > 0) {
            List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(currentShop.getShopId());
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
        }
        return modelMap;
    }

    @RequestMapping(value = "addproductcategorys", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (productCategoryList != null && productCategoryList.size() > 0) {
            productCategoryList.stream().forEach(e -> e.setShopId(currentShop.getShopId()));
            ProductCategoryExecution productCategoryExecution = productCategoryService.batchAddProductCategory(productCategoryList);
            if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", productCategoryExecution.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个商品类别");
        }
        return modelMap;
    }

    @RequestMapping(value = "removeproductcategory", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeProductCategorys(Long productCategoryId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productCategoryId != null && productCategoryId > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCategoryExecution productCategoryExecution = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
                if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", productCategoryExecution.getStateInfo());
                }
            } catch (ProductCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "至少选择一个商品类别");
        }
        return modelMap;
    }
}
