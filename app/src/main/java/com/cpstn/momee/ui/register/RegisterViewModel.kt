package com.cpstn.momee.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpstn.momee.data.domain.AuthDomain
import com.cpstn.momee.data.payload.RegisterPayload
import com.cpstn.momee.network.Result
import com.cpstn.momee.network.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    private var _registerResult: MutableLiveData<Result<AuthDomain.Result>> = MutableLiveData()
    val registerResult: LiveData<Result<AuthDomain.Result>> = _registerResult


    fun register(username: String, email: String, password: String) {
        val registerPayload = RegisterPayload(username, email, password)
        viewModelScope.launch {
            authRepository.register(registerPayload).collect {
                _registerResult.value = it
            }
        }
    }
}