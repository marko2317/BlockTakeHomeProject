package com.example.directorytakehomeproject.network

sealed class NetworkResponseWrapper<out T> {

    data class Success<out T>(val response: T) : NetworkResponseWrapper<T>()
    data class Error(val message: String) : NetworkResponseWrapper<Nothing>()
}
