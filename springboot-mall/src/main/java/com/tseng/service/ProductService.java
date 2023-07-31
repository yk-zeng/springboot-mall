package com.tseng.service;

import com.tseng.dto.ProductRequest;
import com.tseng.model.Product;

import java.util.List;

public interface ProductService {

    Integer insertProduct(ProductRequest productRequest);
//    void insertList(List<Product> productList);
//    void delete(Integer productId);
//    void update(Product product);
    Product getProductById(Integer productId);

}
