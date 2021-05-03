package com.example.shopandreview.util.mapper

import com.example.shopandreview.network.model.ReviewDTO
import com.example.shopandreview.room.entity.ReviewEntity
import javax.inject.Inject

class ReviewEntityToDTOMapper @Inject constructor() : Mapper<ReviewEntity, ReviewDTO> {
    override suspend fun map(from: ReviewEntity): ReviewDTO {
        return with(from) {
            ReviewDTO(productId, locale, rating, text)
        }
    }

    override suspend fun mapInverse(from: ReviewDTO): ReviewEntity {
        return with(from) {
            ReviewEntity(productId, locale.orEmpty(), rating, text)
        }
    }
}