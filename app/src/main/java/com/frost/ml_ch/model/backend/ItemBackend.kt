package com.frost.ml_ch.model.backend

import com.frost.ml_ch.model.Item

data class ItemBackend(
    val accepts_mercadopago: Boolean,
    val address: Address,
    val attributes: List<Any>,
    val available_quantity: Int,
    val buying_mode: String,
    val catalog_listing: Boolean,
    val catalog_product_id: String,
    val category_id: String,
    val condition: String,
    val currency_id: String,
    val discounts: Any,
    val domain_id: String,
    val id: String,
    val installments: Installments,
    val inventory_id: Any,
    val listing_type_id: String,
    val official_store_id: Any,
    val order_backend: Int,
    val original_price: Any,
    val permalink: String,
    val price: Float,
    val promotions: List<Any>,
    val sale_price: Any,
    val seller: Seller,
    val seller_address: SellerAddress,
    val shipping: Shipping,
    val site_id: String,
    val sold_quantity: Int,
    val stop_time: String,
    val tags: List<Any>,
    val thumbnail: String,
    val thumbnail_id: String,
    val title: String,
    val use_thumbnail_id: Boolean,
    val winner_item_id: Any
) {

    fun toItem() = Item(
        id = this.id,
        title = this.title,
        thumbnail = this.thumbnail,
        price = this.price,
        condition = this.condition
    )
}

data class BackendResult(
    val results : List<ItemBackend> = listOf()
) {

    fun toItemList() = this.results.map { it.toItem() }
}