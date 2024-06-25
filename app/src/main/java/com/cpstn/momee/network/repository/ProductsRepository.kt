package com.cpstn.momee.network.repository

import com.cpstn.momee.data.domain.ProductCategoryDomain
import com.cpstn.momee.data.domain.ProductDetailDomain
import com.cpstn.momee.data.domain.ProductDomain
import com.cpstn.momee.data.domain.ProductsSearchByImageDomain
import com.cpstn.momee.data.domain.SubmitProductDomain
import com.cpstn.momee.data.mapper.ProductCategoryMapper
import com.cpstn.momee.data.mapper.ProductDetailMapper
import com.cpstn.momee.data.mapper.ProductMapper
import com.cpstn.momee.data.mapper.ProductsSearchByImageMapper
import com.cpstn.momee.data.mapper.SubmitProductMapper
import com.cpstn.momee.data.payload.ProductPayload
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.network.datasource.ProductsDataSource
import com.cpstn.momee.network.response.ProductCategoryResponse
import com.cpstn.momee.network.response.ProductDetailResponse
import com.cpstn.momee.network.response.ProductResponse
import com.cpstn.momee.network.response.ProductsSearchByImageResponse
import com.cpstn.momee.network.response.SubmitProductResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import org.json.JSONObject

interface ProductsRepository {
    fun getAllProduct(): Flow<DataResult<ProductDomain>>
    fun productDetail(productId: Int): Flow<DataResult<ProductDetailDomain>>
    fun searchProduct(query: String): Flow<DataResult<ProductDomain>>
    fun searchByImage(image: MultipartBody.Part): Flow<DataResult<ProductsSearchByImageDomain.Result>>
    fun getCategoryProduct(): Flow<DataResult<ProductCategoryDomain>>
    fun submitProduct(submitProductPayload: ProductPayload): Flow<DataResult<SubmitProductDomain>>
    fun updateProduct(
        productId: Int?, submitProductPayload: ProductPayload
    ): Flow<DataResult<SubmitProductDomain>>
}

class ProductsRepositoryImpl(private val productsDataSource: ProductsDataSource) :
    ProductsRepository {
    override fun getAllProduct() = flow {
        emit(DataResult.Loading)
        val response = productsDataSource.getAllProduct()
        try {
            if (response.isSuccessful) {
                val mapper = ProductMapper().map(
                    response.body() ?: ProductResponse()
                )
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

    override fun productDetail(productId: Int) = flow {
        emit(DataResult.Loading)
        val response = productsDataSource.productDetail(productId)
        try {
            if (response.isSuccessful) {
                val mapper = ProductDetailMapper().map(
                    response.body() ?: ProductDetailResponse()
                )
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

    override fun searchProduct(query: String) = flow {
        emit(DataResult.Loading)
        val response = productsDataSource.searchProduct(query)
        try {
            if (response.isSuccessful) {
                val mapper = ProductMapper().map(response.body() ?: ProductResponse())
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

    override fun searchByImage(image: MultipartBody.Part) = flow {
        emit(DataResult.Loading)
        val response = productsDataSource.uploadImage(image)
        try {
            if (response.isSuccessful) {
                val mapper = ProductsSearchByImageMapper().map(
                    response.body() ?: ProductsSearchByImageResponse.Result()
                )
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

    override fun getCategoryProduct() = flow {
        emit(DataResult.Loading)
        val response = productsDataSource.productCategory()
        try {
            if (response.isSuccessful) {
                val mapper =
                    ProductCategoryMapper().map(response.body() ?: ProductCategoryResponse())
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

    override fun submitProduct(submitProductPayload: ProductPayload) = flow {
        emit(DataResult.Loading)
        val response = productsDataSource.createProduct(
            nameProducts = submitProductPayload.nameProducts,
            description = submitProductPayload.description,
            phone = submitProductPayload.phone,
            stock = submitProductPayload.stock,
            isAvailable = submitProductPayload.isAvailable,
            categoryId = submitProductPayload.categoryId,
            tenantId = submitProductPayload.tenantId,
            file = submitProductPayload.file
        )
        try {
            if (response.isSuccessful) {
                val mapper = SubmitProductMapper().map(response.body() ?: SubmitProductResponse())
                emit(DataResult.Success(mapper))
            } else {
                val errorBody = response.errorBody()?.string().orEmpty()
                val message = org.json.JSONObject(errorBody).getString("message")
                emit(DataResult.Error(message))
            }
        } catch (e: Exception) {
            emit(DataResult.Error(e.message.orEmpty()))
        }
    }

    override fun updateProduct(
        productId: Int?, submitProductPayload: ProductPayload
    ) = flow {
        emit(DataResult.Loading)
        val response = productsDataSource.updateProduct(
            productId = productId,
            nameProducts = submitProductPayload.nameProducts,
            description = submitProductPayload.description,
            phone = submitProductPayload.phone,
            stock = submitProductPayload.stock,
            isAvailable = submitProductPayload.isAvailable,
            categoryId = submitProductPayload.categoryId,
            tenantId = submitProductPayload.tenantId,
            file = submitProductPayload.file
        )
        try {
            if (response.isSuccessful) {
                val mapper = SubmitProductMapper().map(response.body() ?: SubmitProductResponse())
                emit(DataResult.Success(mapper))
            } else {
                val errorBody = response.errorBody()?.string().orEmpty()
                val message = org.json.JSONObject(errorBody).getString("message")
                emit(DataResult.Error(message))
            }
        } catch (e: Exception) {
            emit(DataResult.Error(e.message.orEmpty()))
        }
    }
}