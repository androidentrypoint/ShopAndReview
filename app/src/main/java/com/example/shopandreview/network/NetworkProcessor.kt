package com.example.shopandreview.network

import com.example.shopandreview.util.NetworkStatus
import retrofit2.Response

interface NetworkProcessor {

    suspend fun <R> processNetworkResponse(block: suspend () -> Response<R>): NetworkStatus<R>
}