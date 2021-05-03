package com.example.shopandreview.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val currency: String,
    val price: Double,
    val imgUrl: String
)
