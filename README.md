# Nutryon - Backend API 🚀

> Sistema inteligente de planejamento nutricional e controle de macronutrientes.

---

## 👥 Integrantes e RMs
*   **Renato** — RM560928
*   **Victor** — RM560087
*   **Luan Noqueli Klochko** — RM560313
*   **Lucas Higuti Fontanezi** — RM561120
---

## 🎯 Objetivo do Projeto
O **Nutryon** é uma solução robusta em **Spring Boot** desenvolvida para auxiliar no planejamento de refeições semanais. O sistema calcula automaticamente calorias e macronutrientes (proteína, carboidrato e gordura) com base em um banco de dados de ingredientes normalizado, permitindo que usuários monitorem sua dieta de forma técnica e precisa.

---

## 🔗 Projetos Conectados (Ecosistema)
Esta API faz parte de uma solução Full-Stack. O cliente de visualização pode ser encontrado no link abaixo:

*   **Frontend (Angular):** [Nutryon-angular](https://github.com/VoyDcode/Nutryon-angular)

---

## 🧱 Arquitetura e Tech Stack
*   **Linguagem:** Java 17
*   **Framework:** Spring Boot 3.5.x
*   **Segurança:** Spring Security + JWT (Auth0)
*   **Persistência:** Spring Data JPA / Hibernate
*   **Migrations:** Flyway DB
*   **Banco de Dados:** Oracle Database (OJDBC11)
*   **Documentação:** Swagger UI / OpenAPI 3

---

## 🛠️ Instruções de Instalação e Execução

### Pré-requisitos
*   **Java 17** ou superior instalado.
*   **Maven 3.x** instalado.
*   Acesso ao banco de dados **Oracle FIAP** (ou uma instância local configurada).

### Passo 1: Configuração do Banco de Dados
```properties
spring.datasource.url=jdbc:oracle:thin:@//oracle.fiap.com.br:1521/ORCL
spring.datasource.username=seu_rm
spring.datasource.password=sua_senha
```

### Passo 2: Instalação das Dependências
No terminal, na raiz do projeto, execute:
```bash
./mvnw clean install
```

### Passo 3: Execução da Aplicação
Para subir o servidor:
```bash
./mvnw spring-boot:run
```
O servidor iniciará por padrão na porta **8080**.
---

## 📖 Acesso e Documentação

### Documentação Interativa (Swagger)
Assim que a aplicação estiver rodando, acesse a interface do Swagger para testar os endpoints em tempo real:
👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Fluxo de Autenticação para Testes
1.  **Registro:** Utilize o endpoint `POST /auth/register` para criar um usuário (ex: role `ADMIN`).
2.  **Login:** Utilize `POST /auth/login` para receber seu **Bearer Token**.
3.  **Autorização:** No Swagger, clique no botão **Authorize 🔒** no topo da página e cole o token recebido.

---

## 📽️ Demonstração (Vídeo)
Apresentação da proposta tecnológica, público-alvo e solução do problema:
👉 [Link para o Vídeo no Google Drive]([https://drive.google.com/drive/folders/1JytEqJumGWoHfoypQXreFViqLMSov2mx?usp=sharing](https://youtu.be/ro4DpbkZ_bY))

---

## 🐳 Docker (Em Breve)
A aplicação está sendo preparada para conteinerização. O arquivo `docker-compose.yml` será disponibilizado na próxima etapa para facilitar o deploy unificado (Backend + Frontend + DB).
