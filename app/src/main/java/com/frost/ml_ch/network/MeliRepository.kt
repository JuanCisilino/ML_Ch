package com.frost.ml_ch.network

import com.frost.ml_ch.model.Result
import rx.Observable

class MeliRepository(private val api: MeliApi) {

    fun getItems(search: String): Observable<Result>{
        return api.search(search)
    }

}