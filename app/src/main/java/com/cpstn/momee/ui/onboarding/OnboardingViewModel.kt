package com.cpstn.momee.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpstn.momee.data.domain.UserDataPreference
import com.cpstn.momee.network.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    init {
        getUserSession()
    }

    private var _userSessionResult: MutableLiveData<UserDataPreference> = MutableLiveData()
    val userSessionResult: LiveData<UserDataPreference> = _userSessionResult

    fun saveUserSession() = authRepository.saveSession(null, null, null, true)

    fun getUserSession() {
        viewModelScope.launch {
            val ada = async { authRepository.getUserSession() }.await()
            ada.collect {
                _userSessionResult.value = it
            }
        }
    }
}