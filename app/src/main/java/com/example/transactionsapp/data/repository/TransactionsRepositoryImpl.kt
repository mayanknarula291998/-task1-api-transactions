package com.example.transactionsapp.data.repository

import com.example.transactionsapp.data.api.TransactionsApi
import com.example.transactionsapp.data.model.response.Transaction
import com.example.transactionsapp.domain.repository.TransactionsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionsRepositoryImpl @Inject constructor(private val api: TransactionsApi) :
    TransactionsRepository {
    override suspend fun getTransactions(): List<Transaction> {
        val response = api.getTransactions()
        println("Started Response - ${response.body()}")
        return response.body() ?: emptyList()
    }
}