package com.picpay.edi

class ProcessorEngine(
    validations: List<ValidationRule>,
    applicable: List<ApplicableRule>
) {
    private var validationsMap: Map<String, ValidationRule> = validations.associateBy { it.name }
    private var applyMap: Map<String, ApplicableRule> = applicable.associateBy { it.name }

    fun applyValidations(
        file: EntityType,
        validations: Map<String, Boolean>,
        ruleField: ValidationField
    ): List<Pair<String, Boolean>> =
        validations
            .mapNotNull { validationsMap[it.key]?.apply { setParams(it.value) } }
            .filter { it.isValidFile(file) }.map { it.name to it.valid(ruleField) }

    fun applyApplicable(file: EntityType, applicable: Map<String, *>, row: Any): Any {
        var modifiedRow = row
        applicable
            .mapNotNull { entry ->
                applyMap[entry.key]
                    ?.also {
                        it.setParams(requireNotNull(entry.value))
                    }
            }
            .filter { it.isValidFile(file) }
            .forEach { modifiedRow = it.apply(row) }
        return modifiedRow
    }
}