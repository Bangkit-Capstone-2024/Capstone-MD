package com.cpstn.momee.data.domain

import com.google.gson.annotations.SerializedName

class AuthDomain {

    data class Result(
        var success: String? = null,
        var message: String? = null,
        var data: Data? = Data()
    )

    data class Data(
        var id: Int? = 0,
        var email: String? = null,
        var username: String? = null,
        var isVerified: Boolean? = false,
        var token: String? = null

    )
}