package com.cpstn.momee.network.repository

import com.cpstn.momee.data.domain.ProductsSearchByImageDomain
import com.cpstn.momee.data.mapper.ProductsSearchByImageMapper
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.network.datasource.ProductsDataSource
import com.cpstn.momee.network.response.ProductsSearchByImageResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import org.json.JSONObject

interface ProductsRepository {
    fun searchByImage(image: MultipartBody.Part) : Flow<DataResult<ProductsSearchByImageDomain.Result>>
}

class ProductsRepositoryImpl(private val productsDataSource: ProductsDataSource): ProductsRepository {
    override fun searchByImage(image: MultipartBody.Part) = flow {
        emit(DataResult.Loading)
        val response = productsDataSource.uploadImage(image)
        try {
            if (response.isSuccessful) {
                val mapper = ProductsSearchByImageMapper().map(response.body() ?: ProductsSearchByImageResponse.Result())
                emit(DataResult.Success(mapper))
            } else {
                val errorBody = response.errorBody()?.string().orEmpty()
                val message = JSONObject(errorBody).getString("message")
                emit(DataResult.Error(message))
            }
        } catch (e: Exception) {
            emit(DataResult.Error(e.message.orEmpty()))
        }
    }

}