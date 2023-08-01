package com.tseng.service;

import com.tseng.constant.ProductCategory;
import com.tseng.dto.ProductRequest;
import com.tseng.model.Product;

import java.util.List;

public interface ProductService {

    Integer insertProduct(ProductRequest productRequest);
    List<Product> getProducts(ProductCategory category, String search);
    Product getProductById(Integer productId);
//    void insertList(List<Product> productList);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProduct(Integer productId);

}
