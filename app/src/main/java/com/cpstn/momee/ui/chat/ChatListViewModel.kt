package com.cpstn.momee.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpstn.momee.data.domain.UserDataPreference
import com.cpstn.momee.data.domain.UserListDomain
import com.cpstn.momee.network.DataResult
import com.cpstn.momee.network.repository.AuthRepository
import com.cpstn.momee.network.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private var _userListResult: MutableLiveData<DataResult<UserListDomain>> = MutableLiveData()
    val userListResult: LiveData<DataResult<UserListDomain>> = _userListResult

    private var _userSessionResult: MutableLiveData<UserDataPreference> = MutableLiveData()
    val userSessionResult: LiveData<UserDataPreference> = _userSessionResult

    fun getUserList() {
        viewModelScope.launch {
            chatRepository.getUserList().collect {
                _userListResult.value = it
            }
        }
    }

    fun getUserSession() {
        viewModelScope.launch {
            authRepository.getUserSession().collect {
                _userSessionResult.value = it
            }
        }
    }
}