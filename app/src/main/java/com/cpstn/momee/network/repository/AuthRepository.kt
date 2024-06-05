package com.cpstn.momee.network.repository

import com.cpstn.momee.data.domain.AuthDomain
import com.cpstn.momee.data.domain.UserDataPreference
import com.cpstn.momee.data.mapper.AuthMapper
import com.cpstn.momee.data.payload.RegisterPayload
import com.cpstn.momee.network.Result
import com.cpstn.momee.network.datasource.AuthDataSource
import com.cpstn.momee.network.response.AuthResponse
import com.cpstn.momee.preference.UserPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.json.JSONObject


interface AuthRepository {
    fun register(registerPayload: RegisterPayload): Flow<Result<AuthDomain.Result>>
    fun login(email: String, password: String): Flow<Result<AuthDomain.Result>>
    fun saveSession(userToken: String)
    fun getUserSession(): Flow<UserDataPreference>
    fun logout()
}

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val userPreference: UserPreference
) : AuthRepository {

    override fun register(registerPayload: RegisterPayload) = flow {
        emit(Result.Loading)
        val response = authDataSource.register(
            registerPayload.username,
            registerPayload.email,
            registerPayload.password
        )
        try {
            if (response.isSuccessful) {
                val mapper = AuthMapper().map(response.body() ?: AuthResponse.Result())
                emit(Result.Success(mapper))
            } else {
                val errorBody = response.errorBody()?.string().orEmpty()
                val message = JSONObject(errorBody).getString("message")
                emit(Result.Error(message))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.orEmpty()))
        }
    }

    override fun login(email: String, password: String): Flow<Result<AuthDomain.Result>> = flow {
        emit(Result.Loading)
        val response = authDataSource.login(email, password)
        try {
            if (response.isSuccessful) {
                val mapper = AuthMapper().map(response.body() ?: AuthResponse.Result())
                emit(Result.Success(mapper))
            } else {
                val errorBody = response.errorBody()?.string().orEmpty()
                val message = JSONObject(errorBody).getString("message")
                emit(Result.Error(message))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.orEmpty()))
        }
    }

    override fun saveSession(userToken: String) {
        CoroutineScope(Dispatchers.IO).launch {
            userPreference.saveUserSession(userToken = userToken)
        }
    }

    override fun getUserSession() = userPreference.getUserSession()

    override fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            userPreference.clearSession()
        }
    }
}