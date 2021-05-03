package com.example.shopandreview.network

import com.example.shopandreview.base.BaseServiceTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class ProductServiceTest : BaseServiceTest<ProductService>(ProductService::class.java) {

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `verify that yearly products api response is properly parsed`() = runBlocking {
        enqueueResponse("sample-products-response.json")
        val response = service.getProducts()
        val request = mockWebServer.takeRequest()
        Assert.assertEquals("GET", request.method)
        Assert.assertEquals("/product", request.path)
        Assert.assertEquals(response.isSuccessful, true)

        val body = response.body()!!
        Assert.assertTrue(body.isNotEmpty())
        Assert.assertEquals(body.first().name, "product")
        Assert.assertEquals(body.first().reviews.first().text, "this product is the bestaaaa")
    }
}