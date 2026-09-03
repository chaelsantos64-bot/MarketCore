# 🛒 MarketCore

Backend de um sistema de vendas/e-commerce desenvolvido em **Java**, criado como projeto contínuo de estudos para aplicar conceitos de desenvolvimento backend e Engenharia de Software na prática.

O MarketCore começou como um projeto focado em Programação Orientada a Objetos e está evoluindo gradualmente para uma aplicação estruturada em camadas, com persistência de dados utilizando **JDBC e MySQL**.

---

## 🎯 Objetivo

O objetivo do MarketCore é construir e evoluir um sistema backend enquanto aplico conceitos estudados em Java e Engenharia de Software.

Além da implementação das funcionalidades, o projeto busca desenvolver conhecimentos em:

- organização e arquitetura de software;
- separação de responsabilidades;
- modelagem de domínio;
- regras de negócio;
- persistência de dados;
- banco de dados relacional;
- análise de requisitos;
- versionamento com Git.

---

## ⚙️ Funcionalidades atuais

### 👤 Clientes

- Cadastro de clientes
- Busca de cliente por ID
- Atualização de dados
- Listagem
- Exclusão
- Persistência no MySQL

### 📦 Produtos

- Cadastro de produtos
- Busca por ID
- Atualização
- Listagem
- Exclusão
- Controle de estoque
- Validação de estoque insuficiente
- Persistência no MySQL

### 🛒 Carrinho

- Adição de itens
- Controle de quantidade por item
- Cálculo do valor total
- Finalização da compra
- Redução de estoque
- Limpeza do carrinho
- Validação de carrinho vazio

### 🧾 Pedidos

- Criação de pedidos
- Associação do pedido com cliente
- Busca por ID
- Listagem de pedidos
- Atualização
- Exclusão
- Controle de status
- Finalização de pedidos
- Persistência através de Repository
- Integração entre `PedidoService` e `PedidoRepository`

### 📋 Itens do Pedido

- Cadastro de itens do pedido
- Associação com pedido
- Associação com produto
- Quantidade do produto
- Preço unitário
- Busca de itens por pedido
- Atualização
- Exclusão
- Persistência no MySQL

---

## 🏗️ Arquitetura atual

O MarketCore está sendo organizado seguindo separação de responsabilidades entre as camadas da aplicação.

```text
Main / Teste
     ↓
Service
     ↓
Repository
     ↓
JDBC
     ↓
MySQL
```

### Domain / Model

Representa os objetos e conceitos principais do sistema.

Exemplos:

```text
Cliente
Produto
Carrinho
ItemCarrinho
Pedido
ItemPedido
StatusPedido
```

### Service

Responsável pelas regras de negócio e pela coordenação das operações.

Exemplo do fluxo de criação de pedido:

```text
PedidoService
     ↓
valida o carrinho
     ↓
calcula o valor total
     ↓
cria o Pedido
     ↓
PedidoRepository
```

### Repository

Responsável pelas operações de persistência e recuperação dos dados.

Exemplos:

```text
cadastrarPedido()
buscarPedido()
listarTodosPedidos()
atualizarPedido()
excluirPedido()
```

### JDBC

Realiza a comunicação entre a aplicação Java e o MySQL utilizando:

- `Connection`
- `PreparedStatement`
- `ResultSet`
- SQL parametrizado
- `try-with-resources`

### MySQL

Responsável pela persistência dos dados da aplicação.

O banco utiliza relacionamentos, Primary Keys e Foreign Keys para manter a integridade dos dados.

---

## 🔄 Fluxo de uma operação

Exemplo simplificado da criação de um pedido:

```text
Main
 ↓
PedidoService.criarPedido()
 ↓
validação das regras de negócio
 ↓
PedidoRepository.cadastrarPedido()
 ↓
JDBC
 ↓
INSERT
 ↓
MySQL
```

Na busca, o caminho também retorna os dados:

```text
Main
 ↓
PedidoService
 ↓
PedidoRepository
 ↓
SELECT
 ↓
MySQL
 ↓
ResultSet
 ↓
Objeto Pedido
 ↓
Service
 ↓
Main
```

---

## 🗃️ CRUD

O projeto implementa operações CRUD utilizando Java, JDBC e SQL.

| Operação | SQL | Exemplo |
|---|---|---|
| Create | `INSERT` | Cadastrar pedido |
| Read | `SELECT` | Buscar/listar pedidos |
| Update | `UPDATE` | Atualizar status |
| Delete | `DELETE` | Excluir pedido |

---

## 🔗 Relacionamentos

O domínio possui relacionamentos entre as principais entidades.

```text
Cliente
   │
   ▼
Pedido
   │
   ▼
ItemPedido
   │
   ▼
Produto
```

Exemplo:

```text
Cliente #