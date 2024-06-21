package com.cpstn.momee.data.domain

import android.os.Parcelable
import com.cpstn.momee.utils.Constant
import kotlinx.parcelize.Parcelize


data class TenantDomain(
    val `data`: List<Data> = listOf(),
    val success: String? = Constant.EMPTY_STRING
) {
    @Parcelize
    data class Data(
        val addressTenants: String? = Constant.EMPTY_STRING,
        val id: Int? = Constant.ZERO,
        val image: String? = Constant.EMPTY_STRING,
        val locationLat: Double? = Constant.ZERO_DOUBLE,
        val locationLng: Double? = Constant.ZERO_DOUBLE,
        val nameTenants: String? = Constant.EMPTY_STRING,
        val phone: String? = Constant.EMPTY_STRING,
        val totalProducts: Int? = Constant.ZERO,
    ) : Parcelable
}