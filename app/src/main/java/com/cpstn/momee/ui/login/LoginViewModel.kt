package com.cpstn.momee.ui.login

import androidx.lifecycle.ViewModel
import com.cpstn.momee.network.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {

    fun login() = authRepository.login("mail@test.com", "admin123")
}