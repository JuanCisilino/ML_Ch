package com.frost.ml_ch.ui.utils

sealed class LoadState{
    object Loading : LoadState()
    object Success : LoadState()
    object Error : LoadState()
}
