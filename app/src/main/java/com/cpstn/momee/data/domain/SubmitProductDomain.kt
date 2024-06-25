package com.cpstn.momee.data.domain

import com.cpstn.momee.utils.Constant


data class SubmitProductDomain(
    val `data`: Data? = Data(),
    val message: String? = Constant.EMPTY_STRING,
    val success: String = Constant.EMPTY_STRING
) {
    data class Data(
        val addressTenants: String? = Constant.EMPTY_STRING,
        val description: String? = Constant.EMPTY_STRING,
        val id: Int? = Constant.ZERO,
        val isAvailable: Boolean? = false,
        val nameProducts: String? = Constant.EMPTY_STRING,
        val pictures: String? = Constant.EMPTY_STRING,
        val price: Int? = Constant.ZERO,
        val slug: String? = Constant.EMPTY_STRING,
        val stock: Int? = Constant.ZERO,
    )
}