package com.cpstn.momee.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpstn.momee.data.domain.AuthDomain
import com.cpstn.momee.data.domain.UserDataPreference
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.network.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private var _loginDataResult: MutableLiveData<DataResult<AuthDomain.Result>> = MutableLiveData()
    val loginDataResult: LiveData<DataResult<AuthDomain.Result>> = _loginDataResult

    private var _loginGoogleResult: MutableLiveData<DataResult<AuthDomain.Result>> = MutableLiveData()
    val loginGoogleResult: LiveData<DataResult<AuthDomain.Result>> = _loginGoogleResult

    private var _userSessionResult: MutableLiveData<UserDataPreference> = MutableLiveData()
    val userSessionResult: LiveData<UserDataPreference> = _userSessionResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(email, password).collect {
                _loginDataResult.value = it
            }
        }
    }

    fun loginGoogle(token: String) {
        viewModelScope.launch {
            authRepository.loginGoogle(token).collect {
                _loginDataResult.value = it
            }
        }
    }

    fun saveUserSession(userToken: String, userName: String, userEmail: String) =
        authRepository.saveSession(userToken, userName, userEmail, true)

    fun getUserSession() {
        viewModelScope.launch {
            authRepository.getUserSession().collect {
                _userSessionResult.value = it
            }
        }
    }
}