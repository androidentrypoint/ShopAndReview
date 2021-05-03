package com.example.shopandreview.repository

import com.example.shopandreview.model.ProductWithReviews
import com.example.shopandreview.util.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(): Flow<NetworkStatus<List<ProductWithReviews>>>
}