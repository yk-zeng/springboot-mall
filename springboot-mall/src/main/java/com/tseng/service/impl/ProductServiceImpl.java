package com.tseng.service.impl;

import com.tseng.dao.ProductDao;
import com.tseng.dto.ProductRequest;
import com.tseng.model.Product;
import com.tseng.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Integer insertProduct(ProductRequest productRequest) {
        return productDao.insertProduct(productRequest);
    }

//    @Override
//    public void insertList(List<ProductRequest> productRequestList) {
//
//    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
