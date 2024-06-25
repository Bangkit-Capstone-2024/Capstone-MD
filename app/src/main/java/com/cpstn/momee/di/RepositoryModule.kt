package com.cpstn.momee.di

import com.cpstn.momee.network.datasource.AuthDataSource
import com.cpstn.momee.network.datasource.ChatDataSource
import com.cpstn.momee.network.datasource.ProductsDataSource
import com.cpstn.momee.network.datasource.TenantDataSource
import com.cpstn.momee.network.repository.AuthRepository
import com.cpstn.momee.network.repository.AuthRepositoryImpl
import com.cpstn.momee.network.repository.ChatRepository
import com.cpstn.momee.network.repository.ChatRepositoryImpl
import com.cpstn.momee.network.repository.ProductsRepository
import com.cpstn.momee.network.repository.ProductsRepositoryImpl
import com.cpstn.momee.network.repository.TenantRepository
import com.cpstn.momee.network.repository.TenantRepositoryImpl
import com.cpstn.momee.preference.UserPreference
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
    fun provideAuthRepository(authDataSource: AuthDataSource, userPreference: UserPreference): AuthRepository = AuthRepositoryImpl(authDataSource, userPreference)

    @Singleton
    @Provides
    fun provideProductsRepository(productsDataSource: ProductsDataSource): ProductsRepository = ProductsRepositoryImpl(productsDataSource)

    @Singleton
    @Provides
    fun provideChatRepository(chatDataSource: ChatDataSource): ChatRepository = ChatRepositoryImpl(chatDataSource)

    @Singleton
    @Provides
    fun provideTenantRepository(tenantDataSource: TenantDataSource): TenantRepository = TenantRepositoryImpl(tenantDataSource)

}