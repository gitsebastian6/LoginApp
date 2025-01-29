package com.innovationhtb.loginapp;

import com.innovationhtb.products.ProductModel;
import com.innovationhtb.products.ProductRequest;
import com.innovationhtb.products.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("auth/login")
    Call<String> loginUser(@Body LoginCredentials loginCredentials);

    @GET("products/")
    Call<List<Product>> getProducts();


    @POST("products/")
    Call<Void> createProduct(@Body ProductRequest productRequest);


    //@PATCH("products/")
    //Call<Void> editProduct(@Body ProductRequest productRequest);
    @DELETE("products/{id}")
    Call<Void> deleteProduct(@Path("id") String productId);


}