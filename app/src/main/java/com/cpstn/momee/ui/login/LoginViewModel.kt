package com.cpstn.momee.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpstn.momee.data.domain.AuthDomain
import com.cpstn.momee.data.domain.UserDataPreference
import com.cpstn.momee.network.Result
import com.cpstn.momee.network.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private var _loginResult: MutableLiveData<Result<AuthDomain.Result>> = MutableLiveData()
    val loginResult: LiveData<Result<AuthDomain.Result>> = _loginResult

    private var _userSessionResult: MutableLiveData<UserDataPreference> = MutableLiveData()
    val userSessionResult: LiveData<UserDataPreference> = _userSessionResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(email, password).collect {
                _loginResult.value = it
            }
        }
    }

    fun saveUserSession(userToken: String) = authRepository.saveSession(userToken)

    fun getUserSession() {
        viewModelScope.launch {
            authRepository.getUserSession().collect {
                _userSessionResult.value = it
            }
        }
    }
}