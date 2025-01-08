package com.example.transactionsapp.domain.usecase

import com.example.transactionsapp.data.model.response.Transaction
import com.example.transactionsapp.domain.repository.TransactionsRepository
import javax.inject.Inject

class FetchTransactionsUseCase @Inject constructor(private val transactionsRepository: TransactionsRepository) {
    suspend operator fun invoke(): List<Transaction> {
        return transactionsRepository.getTransactions()
    }
}