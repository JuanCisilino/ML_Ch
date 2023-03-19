package com.frost.ml_ch.network

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable
import com.frost.ml_ch.model.Result

interface MeliApi {

    @GET("search")
    fun search(@Query("q") search_key: String): Observable<Result>

}