package com.frost.ml_ch.ui.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.frost.ml_ch.databinding.ItemBinding
import com.frost.ml_ch.model.Item
import com.squareup.picasso.Picasso

class ItemAdapter(private val itemslist: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

    var onItemClickCallback : ((item: Item) -> Unit)? = null

    inner class ViewHolder(val binding: ItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val binding = ItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        with(holder){
            with(itemslist[position]) {
                val product = this
                thumbnail?.let { picassoIt(it, binding.image) }
                with(binding){
                    nameTextView.text = title
                    conditionText.text = if (condition == "new") "Nuevo" else "Usado"
                    priceText.text = "$ $price"
                    cardLayout.setOnClickListener { onItemClickCallback?.invoke(product) }
                }
            }
        }
    }

    private fun picassoIt(url: String, image: ImageView) {
        Picasso.get()
            .load(url)
            .into(image)
    }

    override fun getItemCount() = itemslist.size

}