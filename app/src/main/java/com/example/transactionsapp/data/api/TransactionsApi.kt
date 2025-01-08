package com.example.transactionsapp.data.api

import com.example.transactionsapp.data.model.response.Transaction
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface TransactionsApi {
    @GET("transactions")
    suspend fun getTransactions(): Response<List<Transaction>>
}