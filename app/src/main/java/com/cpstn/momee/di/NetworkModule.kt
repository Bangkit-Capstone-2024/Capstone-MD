package com.cpstn.momee.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.cpstn.momee.BuildConfig
import com.cpstn.momee.network.ApiConfig
import com.cpstn.momee.network.datasource.AuthDataSource
import com.cpstn.momee.network.datasource.ProductsDataSource
import com.cpstn.momee.preference.UserPreference
import com.cpstn.momee.utils.API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideAuthInterceptor(userPreference: UserPreference): Interceptor = Interceptor { chain ->
        val user = runBlocking { userPreference.getUserSession().first() }
        val req = chain.request()
        val headers = req.newBuilder()
            .addHeader(API.AUTHORIZATION, "Bearer ${user.userToken}")
            .build()
        if (user.userToken.isNotEmpty()) {
            chain.proceed(headers)
        } else {
            chain.proceed(req)
        }
    }

    @Singleton
    @Provides
    fun provideClientInterceptor(
        authInterceptor: Interceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        return if (BuildConfig.DEBUG) {
            okHttpClient
                .addInterceptor(authInterceptor)
                .addInterceptor(ChuckerInterceptor(context))
                .build()
        } else {
            okHttpClient.build()
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(clientInterceptor: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(clientInterceptor)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Singleton
    @Provides
    fun provideAuthDataSource(retrofit: Retrofit): AuthDataSource = ApiConfig.getApiDataSource(retrofit)

    @Singleton
    @Provides
    fun provideProductsDataSource(retrofit: Retrofit): ProductsDataSource = ApiConfig.getApiDataSource(retrofit)
}