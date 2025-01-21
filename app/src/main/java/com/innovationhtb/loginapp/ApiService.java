package com.innovationhtb.loginapp;

import com.innovationhtb.products.ProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiService {


    @POST("auth/login")
    Call<String> loginUser(@Body LoginCredentials loginCredentials);

    @GET("products/")
    Call<List<ProductModel>> getAllProducts();

    @POST("products/")
    Call<Void> createProduct(@Body ProductModel product);

}





