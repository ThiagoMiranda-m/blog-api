<div align="center">

<h1 align="center">🚀 Blog API</h1>

<p align="center">
  <strong>Uma API RESTful completa e segura para uma plataforma de blog, construída com o ecossistema Spring.</strong>
</p>

<p align="center">
  <a href="#-tecnologias">Tecnologias</a> •
  <a href="#-features">Features</a> •
  <a href="#-pré-requisitos">Pré-requisitos</a> •
  <a href="#-como-executar">Como Executar</a> •
  <a href="#-documentação-da-api">API Endpoints</a> •
  <a href="#-estrutura-do-projeto">Estrutura</a>
</p>

<p align="center">
    <img src="https://img.shields.io/badge/Java-21-blue?logo=java&logoColor=white" alt="Java 21">
    <img src="https://img.shields.io/badge/Spring_Boot-3.5.6-brightgreen?logo=spring-boot&logoColor=white" alt="Spring Boot 3.5.6">
    <img src="https://img.shields.io/badge/Spring_Security-6.x-blue?logo=spring-security&logoColor=white" alt="Spring Security 6.x">
    <img src="https://img.shields.io/badge/Maven-4.0.0-red?logo=apache-maven&logoColor=white" alt="Maven">
    <img src="https://img.shields.io/badge/JWT-Authentication-orange?logo=json-web-tokens&logoColor=white" alt="JWT">
    <img src="https://img.shields.io/badge/H2-Database-lightgrey?logo=h2&logoColor=white" alt="H2 Database">
</p>

</div>

---

## 🎯 Sobre o Projeto

A **Blog API** é um back-end robusto que serve como a espinha dorsal para qualquer aplicação de blog. Ela gerencia usuários, autenticação e, claro, os posts. A arquitetura foi projetada para ser escalável, segura e fácil de manter, utilizando as melhores práticas do desenvolvimento com Spring Boot.

---

## ✨ Features

-   🔐 **Autenticação e Autorização com JWT**: Sistema de registro e login seguro que gera um token JWT para autorizar o acesso a rotas protegidas, garantindo que apenas usuários autorizados realizem operações críticas.
-   👥 **Gerenciamento de Usuários**: Cadastro de novos usuários com papéis (roles) de `USER` ou `ADMIN`, permitindo um controle de acesso granular.
-   ✍️ **CRUD Completo de Posts**:
    -   **Criar**: Usuários autenticados podem criar novos posts.
    -   **Ler**: Leitura de todos os posts ou de um post específico por ID (endpoints públicos).
    -   **Atualizar**: Apenas o autor do post pode editar seu conteúdo.
    -   **Deletar**: Apenas o autor ou um `ADMIN` podem deletar um post.
-   💾 **Banco de Dados em Memória**: Utiliza o H2 Database, ideal para ambientes de desenvolvimento e testes, sem a necessidade de configurar um banco de dados externo.
-   ✔️ **Validação de Dados**: Validações robustas para garantir a integridade dos dados que chegam à API.
-   🛡️ **Tratamento de Exceções**: Respostas de erro claras e padronizadas para recursos não encontrados (`404`) ou acesso negado (`403`).

---

## 🛠️ Tecnologias

O projeto foi construído utilizando as seguintes tecnologias:

| Tecnologia         | Descrição                                         |
| ------------------ | --------------------------------------------------- |
| **Java 21** | Versão mais recente do Java (LTS).                  |
| **Spring Boot** | Framework principal para criação da aplicação.      |
| **Spring Security**| Implementação da autenticação e autorização.      |
| **Spring Data JPA**| Camada de persistência de dados.                    |
| **Hibernate** | Implementação da especificação JPA.               |
| **H2 Database** | Banco de dados relacional em memória.               |
| **Maven** | Gerenciamento de dependências e build do projeto.   |
| **JWT** | Geração e validação de tokens de acesso.          |

---

## 📋 Pré-requisitos

Antes de começar, você precisará ter as seguintes ferramentas instaladas em sua máquina:
-   [**JDK 21**](https://www.oracle.com/java/technologies/downloads/#java21) ou superior.
-   [**Apache Maven**](https://maven.apache.org/download.cgi) 3.5 ou superior.
-   Uma IDE de sua preferência (ex: **IntelliJ IDEA**, **VS Code**, etc.).
-   Um cliente de API, como o **[Postman](https://www.postman.com/downloads/)** ou **[Insomnia](https://insomnia.rest/download)**.

---

## 🚀 Como Executar

Siga os passos abaixo para executar o projeto localmente.

1.  **Clone o repositório:**
    ```bash
    git clone <url-do-seu-repositorio>
    cd blog-api
    ```

2.  **Execute a aplicação com o Maven Wrapper:**
    *O Maven Wrapper (`mvnw`) já está incluído no projeto, então você não precisa ter o Maven instalado globalmente.*
    ```bash
    ./mvnw spring-boot:run
    ```

3.  **Acesse o Console do Banco H2:**
    Com a aplicação rodando, abra seu navegador e acesse:
    ➡️ `http://localhost:8080/h2-console`

    Use as seguintes configurações para conectar:
    -   **Driver Class**: `org.h2.Driver`
    -   **JDBC URL**: `jdbc:h2:mem:testdb`
    -   **User Name**: `sa`
    -   **Password**: (deixe em branco)

A API estará rodando em `http://localhost:8080`.

---

##  API Endpoints

A seguir estão detalhados os endpoints disponíveis na API.

**URL Base**: `http://localhost:8080`

<details>
<summary><strong>🔑 Autenticação</strong></summary>

#### 1. Registrar um Novo Usuário
-   **Método**: `POST`
-   **Endpoint**: `/api/auth/register`
-   **Descrição**: Cria um novo usuário no sistema.
-   **Corpo da Requisição (`JSON`):**
    ```json
    {
      "username": "novo_usuario",
      "password": "senha_forte_123",
      "role": "USER"
    }
    ```
-   **Resposta de Sucesso (200 OK):** Retorna o token JWT para o usuário recém-criado.
    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiJ9..."
    }
    ```

#### 2. Autenticar um Usuário (Login)
-   **Método**: `POST`
-   **Endpoint**: `/api/auth/login`
-   **Descrição**: Autentica um usuário e retorna um token JWT para ser usado nas requisições protegidas.
-   **Corpo da Requisição (`JSON`):**
    ```json
    {
      "username": "novo_usuario",
      "password": "senha_forte_123"
    }
    ```
-   **Resposta de Sucesso (200 OK):**
    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiJ9..."
    }
    ```
</details>

<details>
<summary><strong>📄 Posts</strong></summary>

#### 3. Listar Todos os Posts
-   **Método**: `GET`
-   **Endpoint**: `/api/posts`
-   **Autenticação**: Não requerida.
-   **Resposta de Sucesso (200 OK):** Retorna uma lista com todos os posts.
    ```json
    [
      {
        "id": 1,
        "title": "Meu Primeiro Post",
        "content": "Este é o conteúdo do meu primeiro post.",
        "createdAt": "2025-10-02T10:30:00",
        "authorUsername": "autor_do_post"
      }
    ]
    ```

#### 4. Buscar um Post por ID
-   **Método**: `GET`
-   **Endpoint**: `/api
  
---

## 👨‍💻 Autor

Desenvolvido por **Thiago André Neves Miranda**.

-   [![LinkedIn](https://img.shields.io/badge/LinkedIn-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/tanm-dev/)
-   [![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/[SEU-USUARIO-GITHUB])

## 📄 Licença

Este projeto está sob a licença MIT.
