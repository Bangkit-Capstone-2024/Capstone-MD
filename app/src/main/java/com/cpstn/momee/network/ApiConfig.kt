package com.cpstn.momee.network

import retrofit2.Retrofit

class ApiConfig {
    companion object {
        inline fun <reified T> getApiDataSource(retrofit: Retrofit): T = retrofit.create(T::class.java)
    }
}