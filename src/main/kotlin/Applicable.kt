package com.picpay.edi

import com.picpay.edi.ShortEntityList.FILES_WITHOUT_PIX
import com.picpay.edi.ShortEntityList.ONLY_TRANSACTIONS

class NSellersApplicableRule : AbstractApplicableRule() {
    override val name = "nsellers"
    override val files = FILES_WITHOUT_PIX
    private var internalInformations: List<InternalInformation> = emptyList()

    override fun setParams(params: Any) {
        val configs = params as List<Map<String, *>>
        internalInformations = configs.map {
            InternalInformation(
                acqTerminalId = it["acquirerTerminalId"] as String?,
                document = it["document"] as String?,
                sellerName = it["sellerName"] as String?,
                sellerNameMatriz = it["sellerNameMatriz"] as String?,
            )
        }
    }

    override fun whenTransaction(transactions: Transactions): Transactions =
        ifDocument(transactions.document, transactions) {
                r, i ->
            r.copy(
                sellerName = i.sellerName ?: r.sellerName
            )
        }

    override fun whenMovement(movement: Movements): Movements =
        ifDocument(movement.document, movement) {
                r, i ->
            r.copy(
                sellerName = i.sellerName ?: r.sellerName
            )
        }

    override fun whenWithdrawal(withdrawal: Withdrawals): Withdrawals =
        ifDocument(withdrawal.document, withdrawal) {
                r, i ->
            r.copy(
                sellerName = i.sellerName ?: r.sellerName
            )
        }

    private inline fun <T> ifDocument(document: String, record: T, fn: (T, InternalInformation) -> T): T {
        val internalInformation = internalInformations.firstOrNull { it.document == document }
        return if (internalInformation != null) {
            fn(record, internalInformation)
        } else record
    }

    private data class InternalInformation(
        val acqTerminalId: String?,
        val document: String?,
        val sellerName: String?,
        val sellerNameMatriz: String?
    )
}

class SellerApplicableRule : AbstractApplicableRule(){
    override val name = "sellerId"
    override val files: List<EntityType> = ONLY_TRANSACTIONS
    private var enabled = false
    override fun setParams(params: Any) {
        enabled = params as Boolean
    }

    override fun whenTransaction(transactions: Transactions): Transactions =
        if (enabled) {
            with(transactions) {
                copy(
                    sellerId = preservedSellerId
                )
            }
        } else transactions
}