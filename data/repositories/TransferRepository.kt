package com.samuel.domain.repositories

import com.samuel.domain.models.ExchangeRate
import com.samuel.domain.models.Transaction
import com.samuel.domain.models.TransactionResponse

interface TransferRepository {
    suspend fun getRate(currency: String): Double?
    suspend fun sendMoney(transaction: Transaction): TransactionResponse
}