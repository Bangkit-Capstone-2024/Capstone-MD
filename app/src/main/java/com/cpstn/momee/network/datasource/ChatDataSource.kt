package com.cpstn.momee.network.datasource

import com.cpstn.momee.network.response.UserListResponse
import retrofit2.Response
import retrofit2.http.POST


interface ChatDataSource {

    @POST("users/read")
    suspend fun getUserList(): Response<UserListResponse>

}