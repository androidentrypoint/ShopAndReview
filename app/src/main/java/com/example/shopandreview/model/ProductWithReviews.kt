package com.example.shopandreview.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductWithReviews(
    val product: Product,
    val reviews: List<Review>
) : Parcelable
