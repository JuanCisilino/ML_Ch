package com.frost.ml_ch.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.frost.ml_ch.R
import com.frost.ml_ch.databinding.ActivityDetailBinding
import com.frost.ml_ch.extensions.showAlert
import com.frost.ml_ch.extensions.showToast
import com.frost.ml_ch.model.Item
import com.frost.ml_ch.ui.utils.LoadingDialog
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()
    private var loadingDialog = LoadingDialog()

    companion object {
        const val itemKey = "item"

        fun start(activity: Activity, item: Item){
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(itemKey, item)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        callItem()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.itemLiveData.observe(this) { handleItem(it) }
    }

    private fun handleItem(item: Item?) {
        loadingDialog.dismiss()
        item
            ?.let { showItem(it) }
            ?:run { showAlert() }
    }

    private fun showItem(item: Item) {
        item.thumbnail?.let { Picasso.get().load(it).into(binding.image) }
        with(binding){
            nameTextView.text = item.title
            conditionText.text = if (item.condition == "new") "Nuevo" else "Usado"
            priceText.text = "$ ${item.price}"
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun callItem() {
        loadingDialog.show(supportFragmentManager)
        val selectedItem = getItem()
        selectedItem?.let { viewModel.emulateCallToGetItem(it) }
            ?:run { showToast(this, getString(R.string.error)) }
    }
    
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getItem() =
        if (Build.VERSION.CODENAME == Build.VERSION_CODES.TIRAMISU.toString())
            intent.getParcelableExtra(itemKey, Item::class.java)
        else intent.getParcelableExtra<Item>(itemKey)
}