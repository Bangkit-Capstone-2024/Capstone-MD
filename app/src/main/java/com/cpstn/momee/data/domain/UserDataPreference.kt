package com.cpstn.momee.data.domain

import android.os.Parcelable
import com.cpstn.momee.utils.Constant
import kotlinx.parcelize.Parcelize

data class UserDataPreference(
    var userToken: String = Constant.EMPTY_STRING,
    var userEmail: String = Constant.EMPTY_STRING
)

data class UserFirebase(
    var userEmail: String = Constant.EMPTY_STRING,
    var fcmToken: String = Constant.EMPTY_STRING
)

data class UserListDomain(
    val currentPage: Int? = Constant.ZERO,
    val message: String? = Constant.EMPTY_STRING,
    val query: List<Query> = listOf(),
    val success: String? = Constant.EMPTY_STRING,
    val totalData: Int? = Constant.ZERO,
    val totalPage: Int? = Constant.ZERO
) {
    @Parcelize
    data class Query(
        val email: String? = Constant.EMPTY_STRING,
        val id: Int? = Constant.ZERO,
        val isVerified: Boolean? = false,
        val username: String = Constant.EMPTY_STRING
    ) : Parcelable
}

