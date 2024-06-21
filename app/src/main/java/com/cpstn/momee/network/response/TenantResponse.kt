package com.cpstn.momee.network.response

import com.cpstn.momee.utils.Constant
import com.google.gson.annotations.SerializedName


data class TenantsResponse(
    @SerializedName("data") val `data`: List<Data> = listOf(),
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
        @SerializedName("totalProducts") val totalProducts: Int? = Constant.ZERO,
    )
}