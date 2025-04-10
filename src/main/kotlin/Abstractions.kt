package com.picpay.edi

abstract class AbstractValidationRule: ValidationRule {
    private var enabled: Boolean = false
    protected fun isEnabled() = enabled
    override fun setParams(params: Boolean) { enabled = params }
}

abstract class AbstractApplicableRule: ApplicableRule {
    override fun apply(record: Any): Any =
        when(record) {
            is Transactions -> whenTransaction(record)
            is Movements -> whenMovement(record)
            is Withdrawals -> whenWithdrawal(record)
            is Pix -> whenPix(record)
            else -> record
        }

    protected open fun whenTransaction(transactions: Transactions) = transactions
    protected open fun whenMovement(movement: Movements) = movement
    protected open fun whenWithdrawal(withdrawal: Withdrawals) = withdrawal
    protected open fun whenPix(pix: Pix) = pix
}