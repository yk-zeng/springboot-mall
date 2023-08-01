package com.tseng.service;

import com.tseng.dto.ProductQueryParams;
import com.tseng.dto.ProductRequest;
import com.tseng.model.Product;

import java.util.List;

public interface ProductService {

    Integer insertProduct(ProductRequest productRequest);
    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
//    void insertList(List<Product> productList);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProduct(Integer productId);

}
