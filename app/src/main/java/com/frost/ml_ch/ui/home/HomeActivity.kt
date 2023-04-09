package com.frost.ml_ch.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.frost.ml_ch.R
import com.frost.ml_ch.databinding.ActivityMainBinding
import com.frost.ml_ch.extensions.hide
import com.frost.ml_ch.extensions.showAlert
import com.frost.ml_ch.extensions.showToast
import com.frost.ml_ch.model.Item
import com.frost.ml_ch.ui.detail.DetailActivity
import com.frost.ml_ch.ui.utils.ItemAdapter
import com.frost.ml_ch.ui.utils.LoadState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = ItemAdapter()
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapterAndViewModel()
        setSearchField()
        subscribeToLiveData()
    }

    private fun setSearchField(){
        binding.searchEditText.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) { checkIfFocus(view) }
        }
        binding.searchEditText.setOnEditorActionListener { view, actionId, _ -> onActionDone(actionId, view) }
    }

    private fun onActionDone(actionId: Int, view: TextView) =
        if (actionId == EditorInfo.IME_ACTION_DONE){
            checkIfFocus(view)
            true
        } else {
            false
        }

    private fun checkIfFocus(view: View) {
        if (!binding.searchEditText.text.isNullOrEmpty()) {
            val searchValue = binding.searchEditText.text
            viewModel.getItems(searchValue.toString().replace(" ", "%"))
            binding.searchEditText.text!!.clear()
            hide(view)
        }
    }

    private fun subscribeToLiveData() {
        viewModel.loadStateLiveData.observe(this) { handleLoadingState(it) }
        viewModel.itemsLiveData.observe(this) { handleItems(it) }
    }

    private fun handleLoadingState(loadState: LoadState) {
        when (loadState) {
            LoadState.Loading -> {
                binding.gridLayout.visibility = View.VISIBLE
                binding.itemsrecyclerView.visibility = View.GONE
            }
            LoadState.Success -> {
                binding.gridLayout.visibility = View.GONE
                binding.itemsrecyclerView.visibility = View.VISIBLE
            }
            else -> showAlert()
        }

    }

    private fun handleItems(itemList: List<Item>?) {
        itemList
            ?.let { adapter.updateItems(it) }
            ?:run { showToast(this, getString(R.string.error)) }
    }

    private fun setAdapterAndViewModel() {
        viewModel.onCreate()
        with(binding){
            itemsrecyclerView.layoutManager = GridLayoutManager(this@HomeActivity, 2)
            itemsrecyclerView.adapter = adapter
        }
        adapter.onItemClickCallback = { DetailActivity.start(this, it) }
    }
}