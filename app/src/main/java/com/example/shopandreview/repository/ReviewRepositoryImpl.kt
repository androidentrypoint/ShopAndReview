package com.example.shopandreview.repository

import com.example.shopandreview.model.Review
import com.example.shopandreview.network.NetworkProcessor
import com.example.shopandreview.network.ReviewService
import com.example.shopandreview.network.model.ReviewDTO
import com.example.shopandreview.room.dao.ProductDao
import com.example.shopandreview.room.entity.ReviewEntity
import com.example.shopandreview.util.DispatcherProvider
import com.example.shopandreview.util.NetworkStatus
import com.example.shopandreview.util.mapper.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    private val reviewService: ReviewService,
    private val productDao: ProductDao,
    private val reviewMapper: Mapper<ReviewEntity, Review>,
    private val reviewDTOtoUiMapper: Mapper<Review, ReviewDTO>,
    private val reviewDTOtoEntityMapper: Mapper<ReviewEntity, ReviewDTO>,
    private val dispatcherProvider: DispatcherProvider,
    private val networkProcessor: NetworkProcessor
) : ReviewRepository, NetworkProcessor by networkProcessor,
    DispatcherProvider by dispatcherProvider {

    override fun postReview(review: Review): Flow<NetworkStatus<Review>> {
        return flow {
            emit(NetworkStatus.Loading())
            when (val response = processNetworkResponse {
                reviewService.postReview(
                    reviewDTOtoUiMapper.map(review),
                    review.productId
                )
            }) {
                is NetworkStatus.Success -> {
                    productDao.insertReviews(listOf(reviewDTOtoEntityMapper.mapInverse(response.data)))
                    emit(NetworkStatus.Success(reviewDTOtoUiMapper.mapInverse(response.data)))
                }
                is NetworkStatus.Error -> {
                    emit(NetworkStatus.Error<Review>(response.message))
                }
            }
        }.flowOn(io())
    }

    override fun getReviewsFlow(productId: String): Flow<List<Review>> {
        return productDao.getReviewsAsFlow(productId).map(reviewMapper::mapList)
    }
}