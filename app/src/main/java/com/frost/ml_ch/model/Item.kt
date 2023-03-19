package com.frost.ml_ch.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val id: String?= null,
    val title: String?= null,
    val thumbnail : String?= null,
    val price : Float? = null,
    val condition: String?= null
): Parcelable

data class Result(
    val results : List<Item> = listOf()
)