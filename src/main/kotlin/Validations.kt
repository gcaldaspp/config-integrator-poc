package com.picpay.edi

import com.picpay.edi.ShortEntityList.FILES_WITHOUT_PIX
import com.picpay.edi.ShortEntityList.MOVEMENTS_AND_WITHDRAWALS

class FepasCodeValidation: AbstractValidationRule() {
    override val name = "fepasCode"
    override val files: List<EntityType> = FILES_WITHOUT_PIX
    override fun valid(field: ValidationField): Boolean =
        if (isEnabled()) field.fepasCode.isNotBlank() else true
}

class CancellationValidation : AbstractValidationRule() {
    override val name = "cancellationId"
    override val files: List<EntityType> = MOVEMENTS_AND_WITHDRAWALS
    override fun valid(field: ValidationField): Boolean =
        if (isEnabled()) field.movementCode == 7 && field.cancellationId.isNotBlank()
        else true
}

class TerminalIdValidation : AbstractValidationRule() {
    override val name = "terminalId"
    override val files: List<EntityType> = FILES_WITHOUT_PIX
    override fun valid(field: ValidationField): Boolean =
        if (isEnabled()) field.terminalId.isNotBlank()
        else true
}