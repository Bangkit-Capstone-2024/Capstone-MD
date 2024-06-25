package com.cpstn.momee.network.response

import com.cpstn.momee.utils.Constant
import com.google.gson.annotations.SerializedName


data class TenantDetailResponse(
    @SerializedName("data") val `data`: Data? = Data(),
    @SerializedName("success") val success: String? = Constant.EMPTY_STRING
) {
    data class Data(
        @SerializedName("address_tenants") val addressTenants: String? = Constant.EMPTY_STRING,
        @SerializedName("id") val id: Int? = Constant.ZERO,
        @SerializedName("image") val image: String? = Constant.EMPTY_STRING,
        @SerializedName("location_lat") val locationLat: Double? = Constant.ZERO_DOUBLE,
        @SerializedName("location_lng") val locationLng: Double? = Constant.ZERO_DOUBLE,
        @SerializedName("name_tenants") val nameTenants: String? = Constant.EMPTY_STRING,
        @SerializedName("phone") val phone: String? = Constant.EMPTY_STRING,
        @SerializedName("products") val products: List<Product> = listOf(),
        @SerializedName("totalProducts") val totalProducts: Int? = Constant.ZERO,
    ) {
        data class Product(
            @SerializedName("address_tenants") val addressTenants: String? = Constant.EMPTY_STRING,
            @SerializedName("description") val description: String? = Constant.EMPTY_STRING,
            @SerializedName("id") val id: Int? = Constant.ZERO,
            @SerializedName("is_available") val isAvailable: Boolean? = false,
            @SerializedName("name_products") val nameProducts: String? = Constant.EMPTY_STRING,
            @SerializedName("pictures") val pictures: String? = Constant.EMPTY_STRING,
            @SerializedName("price") val price: Int? = Constant.ZERO,
            @SerializedName("slug") val slug: String? = Constant.EMPTY_STRING,
            @SerializedName("stock") val stock: Int? = Constant.ZERO,
            @SerializedName("tenant_id") val tenantId: Int? = Constant.ZERO,
        )
    }
}