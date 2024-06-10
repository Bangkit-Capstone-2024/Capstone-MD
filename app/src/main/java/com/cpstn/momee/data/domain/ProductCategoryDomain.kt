package com.cpstn.momee.data.domain

import android.os.Parcelable
import com.cpstn.momee.utils.Constant
import kotlinx.parcelize.Parcelize


@Parcelize
data class ProductCategoryDomain(
    val `data`: List<Data> = listOf(),
    val success: String? = Constant.EMPTY_STRING
) : Parcelable {

    @Parcelize
    data class Data(
        val id: Int = Constant.ZERO,
        val nameCategories: String? = Constant.EMPTY_STRING,
        val image: String? = Constant.EMPTY_STRING,
        val amount: Int? = Constant.ZERO
    ) : Parcelable
}