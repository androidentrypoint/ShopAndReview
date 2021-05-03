package com.example.shopandreview.room.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.shopandreview.room.entity.ProductEntity
import com.example.shopandreview.room.entity.ReviewEntity

data class ProductWithReviewsRelation(
    @Embedded
    val productEntity: ProductEntity,
    @Relation(parentColumn = "id", entityColumn = "productId")
    val reviews: List<ReviewEntity>
)
