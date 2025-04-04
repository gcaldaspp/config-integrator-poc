package com.picpay.edi

import com.picpay.edi.EntityType.MOVEMENTS
import com.picpay.edi.EntityType.TRANSACTIONS
import com.picpay.edi.EntityType.WITHDRAWALS

class FepasCodeValidation: AbstractValidationRule() {
    override val name = "fepasCode"
    override val files: List<EntityType> = listOf(TRANSACTIONS, MOVEMENTS, WITHDRAWALS)
    override fun valid(field: ValidationField): Boolean =
        if (isEnabled()) field.fepasCode.isNotBlank() else true
}

class CancellationValidation : AbstractValidationRule() {
    override val name = "cancellationId"
    override val files: List<EntityType> = listOf(MOVEMENTS, WITHDRAWALS)
    override fun valid(field: ValidationField): Boolean =
        if (isEnabled()) field.movementCode == 7 && field.cancellationId.isNotBlank()
        else true
}

class TerminalIdValidation : AbstractValidationRule() {
    override val name = "terminalId"
    override val files: List<EntityType> = listOf(TRANSACTIONS, MOVEMENTS, WITHDRAWALS)
    override fun valid(field: ValidationField): Boolean =
        if (isEnabled()) field.terminalId.isNotBlank()
        else true
}