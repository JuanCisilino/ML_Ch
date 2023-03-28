package com.frost.ml_ch.ui.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.frost.ml_ch.databinding.ItemBinding
import com.frost.ml_ch.model.Item
import com.squareup.picasso.Picasso

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){

    private var itemslist = listOf<Item>()
    var onItemClickCallback : ((item: Item) -> Unit)? = null

    fun updateItems(newItems: List<Item>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = itemslist.size

            override fun getNewListSize() = newItems.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                itemslist[oldItemPosition].id == newItems[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                itemslist[oldItemPosition] == newItems[newItemPosition]

        })
        itemslist = newItems
        diffResult.dispatchUpdatesTo(this)
    }

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