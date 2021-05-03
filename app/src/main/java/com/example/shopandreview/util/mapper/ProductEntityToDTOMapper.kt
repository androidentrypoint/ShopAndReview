package com.example.shopandreview.util.mapper

import com.example.shopandreview.network.model.ProductResponseDTO
import com.example.shopandreview.network.model.ReviewDTO
import com.example.shopandreview.room.entity.ProductEntity
import com.example.shopandreview.room.entity.ReviewEntity
import com.example.shopandreview.room.relation.ProductWithReviewsRelation
import javax.inject.Inject

class ProductEntityToDTOMapper @Inject constructor(
    private val reviewMapper: Mapper<ReviewEntity, ReviewDTO>
) : Mapper<ProductResponseDTO, ProductWithReviewsRelation> {
    override suspend fun map(from: ProductResponseDTO): ProductWithReviewsRelation {
        return with(from) {
            ProductWithReviewsRelation(
                ProductEntity(
                    id,
                    name,
                    description,
                    currency,
                    price,
                    imgUrl
                ), reviewMapper.mapListInverse(from.reviews)
            )
        }
    }

    override suspend fun mapInverse(from: ProductWithReviewsRelation): ProductResponseDTO {
        return with(from.productEntity) {
            ProductResponseDTO(
                id,
                name,
                description,
                currency,
                price,
                imgUrl,
                reviewMapper.mapList(from.reviews)
            )
        }
    }
}