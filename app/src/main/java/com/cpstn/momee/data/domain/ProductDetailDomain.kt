package com.cpstn.momee.data.domain

import android.os.Parcelable
import com.cpstn.momee.utils.Constant
import kotlinx.parcelize.Parcelize


data class ProductDetailDomain(
    val `data`: Data = Data(),
    val message: String? = Constant.EMPTY_STRING,
    val success: String = Constant.EMPTY_STRING
) {
    @Parcelize
    data class Data(
        val addressTenants: String? = Constant.EMPTY_STRING,
        val categoryId: Int? = Constant.ZERO,
        val description: String? = Constant.EMPTY_STRING,
        val id: Int? = Constant.ZERO,
        val isAvailable: Boolean? = false,
        val nameProducts: String? = Constant.EMPTY_STRING,
        val pictures: String? = Constant.EMPTY_STRING,
        val price: Int? = Constant.ZERO,
        val slug: String? = Constant.EMPTY_STRING,
        val stock: Int? = Constant.ZERO,
        val tenant: Tenant? = Tenant(),
        val tenantId: Int? = Constant.ZERO,
    ) : Parcelable {

        @Parcelize
        data class Tenant(
            val addressTenants: String? = Constant.EMPTY_STRING,
            val id: Int? = Constant.ZERO,
            val image: String? = Constant.EMPTY_STRING,
            val locationLat: Double? = Constant.ZERO_DOUBLE,
            val locationLng: Double? = Constant.ZERO_DOUBLE,
            val nameTenants: String? = Constant.EMPTY_STRING,
            val phone: String? = Constant.EMPTY_STRING,
            val userId: Int = Constant.ZERO
        ) : Parcelable
    }
}