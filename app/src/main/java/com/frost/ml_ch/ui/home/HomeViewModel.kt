package com.frost.ml_ch.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frost.ml_ch.model.Item
import com.frost.ml_ch.network.MeliRepository
import com.frost.ml_ch.ui.utils.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import rx.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val meliRepository: MeliRepository): ViewModel() {

    var loadStateLiveData = MutableLiveData<LoadState>()
    var itemsLiveData = MutableLiveData<List<Item>?>()

    fun getItems(search: String) {
        loadStateLiveData.postValue(LoadState.Loading)
        meliRepository.getItems(search)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                {
                    loadStateLiveData.postValue(LoadState.Success)
                    itemsLiveData.postValue(it.results)
                },
                {
                    loadStateLiveData.postValue(LoadState.Error(it.message?:"Error"))
                    itemsLiveData.postValue(null)
                }
            )
    }
}