package com.tseng.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tseng.constant.ProductCategory;
import com.tseng.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();


    // 查詢商品(正常)
    @Test
    public void getProduct_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{productId}", 1);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category", equalTo("FOOD")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedDate", notNullValue()));
    }

    // 查詢商品(查無此商品)
    @Test
    public void getProduct_notFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{productId}", 10);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));

    }

    // 創建商品(正常)
    @Transactional
    @Test
    public void createProductSuccess() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductId(10);
        productRequest.setProductName("test food product");
        productRequest.setCategory(ProductCategory.FOOD.name());
        productRequest.setImageUrl("test.com");
        productRequest.setPrice(200);
        productRequest.setStock(300);
        productRequest.setDescription("This is the food for testing");

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId", equalTo(10)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName", equalTo("test food product")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category", equalTo("FOOD")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imageUrl", equalTo("test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", equalTo(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock", equalTo(300)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", equalTo("This is the food for testing")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDate", notNullValue()))
                .andExpect((MockMvcResultMatchers.jsonPath("$.lastModifiedDate", notNullValue())));
    }

    // 創建商品(前端參數短缺)
    @Transactional
    @Test
    public void createProduct_illegalArgument() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("test food product");

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));

    }

    // 更新商品
    @Transactional
    @Test
    public void updateProduct_success() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("updating");
        productRequest.setCategory(ProductCategory.CAR.name());
        productRequest.setImageUrl("/update.com");
        productRequest.setPrice(200);
        productRequest.setStock(60);

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}", 5)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                //.andExpect(status().is(200))
                .andExpect(jsonPath("$.productName", equalTo("updating")))
                .andExpect(jsonPath("$.category", equalTo("CAR")))
                .andExpect(jsonPath("$.imageUrl", equalTo("update.com")))
                .andExpect(jsonPath("$.price", equalTo(200)))
                .andExpect(jsonPath("$.stock", equalTo(60)));
    }

    //更新商品(前端參數短缺)
    @Transactional
    @Test
    public void updateProduct_illegalArgument() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("updating");

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));

    }

    // 更新商品(前端嘗試更新不存在之商品)
    @Transactional
    @Test
    public  void updateProduct_productNotFound() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("updating");
        productRequest.setCategory(ProductCategory.CAR.name());
        productRequest.setPrice(100);
        productRequest.setStock(200);

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}",2000)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }


    //刪除商品
    @Transactional
    @Test
    public void deleteProduct_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{productId}", 1);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    // 前端嘗試刪除不存在之商品
    @Transactional
    @Test
    public void deleteProduct_deleteNonExistingProduct() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{productId}", 200);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    @Test
    public void getProduct() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(5)));
    }

    @Test
    public void getProductFiltering() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("category", "CAR")
                .param("search", "B");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(2)));
    }

    @Test
    public void getProduct_sorting() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("orderBy", "price")
                .param("sort", "desc");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(5)))
                .andExpect(jsonPath("$.results[0].productId", equalTo(6)))
                .andExpect(jsonPath("$.results[1].productId", equalTo(5)))
                .andExpect(jsonPath("$.results[2].productId", equalTo(7)))
                .andExpect(jsonPath("$.results[3].productId", equalTo(4)))
                .andExpect(jsonPath("$.results[4].productId", equalTo(2)));
    }

    @Test
    public void getProducts_pagination() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("limit","2")
                .param("offset", "2");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(2)))
                .andExpect(jsonPath("$.results[0].productId", equalTo(5)))
                .andExpect(jsonPath("$.results[1].productId", equalTo(4)));
    }

}