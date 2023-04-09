package com.frost.ml_ch.network.uc

import com.frost.ml_ch.model.Item
import com.frost.ml_ch.network.repository.MeliRepository
import javax.inject.Inject

class ItemUC @Inject constructor(private val repo: MeliRepository) {

    suspend fun search(search: String): List<Item>?{
        val list = repo.search(search)
        return if (list.isNotEmpty()) list else null
    }
}