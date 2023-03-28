package com.frost.ml_ch.ui.utils

sealed class LoadState{
    object Loading : LoadState()
    object Success : LoadState()
    data class Error(val errorMessage: String) : LoadState()
}
