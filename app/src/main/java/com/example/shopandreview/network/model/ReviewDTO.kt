package com.example.shopandreview.network.model

data class ReviewDTO(
    val productId: String,
    val locale: String?,
    val rating: Int,
    val text: String
)
