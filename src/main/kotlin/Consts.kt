package com.picpay.edi

import com.picpay.edi.EntityType.*

object ShortEntityList {
    val FILES_WITHOUT_PIX = listOf(TRANSACTIONS, MOVEMENTS, WITHDRAWALS)
    val ONLY_TRANSACTIONS = listOf(TRANSACTIONS)
    val MOVEMENTS_AND_WITHDRAWALS = listOf(MOVEMENTS, WITHDRAWALS)
}