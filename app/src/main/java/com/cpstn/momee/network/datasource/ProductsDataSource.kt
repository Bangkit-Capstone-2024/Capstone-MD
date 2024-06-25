package com.cpstn.momee.network.datasource

import com.cpstn.momee.network.response.ProductCategoryResponse
import com.cpstn.momee.network.response.ProductDetailResponse
import com.cpstn.momee.network.response.ProductResponse
import com.cpstn.momee.network.response.ProductsSearchByImageResponse
import com.cpstn.momee.network.response.SubmitProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsDataSource {

    @Multipart
    @POST("products/search-by-image")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<ProductsSearchByImageResponse.Result>

    @GET("products")
    suspend fun getAllProduct(): Response<ProductResponse>

    @GET("products/search")
    suspend fun searchProduct(
        @Query("name") name: String
    ): Response<ProductResponse>

    @GET("products/{product_id}")
    suspend fun productDetail(
        @Path("product_id") productId: Int
    ): Response<ProductDetailResponse>

    @Multipart
    @POST("products")
    suspend fun createProduct(
        @Part("name_products") nameProducts: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") phone: RequestBody,
        @Part("stock") stock: RequestBody,
        @Part("is_available") isAvailable: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part("tenant_id") tenantId: RequestBody,
        @Part file: MultipartBody.Part? = null,
    ): Response<SubmitProductResponse>

    @Multipart
    @PATCH("products/{product_id}")
    suspend fun updateProduct(
        @Path("product_id") productId: Int?,
        @Part("name_products") nameProducts: RequestBody,
        @Part("description") description: RequestBody,
        @Part("price") phone: RequestBody,
        @Part("stock") stock: RequestBody,
        @Part("is_available") isAvailable: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part("tenant_id") tenantId: RequestBody,
        @Part file: MultipartBody.Part? = null,
    ): Response<SubmitProductResponse>

    @GET("categories")
    suspend fun productCategory(): Response<ProductCategoryResponse>

}