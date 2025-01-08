package com.example.transactionsapp.domain.usecase

import com.example.transactionsapp.data.model.request.LoginRequest
import com.example.transactionsapp.data.model.response.LoginResponse
import com.example.transactionsapp.domain.repository.AuthRepository
import javax.inject.Inject


class LoginUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(credentials: LoginRequest): LoginResponse {
        return authRepository.login(credentials)
    }
}