package com.tseng.controller;

import com.tseng.constant.ProductCategory;
import com.tseng.dto.ProductQueryParams;
import com.tseng.dto.ProductRequest;
import com.tseng.model.Product;
import com.tseng.service.ProductService;
import com.tseng.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@Validated
@RestController
public class ProductController {

    private final static Logger log = LoggerFactory.getLogger("ProductController.class");

    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<Product> insertProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId = productService.insertProduct(productRequest);

        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);

    }

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            //查詢條件 Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            //排序 Sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,

            //分頁 Pagination
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ) {

        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        // 取得 product List
        List<Product> productList = productService.getProducts(productQueryParams);

        // 取得 product 總數
        Integer total = productService.countProduct(productQueryParams);

        // 分頁
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) {

        Product product = productService.getProductById(productId);
        if(product != null){
            log.info("取得 product {}", productId);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest) {

        //檢查product是否存在
        Product product = productService.getProductById(productId);
        if(product == null) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        };

        //修改商品的數據
        productService.updateProduct(productId, productRequest);
        Product updatedProduct = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {

        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
