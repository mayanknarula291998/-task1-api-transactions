package com.example.transactionsapp.data.model.response

data class LoginResponse(
    val message: String?,
    val success: Boolean?,
    val token: String?
)