# 📊 Investments Aggregator

O Investments Aggregator é uma aplicação desenvolvida em Java com Spring Boot que permite gerenciar contas de investimento, ações e endereços de cobrança.
O sistema possibilita a integração com a API Brapi para consulta de cotações em tempo real, além de oferecer funcionalidades CRUD completas e testes unitários com Mockito.

# ⚙️ Funcionalidades
📂 Gerenciamento de Contas

- Criar, atualizar, listar e remover contas.
- Associação de endereços de cobrança.

📈 Gerenciamento de Ações

- Cadastro e manutenção de ações.
- Associação de ações a contas de investimento.
- Consulta de cotações em tempo real via Brapi API.

🔄 Relacionamentos

- Account ↔ BillingAddress
- Account ↔ Stock (via AccountStock)

✅ Testes

- Testes unitários com Mockito para garantir a confiabilidade das regras de negócio.

# 🏗️ Estrutura do Projeto
src/main/java/com/example/investmentsaggregator/
│
├── client/                # Cliente para integração com APIs externas (ex: Brapi)
├── controller/            # Controladores REST (UserController, AccountController, StockController)
│   └── dto/               # Objetos de transferência de dados (DTOs)
├── entity/                # Entidades JPA (Account, Stock, BillingAddress, etc.)
├── repository/            # Interfaces JPA para persistência
├── service/               # Camada de serviços (lógica de negócio)

# 🔧 Tecnologias Utilizadas

- Java 21
- Spring Boot (Web, Data JPA)
- Hibernate
- H2 / MySQL (configurável)
- Mockito (testes unitários)
- Brapi API (consulta de cotações)
