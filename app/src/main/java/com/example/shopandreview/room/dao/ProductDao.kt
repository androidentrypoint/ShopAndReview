package com.example.shopandreview.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.shopandreview.room.entity.ProductEntity
import com.example.shopandreview.room.entity.ReviewEntity
import com.example.shopandreview.room.relation.ProductWithReviewsRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Transaction
    @Query("SELECT * FROM ProductEntity")
    fun getAllProductsAsFlow(): Flow<List<ProductWithReviewsRelation>>

    @Query("SELECT * FROM ReviewEntity WHERE productId = :productId")
    fun getReviewsAsFlow(productId: String): Flow<List<ReviewEntity>>

    @Insert
    suspend fun insertProducts(products: List<ProductEntity>)

    @Insert
    suspend fun insertReviews(reviews: List<ReviewEntity>)

    @Query("DELETE FROM ProductEntity")
    suspend fun deleteAllProducts()

    @Transaction
    suspend fun insertProductsWithReviews(products: List<ProductWithReviewsRelation>) {
        deleteAllProducts()
        insertProducts(products.map { it.productEntity })
        insertReviews(products.flatMap { it.reviews })
    }
}