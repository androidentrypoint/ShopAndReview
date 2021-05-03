package com.example.shopandreview.network.model

data class ProductResponseDTO(
    val id: String,
    val name: String,
    val description: String,
    val currency: String,
    val price: Double,
    val imgUrl: String,
    val reviews: List<ReviewDTO>
)
