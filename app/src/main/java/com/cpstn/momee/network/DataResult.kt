package com.cpstn.momee.network

sealed class DataResult<out R> private constructor() {
    data class Success<out T>(val data: T?) : DataResult<T>()
    data class Error(val error: String) : DataResult<Nothing>()
    data object Loading: DataResult<Nothing>()
}