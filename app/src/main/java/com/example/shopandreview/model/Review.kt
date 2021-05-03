package com.example.shopandreview.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val productId: String,
    val locale: String,
    val rating: Int,
    val text: String
) : Parcelable
