package com.frost.ml_ch.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import com.frost.ml_ch.R
import com.frost.ml_ch.databinding.ActivityDetailBinding
import com.frost.ml_ch.extensions.showAlert
import com.frost.ml_ch.extensions.showToast
import com.frost.ml_ch.model.Item
import com.frost.ml_ch.ui.utils.LoadState
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

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
        setToolbar()
        callItem()
        subscribeToLiveData()
    }

    private fun setToolbar(){
        val toolbar = binding.toolbar
        toolbar.title = "Detalle"
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun subscribeToLiveData() {
        viewModel.loadStateLiveData.observe(this) { handleLoadingState(it) }
        viewModel.itemLiveData.observe(this) { handleItem(it) }
    }

    private fun handleLoadingState(loadState: LoadState) {
        when (loadState) {
            LoadState.Loading -> {
                binding.shimmerLayout.visibility = View.VISIBLE
                binding.normalLayout.visibility = View.GONE
            }
            LoadState.Success -> {
                binding.shimmerLayout.visibility = View.GONE
                binding.normalLayout.visibility = View.VISIBLE
            }
            else -> showAlert()
        }

    }

    private fun handleItem(item: Item?) {
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

    private fun callItem() {
        getItem()?.let { viewModel.setItem(it) }
        viewModel.item
            ?.let { Handler().postDelayed( { viewModel.emulateCallToGetItem(it) }, 3000) }
            ?:run { showToast(this, getString(R.string.error)) }
    }

    private fun getItem() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        intent.getSerializableExtra(itemKey, Item::class.java)
    else intent.getSerializableExtra(itemKey) as Item
}