package com.tseng.service;

import com.tseng.model.Product;

public interface ProductService {

//    void insert(Product product);
//    void insertList(List<Product> productList);
//    void delete(Integer productId);
//    void update(Product product);
    Product getProductById(Integer productId);

}
