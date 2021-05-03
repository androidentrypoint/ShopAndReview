package com.example.shopandreview.di

import android.content.Context
import com.example.shopandreview.BuildConfig
import com.example.shopandreview.model.ProductWithReviews
import com.example.shopandreview.model.Review
import com.example.shopandreview.network.NetworkProcessor
import com.example.shopandreview.network.NetworkProcessorImpl
import com.example.shopandreview.network.ProductService
import com.example.shopandreview.network.ReviewService
import com.example.shopandreview.network.model.ProductResponseDTO
import com.example.shopandreview.network.model.ReviewDTO
import com.example.shopandreview.repository.ProductRepository
import com.example.shopandreview.repository.ProductRepositoryImpl
import com.example.shopandreview.repository.ReviewRepository
import com.example.shopandreview.repository.ReviewRepositoryImpl
import com.example.shopandreview.room.SarDatabase
import com.example.shopandreview.room.entity.ReviewEntity
import com.example.shopandreview.room.relation.ProductWithReviewsRelation
import com.example.shopandreview.util.DefaultDispatcherProvider
import com.example.shopandreview.util.DispatcherProvider
import com.example.shopandreview.util.mapper.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideProductService(): ProductService {
        return Retrofit.Builder()
            .baseUrl("${BuildConfig.HOST_URL}:3001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductService::class.java)
    }

    @Singleton
    @Provides
    fun provideReviewService(): ReviewService {
        return Retrofit.Builder()
            .baseUrl("${BuildConfig.HOST_URL}:3002/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReviewService::class.java)
    }

    @Singleton
    @Provides
    fun provideLocalDb(
        @ApplicationContext context: Context,
    ): SarDatabase {
        return SarDatabase.getInstance(
            context
        )
    }

    @Singleton
    @Provides
    fun provideProductDao(db: SarDatabase) = db.productDao()

}

@Module
@InstallIn(SingletonComponent::class)
interface BindingModule {

    @get:[Binds Singleton]
    val ProductRepositoryImpl.productRepo: ProductRepository

    @get:[Binds Singleton]
    val ReviewRepositoryImpl.reviewRepository: ReviewRepository

    @get:[Binds Singleton]
    val NetworkProcessorImpl.networkProcessor: NetworkProcessor

    @get:[Binds Singleton]
    val DefaultDispatcherProvider.dispatcherProvider: DispatcherProvider

    @get:[Binds Singleton]
    val ReviewEntityToUiMapper.reviewMapper: Mapper<ReviewEntity, Review>

    @get:[Binds Singleton]
    val ReviewUiToDTOMapper.reviewDTOtoUiMapper: Mapper<Review, ReviewDTO>

    @get:[Binds Singleton]
    val ReviewEntityToDTOMapper.reviewDTOtoEntityMapper: Mapper<ReviewEntity, ReviewDTO>

    @get:[Binds Singleton]
    val ProductEntityToUiMapper.productMapper: Mapper<ProductWithReviewsRelation, ProductWithReviews>

    @get:[Binds Singleton]
    val ProductEntityToDTOMapper.productDTOMapper: Mapper<ProductResponseDTO, ProductWithReviewsRelation>

}