package com.frost.ml_ch.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frost.ml_ch.model.Item
import com.frost.ml_ch.network.uc.ItemUC
import com.frost.ml_ch.ui.utils.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val itemUC: ItemUC): ViewModel() {

    var loadStateLiveData = MutableLiveData<LoadState>()
    var itemsLiveData = MutableLiveData<List<Item>?>()

    fun onCreate() {
        viewModelScope.launch {
            loadStateLiveData.postValue(LoadState.Loading)
            val result = itemUC.search("ofertas")

            result
                ?.let {
                    loadStateLiveData.postValue(LoadState.Success)
                    itemsLiveData.postValue(it)
                }
                ?:run {
                    loadStateLiveData.postValue(LoadState.Error)
                    itemsLiveData.postValue(null) }
        }
    }

    fun getItems(search: String) {
        viewModelScope.launch {
            loadStateLiveData.postValue(LoadState.Loading)
            val result = itemUC.search(search)

            result
                ?.let {
                    loadStateLiveData.postValue(LoadState.Success)
                    itemsLiveData.postValue(it)
                }
                ?:run {
                    loadStateLiveData.postValue(LoadState.Error)
                    itemsLiveData.postValue(null) }
        }
    }
}