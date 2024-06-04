package com.cpstn.momee.network.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response


interface AuthRepository {
    fun register(registerPayload: RegisterPayload): LiveData<Result<AuthDomain.Result>>
    fun login(email: String, password: String): Flow<Result<AuthDomain.Result>>
    fun saveSession(userToken: String)
    fun getUserSession(): Flow<UserDataPreference>
    fun logout()
}

class AuthRepositoryImpl(private val authDataSource: AuthDataSource, private val userPreference: UserPreference) : AuthRepository {
    override fun register(registerPayload: RegisterPayload): LiveData<Result<AuthDomain.Result>> {
        val result = MutableLiveData<Result<AuthDomain.Result>>()
        CoroutineScope(Dispatchers.IO).launch {
            val call = authDataSource.register(
                registerPayload.username,
                registerPayload.email,
                registerPayload.password
            )
            result.postValue(Result.Loading)
            call.enqueue(object : Callback<AuthResponse.Result> {
                override fun onResponse(
                    call: Call<AuthResponse.Result>,
                    response: Response<AuthResponse.Result>
                ) {
                    try {
                        val mapper = AuthMapper().map(response.body() ?: AuthResponse.Result())
                        result.postValue(Result.Success(mapper))
                    } catch (e: HttpException) {
                        val errorBody = e.response()?.errorBody()?.string().orEmpty()
                        val message = JSONObject(errorBody).getString("message")
                        result.postValue(Result.Error(message))
                    }
                }

                override fun onFailure(p0: Call<AuthResponse.Result>, e: Throwable) {
                    result.postValue(Result.Error(e.message.orEmpty()))
                }
            })
        }
        return result
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

    override fun getUserSession()  = userPreference.getUserSession()

    override fun logout() {
        CoroutineScope(Dispatchers.IO).launch {
            userPreference.clearSession()
        }
    }
}