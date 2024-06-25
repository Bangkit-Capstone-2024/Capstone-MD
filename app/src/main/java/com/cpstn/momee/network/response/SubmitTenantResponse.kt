package com.cpstn.momee.network.response

import com.cpstn.momee.utils.Constant
import com.google.gson.annotations.SerializedName


data class SubmitTenantResponse(
    @SerializedName("data") val `data`: Data? = Data(),
    @SerializedName("message") val message: String? = Constant.EMPTY_STRING,
    @SerializedName("success") val success: String? = Constant.EMPTY_STRING
) {
    data class Data(
        @SerializedName("address_tenants") val addressTenants: String? = Constant.EMPTY_STRING,
        @SerializedName("id") val id: Int? = Constant.ZERO,
        @SerializedName("image") val image: String? = Constant.EMPTY_STRING,
        @SerializedName("name_tenants") val nameTenants: String? = Constant.EMPTY_STRING,
        @SerializedName("phone") val phone: String? = Constant.EMPTY_STRING,
    )
}