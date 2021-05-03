package com.example.shopandreview.ui.productdetail

import androidx.lifecycle.ViewModel
import com.example.shopandreview.model.Review
import com.example.shopandreview.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ReviewRepository
) : ViewModel() {

    private val mutableReviewsFlow = MutableSharedFlow<String>(1)
    val reviewsFlow = mutableReviewsFlow.flatMapLatest {
        repository.getReviewsFlow(it)
    }

    private val reviewsPostRequest = MutableSharedFlow<Review>(1)
    val reviewsPostResult = reviewsPostRequest.flatMapLatest {
        repository.postReview(it)
    }

    fun getReviews(productId: String) {
        mutableReviewsFlow.tryEmit(productId)
    }

    fun postReview(review: Review) {
        reviewsPostRequest.tryEmit(review)
    }
}