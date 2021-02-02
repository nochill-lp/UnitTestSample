package com.nochill_lp.unittestsample.ui

import com.nochill_lp.unittestsample.domain.ResultState

sealed class UIState<out T> {
    data class Success<out T>(val data: T): UIState<T>()
    data class Error(val exception: Exception): UIState<Nothing>()
    object Loading: UIState<Nothing>()
}

fun <T> ResultState<T>.toUIState() =
    when(this){
        is ResultState.Success -> UIState.Success(this.data)
        is ResultState.Error -> UIState.Error(this.exception)
    }