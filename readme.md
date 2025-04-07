# POC Processador de regras do EDI

Nesse projeto temos a implementação do motor de aplicação de regra do edi.

## Conceitos importantes

No projeto, as regras foram categorizadas em dois tipos com funções distintas:

1- Regras Validáveis: Verificam a conformidade de um registro, determinando se ele deve ser incluído ou excluído no arquivo final. Atuam como filtro, garantindo que apenas dados que atendam aos requisitos pré-estabelecidos sejam processados.
2- Regras Aplicáveis: Modificam diretamente os valores de um registro. Alteram o conteúdo final dos dados processados, ajustando informações antes da geração do arquivo de saída.

## Exemplo de json

```json
{
    "validations": {
        "fepasCode": true, 
        "terminalId": true, 
        "cancellationId": true
    },
    "applicable": {
        "nsellers": [
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
```