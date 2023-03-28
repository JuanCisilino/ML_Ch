package com.frost.ml_ch.model

import androidx.recyclerview.widget.DiffUtil
import java.io.Serializable

data class Item(
    val id: String?= null,
    val title: String?= null,
    val thumbnail : String?= null,
    val price : Float? = null,
    val condition: String?= null
): Serializable, DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}

data class Result(
    val results : List<Item> = listOf()
)