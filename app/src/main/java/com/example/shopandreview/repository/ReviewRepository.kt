package com.example.shopandreview.repository

import com.example.shopandreview.model.Review
import com.example.shopandreview.util.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    fun postReview(review: Review): Flow<NetworkStatus<Review>>
    fun getReviewsFlow(productId: String): Flow<List<Review>>
}