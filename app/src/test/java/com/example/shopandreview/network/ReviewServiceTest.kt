package com.example.shopandreview.network

import com.example.shopandreview.base.BaseServiceTest
import com.example.shopandreview.network.model.ReviewDTO
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class ReviewServiceTest : BaseServiceTest<ReviewService>(ReviewService::class.java) {

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `verify that review request was properly formed and posted correctly`() = runBlocking {
        enqueueResponse("sample-review-response.json")
        val requestObj = ReviewDTO("HI333", "en-US", 4, "I love it")
        val response = service.postReview(requestObj, requestObj.productId)
        val request = mockWebServer.takeRequest()
        Assert.assertEquals("POST", request.method)
        Assert.assertEquals("/reviews/HI333", request.path)
        Assert.assertEquals(request.body.readString(Charsets.UTF_8), Gson().toJson(requestObj))
        Assert.assertEquals(response.isSuccessful, true)

        val body = response.body()!!
        Assert.assertEquals(body.text, requestObj.text)
        Assert.assertEquals(body.rating, requestObj.rating)
    }
}