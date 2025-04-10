package com.picpay.edi

data class Transactions(
    val brandTrxId: String,
    val document: String,
    val fepasCode: String,
    val sellerName: String? = null,
    val sellerId: String? = null,
    val preservedSellerId: String? = null
)

data class Movements(
    val brandTrxId: String,
    val document: String,
    val fepasCode: String,
    val movementCode: Int,
    val cancellationId: String? = null,
    val terminalId: String = "",
    val sellerName: String? = null,
    val sellerId: String? = null,
    val preservedSellerId: String? = null
)

data class Withdrawals(
    val brandTrxId: String,
    val document: String,
    val fepasCode: String,
    val movementCode: Int,
    val cancellationId: String? = null,
    val terminalId: String = "",
    val sellerName: String? = null,
    val sellerId: String? = null,
    val preservedSellerId: String? = null
)

data class Pix(
    val brandTrxId: String
)

enum class EntityType {
    TRANSACTIONS, MOVEMENTS, WITHDRAWALS, PIX
}

data class ValidationField(
    val brandTrxId: String,
    val document: String,
    val movementCode: Int,
    val cancellationId: String = "",
    val fepasCode: String = "",
    val terminalId: String = "",
)
{

    constructor(transactions: Transactions): this(
        brandTrxId = transactions.brandTrxId,
        document = transactions.document,
        fepasCode = transactions.fepasCode,
        movementCode = 1,
    )

    constructor(movements: Movements): this(
        brandTrxId = movements.brandTrxId,
        document = movements.document,
        fepasCode = movements.fepasCode,
        movementCode = movements.movementCode,
        cancellationId = movements.cancellationId ?: ""
    )

    constructor(withdrawals: Withdrawals): this(
        brandTrxId = withdrawals.brandTrxId,
        document = withdrawals.document,
        fepasCode = withdrawals.fepasCode,
        movementCode = withdrawals.movementCode,
        cancellationId = withdrawals.cancellationId ?: ""
    )
}