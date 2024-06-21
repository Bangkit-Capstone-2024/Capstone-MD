package com.cpstn.momee.network.repository

import com.cpstn.momee.data.domain.AuthDomain
import com.cpstn.momee.data.domain.UserDataPreference
import com.cpstn.momee.data.mapper.AuthMapper
import com.cpstn.momee.data.payload.RegisterPayload
import com.cpstn.momee.network.DataResult
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
    fun register(registerPayload: RegisterPayload): Flow<DataResult<AuthDomain.Result>>
    fun login(email: String, password: String): Flow<DataResult<AuthDomain.Result>>
    fun loginGoogle(token: String): Flow<DataResult<AuthDomain.Result>>
    fun saveSession(userToken: String?, userName: String?, userEmail: String?, hasShowOnBoarding: Boolean?)
    fun getUserSession(): Flow<UserDataPreference>
    fun logout(): Flow<DataResult<AuthDomain.Result>>
}

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val userPreference: UserPreference
) : AuthRepository {

    override fun register(registerPayload: RegisterPayload) = flow {
        emit(DataResult.Loading)
        val response = authDataSource.register(
            registerPayload.username,
            registerPayload.email,
            registerPayload.password
        )
        try {
            if (response.isSuccessful) {
                val mapper = AuthMapper().map(response.body() ?: AuthResponse.Result())
                emit(DataResult.Success(mapper))
            } else {
                val errorBody = response.errorBody()?.string().orEmpty()
                val message = JSONObject(errorBody).getString("message")
                emit(DataResult.Error(message))
            }
        } catch (e: Exception) {
            emit(DataResult.Error(e.message.orEmpty()))
        }
    }

    override fun login(email: String, password: String): Flow<DataResult<AuthDomain.Result>> =
        flow {
            emit(DataResult.Loading)
            val response = authDataSource.login(email, password)
            try {
                if (response.isSuccessful) {
                    val mapper = AuthMapper().map(response.body() ?: AuthResponse.Result())
                    emit(DataResult.Success(mapper))
                } else {
                    val errorBody = response.errorBody()?.string().orEmpty()
                    val message = JSONObject(errorBody).getString("message")
                    emit(DataResult.Error(message))
                }
            } catch (e: Exception) {
                emit(DataResult.Error(e.message.orEmpty()))
            }
        }

    override fun loginGoogle(token: String) = flow {
        emit(DataResult.Loading)
        val response = authDataSource.loginGoogle(token)
        try {
            if (response.isSuccessful) {
                val mapper = AuthMapper().map(response.body() ?: AuthResponse.Result())
                emit(DataResult.Success(mapper))
            } else {
                val errorBody = response.errorBody()?.string().orEmpty()
                val message = JSONObject(errorBody).getString("message")
                emit(DataResult.Error(message))
            }
        } catch (e: Exception) {
            emit(DataResult.Error(e.message.orEmpty()))
        }
    }

    override fun saveSession(userToken: String?, userName: String?, userEmail: String?, hasShowOnBoarding: Boolean?) {
        CoroutineScope(Dispatchers.IO).launch {
            userPreference.saveUserSession(userToken = userToken, userName, userEmail, hasShowOnBoarding)
        }
    }

    override fun getUserSession() = userPreference.getUserSession()

    override fun logout() = flow {
        emit(DataResult.Loading)
        val response = authDataSource.logout()
        try {
            if (response.isSuccessful) {
                val mapper = AuthMapper().map(response.body() ?: AuthResponse.Result())
                emit(DataResult.Success(mapper))
                CoroutineScope(Dispatchers.IO).launch {
                    userPreference.clearSession()
                }
            } else {
                val errorBody = response.errorBody()?.string().orEmpty()
                val message = JSONObject(errorBody).getString("message")
                emit(DataResult.Error(message))
            }
        } catch (e: Exception) {
            emit(DataResult.Error(e.message.orEmpty()))
        }
    }
}