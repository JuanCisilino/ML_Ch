package com.frost.ml_ch.network.service

import com.frost.ml_ch.model.backend.BackendResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MeliApi {

    @GET("search")
    suspend fun search(@Query("q") search_key: String): Response<BackendResult>
}