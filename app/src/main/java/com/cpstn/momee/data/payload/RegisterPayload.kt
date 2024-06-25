package com.cpstn.momee.data.payload

data class RegisterPayload(
    var username: String = "",
    var email: String = "",
    var password: String = ""
)