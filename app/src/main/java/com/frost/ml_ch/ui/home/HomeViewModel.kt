package com.frost.ml_ch.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frost.ml_ch.model.Item
import com.frost.ml_ch.network.MeliRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import rx.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val meliRepository: MeliRepository): ViewModel() {

    var itemsLiveData = MutableLiveData<List<Item>?>()

    fun getItems(search: String) {
        meliRepository.getItems(search)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe(
                {
                    itemsLiveData.postValue(it.results)
                },
                {
                    itemsLiveData.postValue(null)
                }
            )
    }
}