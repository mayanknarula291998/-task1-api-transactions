package com.example.transactionsapp.data.api

import com.example.transactionsapp.data.model.request.LoginRequest
import com.example.transactionsapp.data.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

// AuthApi.kt
interface AuthApi {
    @POST("login")
    suspend fun login(@Body credentials: LoginRequest): Response<LoginResponse>
}