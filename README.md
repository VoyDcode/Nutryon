# Nutryon
- **Integrantes:** Renato (RM560928) • Victor (RM560087)

## 🎯 Objetivo do Projeto
O **Nutryon** é um backend em **Spring Boot** que permite **planejar refeições ao longo da semana** e **calcular automaticamente calorias e macronutrientes** (proteína, carboidrato, gordura) com base em um **banco de ingredientes** (valores por 100 g/ml).  
Foco: simplicidade, entidades relacionais claras e boas práticas de validação.

---

## 🧱 Arquitetura & Stack
- **Linguagem:** Java 21  
- **Framework:** Spring Boot 3.5.6  
- **Módulos:** Spring Web, Spring Data JPA, Bean Validation, Lombok  
- **Banco Relacional:** Oracle (FIAP)  
- **Driver:** `ojdbc11`  
- **Build:** Maven

> Healthcheck exposto em `GET /health` → retorna `nutryon-ok`.

---

## 🗂️ Modelagem (Visão Geral)
Entidades principais:
- **Usuario** (`usuarios`)
  - `id`, `nome`, `email`
- **Ingrediente** (`ingredientes`)
  - `id`, `nome` (único), `unidadeBase` (`GRAMA`, `KILOGRAMA`, `LITRO`, `ML`)
  - **valores por 100**: `kcalPor100`, `proteinaPor100`, `carboPor100`, `gorduraPor100`
- **Refeicao** (`refeicoes`)
  - `id`, `usuario`, `data`, `tipo` (`CAFE`, `ALMOCO`, `JANTAR`, `LANCHE`)
  - **restrição única**: (`usuario_id`, `data`, `tipo`)
- **ItemRefeicao** (`itens_refeicao`)
  - `id`, `refeicao`, `ingrediente`, `quantidade` *(g ou ml conforme ingrediente)*

**Cálculo de macros:** serviço `ServicoNutricao` soma os macros por item aplicando fator `quantidade / 100`.

---

## 🔌 Endpoints (REST)

### ✅ Health
- **GET** `/health` → `"nutryon-ok"`

### 👤 Usuários
- **POST** `/api/usuarios`  
  **Body**:
  ```json
  { "nome": "Victor", "email": "victor@example.com" }

