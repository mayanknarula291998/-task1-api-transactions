package com.example.transactionsapp.data.repository

import com.example.transactionsapp.data.api.AuthApi
import com.example.transactionsapp.data.model.request.LoginRequest
import com.example.transactionsapp.data.model.response.LoginResponse
import com.example.transactionsapp.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(private val api: AuthApi) : AuthRepository {
    override suspend fun login(credential: LoginRequest): LoginResponse {
        val response = api.login(credential)
        println("Response - $response - $credential")
        return response.body() ?: throw Exception("Login failed")
    }
}