package com.tseng.dao;

import com.tseng.dto.ProductRequest;
import com.tseng.model.Product;

import java.util.List;

public interface ProductDao {

    Integer insertProduct(ProductRequest productRequest);
//    void insertList(List<ProductRequest> productRequestList);
//    void delete(Integer productId);
//    void update(Product product);
    Product getProductById(Integer productId);



}
