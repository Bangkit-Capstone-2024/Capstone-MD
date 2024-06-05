package com.cpstn.momee.di

import android.content.Context
import com.cpstn.momee.preference.UserPreference
import com.cpstn.momee.preference.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun provideUserPreference(@ApplicationContext context: Context): UserPreference = UserPreference.getInstance(context.dataStore)
}