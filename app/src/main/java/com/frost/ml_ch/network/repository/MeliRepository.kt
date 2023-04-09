package com.frost.ml_ch.network.repository

import com.frost.ml_ch.model.Item
import com.frost.ml_ch.network.service.MeliApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MeliRepository @Inject constructor(private val api: MeliApi) {

    suspend fun search(search: String): List<Item> {
        return withContext(Dispatchers.IO) {
            val response = api.search(search)
            response.body()?.toItemList() ?: emptyList()
        }
    }

}