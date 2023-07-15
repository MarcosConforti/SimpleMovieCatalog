package com.example.simplemoviecatalog.ui

sealed class UIState<out T>{
    object Loading : UIState<Nothing>()
    data class Success<out T>(val data: T): UIState<T>()
    data class Error(val error: Throwable): UIState<Nothing>()
}