package com.example.shopandreview.util.mapper

import com.example.shopandreview.model.Product
import com.example.shopandreview.model.ProductWithReviews
import com.example.shopandreview.model.Review
import com.example.shopandreview.room.entity.ProductEntity
import com.example.shopandreview.room.entity.ReviewEntity
import com.example.shopandreview.room.relation.ProductWithReviewsRelation
import javax.inject.Inject

class ProductEntityToUiMapper @Inject constructor(
    private val reviewMapper: Mapper<ReviewEntity, Review>
) : Mapper<ProductWithReviewsRelation, ProductWithReviews> {
    override suspend fun map(from: ProductWithReviewsRelation): ProductWithReviews {
        return with(from.productEntity) {
            ProductWithReviews(
                Product(id, name, description, currency, price, imgUrl),
                reviewMapper.mapList(from.reviews)
            )
        }
    }

    override suspend fun mapInverse(from: ProductWithReviews): ProductWithReviewsRelation {
        return with(from.product) {
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
}