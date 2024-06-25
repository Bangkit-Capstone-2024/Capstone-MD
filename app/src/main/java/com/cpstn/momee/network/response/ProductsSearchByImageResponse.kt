package com.cpstn.momee.network.response

import com.google.gson.annotations.SerializedName

class ProductsSearchByImageResponse {
    data class Result(
        @SerializedName("success") var success: String? = null,
        @SerializedName("message") var message: String? = null,
        @SerializedName("data") var data: List<Data>? = listOf()
    )

    data class Data(
        @SerializedName("id") var id: Int = 0,
        @SerializedName("name_products") var name: String? = "",
        @SerializedName("description") var description: String? = "",
    )
}

enum class MediaType(val value: String) {
    TEXT("text/plain"),
    IMAGE("image/jpeg")
}