package com.cpstn.momee.data.domain

import com.cpstn.momee.utils.Constant

data class SubmitTenantDomain(
    val `data`: Data? = Data(),
    val message: String? = Constant.EMPTY_STRING,
    val success: String? = Constant.EMPTY_STRING
) {
    data class Data(
        val addressTenants: String? = Constant.EMPTY_STRING,
        val id: Int? = Constant.ZERO,
        val image: String? = Constant.EMPTY_STRING,
        val nameTenants: String? = Constant.EMPTY_STRING,
        val phone: String? = Constant.EMPTY_STRING,
    )
}