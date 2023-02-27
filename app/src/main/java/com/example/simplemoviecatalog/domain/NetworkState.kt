package com.example.simplemoviecatalog.domain

sealed class NetworkState<out T> {
   // class Loading<out T>:NetworkState<T>()
    data class Success<out T>(val data: T): NetworkState<T>()
    data class Error(val error: Throwable): NetworkState<Nothing>()
}
