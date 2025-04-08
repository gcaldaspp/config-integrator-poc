# Visão Geral do Projeto

Este projeto é uma aplicação baseada em Kotlin que processa e valida transações financeiras, movimentos e saques utilizando um motor de regras. A aplicação está estruturada em múltiplos componentes, incluindo modelos de domínio, regras de validação e um motor de processamento.

## Funcionalidades

- **Domain**: Representa entidades como `Transactions` (Transações), `Movements` (Movimentos), `Withdrawals` (Saques) e `Pix`.
- **ValidationRule**: Implementa regras para validar campos como `fepasCode`, `cancellationId` e `terminalId`.
- **RuleEngine**: Processa validações e aplica regras aplicáveis às entidades.

## Estrutura do Projeto

### `src/main/kotlin/Domain.kt`
Define os modelos de domínio e enums:
- `Transactions`, `Movements`, `Withdrawals` e `Pix` representam diferentes entidades financeiras.
- `ValidationField` é uma classe auxiliar para fins de validação.
- `EntityType` é um enum para categorizar as entidades.

### `src/main/kotlin/Main.kt`
O ponto de entrada da aplicação:
- Lê a configuração JSON para validações e regras aplicáveis.
- Inicializa o `ProcessorEngine` com validações e regras aplicáveis.
- Processa uma lista de `Transactions`, `Movements` e `Withdrawals` utilizando o motor de regras.

### `src/main/kotlin/ProcessEngine.kt`
Implementa o `ProcessorEngine`:
- Gerencia a aplicação de regras de validação e regras aplicáveis às entidades.
- Utiliza mapas para associar nomes de regras às suas implementações.

### `src/main/kotlin/Validations.kt`
Define as regras de validação:
- `FepasCodeValidation`: Valida que o campo `fepasCode` não está vazio.
- `CancellationValidation`: Valida o campo `cancellationId` com base no `movementCode`.
- `TerminalIdValidation`: Valida que o campo `terminalId` não está vazio.

### `src/main/kotlin/Applicable.kt`
Define a lógica para **regras aplicáveis** que modificam ou processam entidades como `Transações`, `Movimentos` e `Retiradas`. Inclui:

- `NSellersApplicableRule`: implementa uma regra que atualiza o campo `sellerName` de entidades com base em seu campo `document` e uma lista de configurações internas.

## Como Executar

1. Clone o repositório e navegue até o diretório do projeto.
2. Certifique-se de ter o Maven e um JDK compatível instalados.
3. Compile o projeto:
   ```bash
   mvn clean install
   ```
4. Execute a aplicação:
   ```bash
   mvn exec:java -Dexec.mainClass="com.picpay.edi.MainKt"
   ```

## Exemplo de Entrada

A aplicação processa uma configuração JSON como a seguinte:
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
        "sellerCNPJMatriz": "123456"
      }
    ]
  }
}
```

## Exemplo de Saída

A aplicação gera os resultados das validações e regras aplicáveis para cada tipo de entidade (`Transactions`, `Movements`, `Withdrawals`).
