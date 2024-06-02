package com.cpstn.momee.network.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cpstn.momee.data.domain.AuthDomain
import com.cpstn.momee.data.mapper.AuthMapper
import com.cpstn.momee.data.payload.RegisterPayload
import com.cpstn.momee.network.Result
import com.cpstn.momee.network.datasource.AuthDataSource
import com.cpstn.momee.network.response.AuthResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response


interface AuthRepository {
    fun register(registerPayload: RegisterPayload): LiveData<Result<AuthDomain.Result>>
    fun login(email: String, password: String): LiveData<Result<AuthDomain.Result>>
}

class AuthRepositoryImpl(private val authDataSource: AuthDataSource) : AuthRepository {
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

    override fun login(email: String, password: String): LiveData<Result<AuthDomain.Result>> {
        val result = MutableLiveData<Result<AuthDomain.Result>>()
        CoroutineScope(Dispatchers.IO).launch {
            val call = authDataSource.login(email, password)
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

                override fun onFailure(call: Call<AuthResponse.Result>, t: Throwable) {
                    result.postValue(Result.Error(t.message.orEmpty()))
                }
            })
        }
        return result
    }
}