package com.example.shopandreview.util.mapper

import com.example.shopandreview.model.Review
import com.example.shopandreview.network.model.ReviewDTO
import javax.inject.Inject

class ReviewUiToDTOMapper @Inject constructor() : Mapper<Review, ReviewDTO> {
    override suspend fun map(from: Review): ReviewDTO {
        return with(from) {
            ReviewDTO(productId, locale, rating, text)
        }
    }

    override suspend fun mapInverse(from: ReviewDTO): Review {
        return with(from) {
            Review(productId, locale.orEmpty(), rating, text)
        }
    }
}