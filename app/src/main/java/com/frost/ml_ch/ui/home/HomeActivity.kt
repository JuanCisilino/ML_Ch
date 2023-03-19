package com.frost.ml_ch.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.frost.ml_ch.App
import com.frost.ml_ch.R
import com.frost.ml_ch.databinding.ActivityMainBinding
import com.frost.ml_ch.extensions.showAlert
import com.frost.ml_ch.extensions.showToast
import com.frost.ml_ch.model.Item
import com.frost.ml_ch.ui.detail.DetailActivity
import com.frost.ml_ch.ui.utils.ItemAdapter
import com.frost.ml_ch.ui.utils.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemAdapter
    private val viewModel by viewModels<HomeViewModel>()
    private var loadingDialog = LoadingDialog()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        callItems(getString(R.string.hint))
        setSearchField()
        subscribeToLiveData()
    }

    private fun callItems(search: String) {
        viewModel.getItems(search)
        loadingDialog.show(supportFragmentManager)
    }

    private fun setSearchField(){
        binding.searchEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) { checkIfFocus() }
        }
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ -> onActionDone(actionId) }
    }

    private fun onActionDone(actionId: Int) =
        if (actionId == EditorInfo.IME_ACTION_DONE){
            checkIfFocus()
            true
        } else {
            false
        }

    private fun checkIfFocus() {
        if (!binding.searchEditText.text.isNullOrEmpty()) {
            val searchValue = binding.searchEditText.text
            callItems(searchValue.toString().replace(" ", "%"))
            binding.searchEditText.text!!.clear()
        }
    }

    private fun subscribeToLiveData() {
        viewModel.itemsLiveData.observe(this) { handleItems(it) }
    }

    private fun handleItems(itemList: List<Item>?) {
        loadingDialog.dismiss()
        itemList
            ?.let { setAdapter(it) }
            ?:run { showAlert() }
    }

    private fun setAdapter(itemList: List<Item>) {
        adapter = ItemAdapter(itemList)
        with(binding){
            itemsrecyclerView.layoutManager = GridLayoutManager(this@HomeActivity, 2)
            itemsrecyclerView.adapter = adapter
        }
        adapter.onItemClickCallback = { DetailActivity.start(this, it) }
    }
}