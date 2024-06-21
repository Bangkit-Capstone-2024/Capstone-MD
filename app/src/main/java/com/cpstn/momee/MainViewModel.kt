package com.cpstn.momee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpstn.momee.data.domain.UserDataPreference
import com.cpstn.momee.data.domain.UserLocation
import com.cpstn.momee.network.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private var _userSessionResult: MutableLiveData<UserDataPreference> = MutableLiveData()
    val userSessionResult: LiveData<UserDataPreference> = _userSessionResult


    var currentUserInfo: UserDataPreference? = UserDataPreference()

    var getCurrentLocation: MutableLiveData<UserLocation> = MutableLiveData()

    fun getUserSession() {
        viewModelScope.launch {
            val data = async { authRepository.getUserSession() }
            data.await().collect {
                _userSessionResult.value = it
            }
        }
    }
}