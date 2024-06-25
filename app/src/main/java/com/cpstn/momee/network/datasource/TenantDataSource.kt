package com.cpstn.momee.network.datasource

import com.cpstn.momee.network.response.SubmitTenantResponse
import com.cpstn.momee.network.response.TenantDetailResponse
import com.cpstn.momee.network.response.TenantsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface TenantDataSource {

    @Multipart
    @POST("tenants")
    suspend fun createTenant(
        @Part("name_tenants") nameTenant: RequestBody,
        @Part("address_tenants") addressTenants: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part file: MultipartBody.Part? = null,
    ): Response<SubmitTenantResponse>

    @GET("tenants/{tenant_id}")
    suspend fun getDetailTenant(
        @Path("tenant_id") tenantId: Int
    ): Response<TenantDetailResponse>

    @GET("tenants/user")
    suspend fun getTenantByUserId(): Response<TenantsResponse>

    @Multipart
    @PATCH("tenants/{tenant_id}")
    suspend fun updateTenant(
        @Path("tenant_id") tenantId: Int?,
        @Part("name_tenants") nameTenant: RequestBody,
        @Part("address_tenants") addressTenants: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part file: MultipartBody.Part? = null,
    ): Response<SubmitTenantResponse>

    @DELETE("tenants/{tenant_id}")
    suspend fun deleteTenant(
        @Path("tenant_id") tenantId: Int
    ): Response<SubmitTenantResponse>
}