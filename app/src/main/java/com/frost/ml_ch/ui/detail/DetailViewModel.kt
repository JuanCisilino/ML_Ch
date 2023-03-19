package com.frost.ml_ch.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frost.ml_ch.model.Item
import com.frost.ml_ch.network.MeliRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val meliRepository: MeliRepository): ViewModel() {

    var itemLiveData = MutableLiveData<Item?>()

    fun emulateCallToGetItem(selectedItem: Item) {
        itemLiveData.postValue(selectedItem)
    }
}