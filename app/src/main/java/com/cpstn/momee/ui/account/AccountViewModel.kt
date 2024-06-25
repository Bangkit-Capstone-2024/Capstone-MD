package com.cpstn.momee.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpstn.momee.data.domain.AuthDomain
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.network.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    private var _logoutResult: MutableLiveData<DataResult<AuthDomain.Result>> = MutableLiveData()
    val logoutResult: LiveData<DataResult<AuthDomain.Result>> = _logoutResult

    fun logout() {
        viewModelScope.launch {
            authRepository.logout().collect {
                _logoutResult.value = it
            }
        }
    }
}