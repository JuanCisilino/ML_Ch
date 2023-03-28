package com.frost.ml_ch.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frost.ml_ch.model.Item
import com.frost.ml_ch.network.MeliRepository
import com.frost.ml_ch.ui.utils.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val meliRepository: MeliRepository): ViewModel() {

    var loadStateLiveData = MutableLiveData<LoadState>()
    var itemLiveData = MutableLiveData<Item?>()
    var item : Item?= null
    private set

    fun setItem(selectedItem: Item) {
        loadStateLiveData.postValue(LoadState.Loading)
        item = selectedItem
    }
    fun emulateCallToGetItem(selectedItem: Item) {
        loadStateLiveData.postValue(LoadState.Success)
        itemLiveData.postValue(selectedItem)
    }
}