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


## Diagramas
- ### UML
 <img width="805" height="664" alt="Captura de tela 2025-10-09 013118" src="https://github.com/user-attachments/assets/c131a6b7-04be-43d1-ae00-1b5afb20fde8" />


### DER
 https://drive.google.com/file/d/184qNsa_qiU7sJK-Yl02szGTKTeVdRCMz/view?usp=sharing 


---

## 🔌 Endpoints (REST)

### 👤 Usuários
- **POST** `/api/usuarios`  
  **Body**:
  ```json
  { "nome": "Victor", "email": "victor@example.com" }
<img width="1918" height="1030" alt="Captura de tela 2025-10-09 043829" src="https://github.com/user-attachments/assets/1639320d-3697-44a9-801f-c3b34cf0b2e4" />

> - **GET**
 
<img width="1918" height="1028" alt="Captura de tela 2025-10-09 043913" src="https://github.com/user-attachments/assets/ae2745a0-d392-4612-80c4-26d5cf1d6732" />

### tratamento de Duplicidade
<img width="1917" height="1001" alt="Captura de tela 2025-10-09 050628" src="https://github.com/user-attachments/assets/932e25b6-2e3b-4edf-bfb3-fa3e85479378" />



# Conexão com banco de dados
  ```properties
spring.datasource.url=jdbc:oracle:thin:@//<HOST>:<PORT>/<SERVICE_NAME>
spring.datasource.username=<USUARIO>
spring.datasource.password=<SENHA>
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.OracleDialect
spring.jpa.show-sql=true 
```

# Vídeo — Proposta Tecnológica, Público-alvo e Problema
> link ➡ https://drive.google.com/drive/folders/1JytEqJumGWoHfoypQXreFViqLMSov2mx?usp=sharing

