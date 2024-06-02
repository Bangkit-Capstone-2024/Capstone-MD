package com.cpstn.momee.network.response

import com.google.gson.annotations.SerializedName


class AuthResponse {
    data class Result(
        @SerializedName("success") var success: String? = null,
        @SerializedName("message") var message: String? = null,
        @SerializedName("data") var data: Data? = Data()
    )

    data class Data(
        @SerializedName("id") var id: Int = 0,
        @SerializedName("email") var email: String? = "",
        @SerializedName("username") var username: String? = "",
        @SerializedName("isVerified") var isVerified: Boolean = false,
        @SerializedName("token") var token: String? = ""

    )
}
