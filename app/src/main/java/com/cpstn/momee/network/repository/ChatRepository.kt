package com.cpstn.momee.network.repository

import com.cpstn.momee.data.domain.UserListDomain
import com.cpstn.momee.data.mapper.UserListMapper
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.network.datasource.ChatDataSource
import com.cpstn.momee.network.response.UserListResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface ChatRepository {
    fun getUserList(): Flow<DataResult<UserListDomain>>
}

class ChatRepositoryImpl(private val dataSource: ChatDataSource) : ChatRepository {
    override fun getUserList() = flow {
        emit(DataResult.Loading)
        val response = dataSource.getUserList()
        try {
            if (response.isSuccessful) {
                val mapper = UserListMapper().map(response.body() ?: UserListResponse())
                emit(DataResult.Success(mapper))
            } else {
                val errorBody = response.errorBody()?.string().orEmpty()
                val message = org.json.JSONObject(errorBody).getString("message")
                emit(DataResult.Error(message))
            }
        } catch (e: Exception) {
            emit(DataResult.Error(e.message.orEmpty()))
        }
    }
}