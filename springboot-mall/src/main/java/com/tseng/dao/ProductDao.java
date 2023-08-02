package com.tseng.dao;

import com.tseng.dto.ProductQueryParams;
import com.tseng.dto.ProductRequest;
import com.tseng.model.Product;

import java.util.List;

public interface ProductDao {

    Integer insertProduct(ProductRequest productRequest);
    Integer countProduct(ProductQueryParams productQueryParams);
    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
//    void insertList(List<ProductRequest> productRequestList);
//
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProduct(Integer productId);


}
