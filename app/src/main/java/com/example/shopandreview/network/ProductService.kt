package com.example.shopandreview.network

import com.example.shopandreview.network.model.ProductResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ProductService {

    @Headers("Accept: application/json")
    @GET("product")
    suspend fun getProducts(): Response<List<ProductResponseDTO>>
}