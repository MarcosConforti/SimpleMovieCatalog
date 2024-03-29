package com.example.simplemoviecatalog.domain

sealed class NetworkState<out T> {
    object Loading : NetworkState<Nothing>()
    data class Success<out T>(val data: T): NetworkState<T>()
    data class Error(val error: Throwable): NetworkState<Nothing>()
}
