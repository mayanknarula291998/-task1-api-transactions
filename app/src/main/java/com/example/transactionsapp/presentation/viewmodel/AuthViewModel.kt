package com.example.transactionsapp.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transactionsapp.data.model.request.LoginRequest
import com.example.transactionsapp.data.model.response.LoginResponse
import com.example.transactionsapp.domain.usecase.LoginUseCase
import com.example.transactionsapp.utils.SecurePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val securePreferences: SecurePreferences,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginResponse = MutableStateFlow(
        LoginResponse(
            message = "",
            success = false,
            token = ""
        )
    )
    val stateLoginResponse = _loginResponse.asStateFlow()

    init {
        viewModelScope.launch {
            _loginResponse.collect { newData ->
                if (newData.token?.isNotEmpty() == true)
                    securePreferences.saveToken(newData.token.toString())
            }
        }
    }

    private val _error = MutableStateFlow("")
    val errorMsg = _error.asStateFlow()

    fun login(credentials: LoginRequest) {
        viewModelScope.launch {
            try {
                _loginResponse.value = loginUseCase(credentials)
            } catch (e: Exception) {
                _error.value = e.message.toString() ?: "Unknown error"
            }
        }
    }

    fun clearMsg() {
        _error.value = ""
    }

    fun clearData() {
        _loginResponse.value = LoginResponse(
            message = "",
            success = false,
            token = ""
        )
    }

}
