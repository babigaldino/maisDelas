## 🌸 +Delas — Backend API

API backend do **+Delas**, uma plataforma criada para **conectar mulheres prestadoras de serviços a clientes**, promovendo autonomia financeira, flexibilidade e visibilidade profissional.

Este repositório representa a **nova versão do backend**, reestruturada com foco em **boas práticas, segurança, escalabilidade e deploy em nuvem**.

---

## 🎯 Propósito do Projeto

O +Delas nasce para enfrentar problemas reais vividos por mulheres no mercado de trabalho, como:

* Falta de oportunidades e visibilidade
* Dificuldade de conciliar trabalho e maternidade
* Dependência de horários rígidos e modelos tradicionais de emprego

A plataforma permite que mulheres **divulguem seus serviços**, **gerenciem sua disponibilidade** e **negociem diretamente com clientes**, fortalecendo a economia local e o empoderamento feminino.

---

## 🚀 Stack Tecnológica

* **Java 17+**
* **Spring Boot**

  * Spring Web
  * Spring Data JPA
  * Spring Security
* **PostgreSQL**
* **JWT** (autenticação baseada em token)
* **Maven**
* **Swagger / SpringDoc** (documentação da API)
* **Docker** (ambiente local e produção)
* **Cloudinary** (upload de imagens)
* **Brevo (Sendinblue)** (envio de e-mails)
* **Deploy:** Render / Railway / similares

---

## 🧱 Arquitetura do Projeto

```
src/
 └── main/
     ├── java/
     │   └── com.example.maisdelas/
     │       ├── controller/   # Endpoints REST
     │       ├── service/      # Regras de negócio
     │       ├── repository/   # Persistência de dados
     │       ├── model/        # Entidades JPA
     │       ├── dto/          # DTOs
     │       └── security/     # JWT e configurações de segurança
     └── resources/
         └── application.properties
```

> 🔐 Todas as credenciais e segredos são fornecidos **exclusivamente via variáveis de ambiente**.

---

## 🔐 Autenticação e Segurança

* Autenticação via **JWT**
* Rotas protegidas exigem o header:

```http
Authorization: Bearer <TOKEN_JWT>
```

* Tokens possuem tempo de expiração configurável

---

## ▶️ Executando o Projeto Localmente

### Pré-requisitos

* Java 17+
* Maven ou Maven Wrapper (`./mvnw`)
* PostgreSQL (ou banco via Docker)

---

### Variáveis de Ambiente

Configure as seguintes variáveis no seu sistema ou arquivo `.env`:

```env
DB_URL=jdbc:postgresql://HOST:5432/DB
DB_USER=USER
DB_PASSWORD=PASSWORD

JWT_SECRET=uma_chave_segura
JWT_EXPIRATION=3600000

BREVO_API_KEY=xxxx
FROM_EMAIL=contato@maisdelas.com
FROM_NAME=+Delas

CLOUDINARY_API_KEY=xxxx
CLOUDINARY_API_SECRET=xxxx
CLOUDINARY_CLOUD_NAME=xxxx

PORT=8080
```

---

A aplicação ficará disponível em:

```
http://localhost:8080
```

---

## 📘 Documentação da API

Após subir o projeto, acesse:

```
http://localhost:8080/swagger-ui.html
```

---

## 🗄️ Modelo de Dados (Resumo)

* Usuários (clientes e prestadoras)
* Serviços
* Contratações
* Avaliações
* Favoritos

O relacionamento entre entidades segue um modelo relacional focado em **contratação direta e reputação baseada em avaliações**.

---

## 🧪 Boas Práticas Adotadas

* ❌ Nenhuma credencial versionada
* ✅ Configuração por variáveis de ambiente
* ✅ Separação clara de camadas (Controller / Service / Repository)
* ✅ DTOs para entrada e saída de dados
* ❌ `target/` ignorado no versionamento

---

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch:

```bash
git checkout -b feature/minha-feature
```

3. Commit suas alterações:

```bash
git commit -m "Minha feature"
```

4. Push:

```bash
git push origin feature/minha-feature
```

5. Abra um Pull Request 🚀

---
