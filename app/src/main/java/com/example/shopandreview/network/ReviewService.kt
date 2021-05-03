package com.example.shopandreview.network

import com.example.shopandreview.network.model.ReviewDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewService {

    @Headers("Accept: application/json")
    @POST("reviews/{productId}")
    suspend fun postReview(
        @Body review: ReviewDTO,
        @Path("productId") productId: String
    ): Response<ReviewDTO>
}