package com.example.transactionsapp.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.transactionsapp.data.model.response.Transaction
import com.example.transactionsapp.domain.usecase.FetchTransactionsUseCase
import com.example.transactionsapp.utils.SecurePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val securePreferences: SecurePreferences,
    private val fetchTransactionsUseCase: FetchTransactionsUseCase
) :
    ViewModel() {
    private val _transaction = MutableStateFlow<List<Transaction>>(emptyList())
    val stateTransactions = _transaction.asStateFlow()

    private var error by mutableStateOf("")

    fun fetchTransactions() {
        println("Started")
        viewModelScope.launch {
            try {
                _transaction.value = fetchTransactionsUseCase()
            } catch (e: Exception) {
                error = e.message ?: "Failed to fetch transactions"
            }
        }
    }

    fun clearToken() {
        securePreferences.clearToken()
    }
}