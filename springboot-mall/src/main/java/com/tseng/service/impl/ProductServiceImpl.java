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

    @Override
    public List<Product> getProducts() {
        return productDao.getProducts();
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProduct(Integer productId) {
        productDao.deleteProduct(productId);
    }

    //    @Override
//    public void insertList(List<ProductRequest> productRequestList) {
//
//    }


}
