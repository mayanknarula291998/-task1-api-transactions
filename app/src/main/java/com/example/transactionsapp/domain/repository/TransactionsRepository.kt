package com.example.transactionsapp.domain.repository

import com.example.transactionsapp.data.model.response.Transaction

interface TransactionsRepository {
    suspend fun getTransactions(): List<Transaction>

}