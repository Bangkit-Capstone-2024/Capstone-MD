package com.cpstn.momee.network.response

import com.cpstn.momee.utils.Constant
import com.google.gson.annotations.SerializedName

data class ProductCategoryResponse(
    @SerializedName("data") val `data`: List<Data> = listOf(),
    @SerializedName("success") val success: String? = Constant.EMPTY_STRING
) {
    data class Data(
        @SerializedName("id") val id: Int = Constant.ZERO,
        @SerializedName("name_categories") val nameCategories: String? = Constant.EMPTY_STRING,
        @SerializedName("image") val image: String? = Constant.EMPTY_STRING,
        @SerializedName("amount") val amount: Int? = Constant.ZERO,
    )
}