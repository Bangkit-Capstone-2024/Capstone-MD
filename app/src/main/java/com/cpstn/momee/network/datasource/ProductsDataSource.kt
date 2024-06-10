package com.cpstn.momee.network.datasource

import com.cpstn.momee.network.response.ProductCategoryResponse
import com.cpstn.momee.network.response.ProductsSearchByImageResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProductsDataSource {

    @Multipart
    @POST("products/search-by-image")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<ProductsSearchByImageResponse.Result>

    @GET("categories")
    suspend fun productCategory(): Response<ProductCategoryResponse>

}