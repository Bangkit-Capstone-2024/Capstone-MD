package com.cpstn.momee.network.response

import com.cpstn.momee.utils.Constant
import com.google.gson.annotations.SerializedName

data class SubmitProductResponse(
    @SerializedName("data") val `data`: Data? = Data(),
    @SerializedName("message") val message: String? = Constant.EMPTY_STRING,
    @SerializedName("success") val success: String = Constant.EMPTY_STRING
) {
    data class Data(
        @SerializedName("address_tenants") val addressTenants: String? = Constant.EMPTY_STRING,
        @SerializedName("category_id") val categoryId: Int? = Constant.ZERO,
        @SerializedName("created_at") val createdAt: String? = Constant.EMPTY_STRING,
        @SerializedName("description") val description: String? = Constant.EMPTY_STRING,
        @SerializedName("id") val id: Int? = Constant.ZERO,
        @SerializedName("is_available") val isAvailable: Boolean? = false,
        @SerializedName("name_products") val nameProducts: String? = Constant.EMPTY_STRING,
        @SerializedName("pictures") val pictures: String? = Constant.EMPTY_STRING,
        @SerializedName("price") val price: Int? = Constant.ZERO,
        @SerializedName("slug") val slug: String? = Constant.EMPTY_STRING,
        @SerializedName("stock") val stock: Int? = Constant.ZERO,
        @SerializedName("tenant_id") val tenantId: Int? = Constant.ZERO,
        @SerializedName("updated_at") val updatedAt: String = Constant.EMPTY_STRING
    )
}