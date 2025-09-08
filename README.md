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
client/                # Cliente para integração com APIs externas (ex: Brapi)
controller/            # Controladores REST (UserController, AccountController, StockController)
controller/dto/               # Objetos de transferência de dados (DTOs)
entity/                # Entidades JPA (Account, Stock, BillingAddress, etc.)
repository/            # Interfaces JPA para persistência
service/               # Camada de serviços (lógica de negócio)

# 🔧 Tecnologias Utilizadas

- Java 21
- Spring Boot (Web, Data JPA)
- Hibernate
- H2 / MySQL (configurável)
- Mockito (testes unitários)
- Brapi API (consulta de cotações)

# Como Executar o Projeto:

1. Clonar o repositório
git clone https://github.com/seu-usuario/investments-aggregator.git

2. Acessar o diretório
cd investments-aggregator

2.2 Trocar credenciais para as respectivas credenciais do seu banco de dados
2.3 Gerar Token em https://brapi.dev/ e adicionar ao Spring

4. Rodar a aplicação
./mvnw spring-boot:run

5. Acessar no navegador
http://localhost:8080

# 📌 Endpoints Principais

**User:**
- (Cadastrar user) POST /v1/users {"username": "xxxx", "email": "xxxx@gmail.com", "password": "xxxx"}
- (Buscar user pelo Id) GET /v1/users/{userId}
- (Buscar todos usuários) GET /v1/users
- (Deletar user pelo Id) DELETE /v1/users/{userId}
- (Update user) PUT /v1/users/{userId} {"username": "yyyy" "password": "yyyy"}

**Account and Stock:**
- (Criar conta) PUT /v1/users/{userId}/accounts {"description": "First xxxx Account", "street": "Street of London", "number": 7}
- (Buscar contas) GET /v1/users/{userId}/accounts
- (Cadastrar Ação) POST /v1/stocks {"stockId": "PETR4", "description": "Petrobras"}
- (Associar conta<->ação) POST /v1/users/{userId}/stocks
- (Buscar ações de uma conta) GET /v1/users/{userId}/stock 
