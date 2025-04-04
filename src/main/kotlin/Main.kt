package com.picpay.edi

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.picpay.edi.EntityType.MOVEMENTS
import com.picpay.edi.EntityType.TRANSACTIONS
import com.picpay.edi.EntityType.WITHDRAWALS

fun main() {
    val json = """
        {
            "validations": {
                "fepasCode": true, 
                "terminalId": true, 
                "cancellationId": true
            },
            "applicable": {
                "binance": [
                    {
                        "acquirerTerminalId": "0001",
                        "document": "011",
                        "sellerName": "Binance 1",
                        "sellerCNPJ": "0001",
                        "sellerCNPJMatriz": ""
                    },
                    {
                        "acquirerTerminalId": "0001",
                        "document": "012",
                        "sellerName": "Binance 2",
                        "sellerCNPJ": "0001",
                        "sellerCNPJMatriz": ""
                    }
                ]
            }
        }
    """.trimIndent()
    val mapRules = ObjectMapper().readValue(json, object: TypeReference<Map<String, Map<String, *>>>(){})

    val validations = mapRules["validations"] as Map<String, Boolean>
    val applicable = mapRules["applicable"] as Map<String, *>

    val ruleEngine = ProcessorEngine(
        validations = listOf(
            FepasCodeValidation(),
            CancellationValidation(),
            TerminalIdValidation()
        ),
        applicable = listOf(
            BinanceApplicableRule()
        )
    )

    val transactions = listOf(
        Transactions("1", "011","1"),
        Transactions("2", "012","1"),
        Transactions("3", "013",""),
        Transactions("4", "011",""),
    )

    println("== Transactions == ")
    transactions.forEach {
        ruleEngine.applyValidations(TRANSACTIONS, validations, ValidationField(it)).let(::println)
        ruleEngine.applyApplicable(TRANSACTIONS, applicable, it).let(::println)
    }

    val movements = listOf(
        Movements("1", "011","1",5, null, "1"),
        Movements("2", "012","1",6, null, "1"),
        Movements("3", "013","",7, "xyz"),
        Movements("4", "011","",8),
    )

    println("== Movements == ")
    movements.forEach {
        ruleEngine.applyValidations(MOVEMENTS, validations, ValidationField(it)).let(::println)
        ruleEngine.applyApplicable(MOVEMENTS, applicable, it).let(::println)
    }

    val withdrawals = listOf(
        Withdrawals("1", "011","1",5, null, "1"),
        Withdrawals("2", "012","1",6),
        Withdrawals("3", "013","",7, "xyz"),
        Withdrawals("4", "011","",8, null, "1"),
    )

    println("== Withdrawals == ")
    withdrawals.forEach {
        ruleEngine.applyValidations(WITHDRAWALS, validations, ValidationField(it)).let(::println)
        ruleEngine.applyApplicable(WITHDRAWALS, applicable, it).let(::println)
    }
}