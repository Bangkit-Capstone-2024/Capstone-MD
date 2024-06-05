package com.cpstn.momee.data.domain

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


class ProductsSearchByImageDomain {

    data class Result(
        var success: String? = null,
        var message: String? = null,
        var data: List<Data>? = listOf()
    )

    @Parcelize
    data class Data(
        var id: Int? = 0,
        var name: String? = "",
        var description: String? = "",
    ) : Parcelable

    
}