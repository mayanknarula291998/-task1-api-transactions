package com.example.transactionsapp.domain.repository

import com.example.transactionsapp.data.model.request.LoginRequest
import com.example.transactionsapp.data.model.response.LoginResponse

interface AuthRepository {
    suspend fun login(credential: LoginRequest): LoginResponse
}