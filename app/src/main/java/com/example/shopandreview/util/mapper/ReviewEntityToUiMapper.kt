package com.example.shopandreview.util.mapper

import com.example.shopandreview.model.Review
import com.example.shopandreview.room.entity.ReviewEntity
import javax.inject.Inject

class ReviewEntityToUiMapper @Inject constructor() : Mapper<ReviewEntity, Review> {
    override suspend fun map(from: ReviewEntity): Review {
        return with(from) {
            Review(productId, locale, rating, text)
        }
    }

    override suspend fun mapInverse(from: Review): ReviewEntity {
        return with(from) {
            ReviewEntity(productId, locale, rating, text)
        }
    }
}