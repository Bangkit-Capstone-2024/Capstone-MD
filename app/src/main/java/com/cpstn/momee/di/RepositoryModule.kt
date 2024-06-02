package com.cpstn.momee.di

import com.cpstn.momee.network.datasource.AuthDataSource
import com.cpstn.momee.network.repository.AuthRepository
import com.cpstn.momee.network.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(authDataSource: AuthDataSource): AuthRepository = AuthRepositoryImpl(authDataSource)

}