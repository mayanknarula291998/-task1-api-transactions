package com.example.transactionsapp.data.model.response

data class Transaction(
    val amount: Int?,
    val category: String?,
    val date: String?,
    val description: String?,
    val id: Int?
)