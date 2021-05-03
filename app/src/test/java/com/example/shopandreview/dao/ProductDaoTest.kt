package com.example.shopandreview.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shopandreview.base.BaseDaoTest
import com.example.shopandreview.network.model.ProductResponseDTO
import com.example.shopandreview.room.relation.ProductWithReviewsRelation
import com.example.shopandreview.util.Util
import com.example.shopandreview.util.mapper.ProductEntityToDTOMapper
import com.example.shopandreview.util.mapper.ReviewEntityToDTOMapper
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDaoTest : BaseDaoTest() {

    @Test
    fun `verify that product with reviews insertion was successful`() = runBlocking {
        val fakeProducts = getFakeProductsWithReviews()
        db.productDao().insertProductsWithReviews(fakeProducts)
        Assert.assertEquals(fakeProducts.size, db.productDao().getAllProductsAsFlow().first().size)
    }

    @Test
    fun `verify that product deletion cascades to linked reviews`() = runBlocking {
        val fakeProducts = getFakeProductsWithReviews()
        db.productDao().insertProductsWithReviews(fakeProducts)
        db.productDao().deleteAllProducts()
        Assert.assertEquals(0, db.productDao().getAllProductsAsFlow().first().size)
        Assert.assertEquals(
            0,
            db.productDao().getReviewsAsFlow(fakeProducts.first().productEntity.id).first().size
        )
    }

    private suspend fun getFakeProductsWithReviews(): List<ProductWithReviewsRelation> {
        return ProductEntityToDTOMapper(ReviewEntityToDTOMapper()).mapList(
            Util.parseJsonFileToObject<List<ProductResponseDTO>>(
                "sample-products-response.json",
                object : TypeToken<List<ProductResponseDTO>>() {}.type
            ).orEmpty()
        )
    }
}