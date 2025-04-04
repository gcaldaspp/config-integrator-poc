package com.picpay.edi

interface Rule<T> {
    val name: String
    val files: List<EntityType>
    fun setParams(params: T)
    fun isValidFile(file: EntityType) = file in files
}

// Tudo aquilo que pode impedir um registro de sair no extrato. Exemplo: Ausência de fepasCode
interface ValidationRule: Rule<Boolean> {
    fun valid(field: ValidationField): Boolean
}

// Tudo aquilo que modifica a linha do extrato. Exemplo: Modificações da Binance
interface ApplicableRule: Rule<Any> {
    fun apply(record: Any): Any
}