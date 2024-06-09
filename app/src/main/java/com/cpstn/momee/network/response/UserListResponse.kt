package com.cpstn.momee.network.response

import com.cpstn.momee.utils.Constant
import com.google.gson.annotations.SerializedName

data class UserListResponse(
    @SerializedName("current_page") val currentPage: Int? = Constant.ZERO,
    @SerializedName("message") val message: String? = Constant.EMPTY_STRING,
    @SerializedName("query") val query: List<Query> = listOf(),
    @SerializedName("success") val success: String? = Constant.EMPTY_STRING,
    @SerializedName("total_data") val totalData: Int? = Constant.ZERO,
    @SerializedName("total_page") val totalPage: Int? = Constant.ZERO
) {
    data class Query(
        @SerializedName("email") val email: String? = Constant.EMPTY_STRING,
        @SerializedName("id") val id: Int? = Constant.ZERO,
        @SerializedName("isVerified") val isVerified: Boolean? = false,
        @SerializedName("username") val username: String = Constant.EMPTY_STRING
    )
}