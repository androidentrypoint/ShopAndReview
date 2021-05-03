package com.example.shopandreview.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val currency: String,
    val price: Double,
    val imgUrl: String
) : Parcelable
