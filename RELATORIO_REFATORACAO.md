# Relatório de Refatoração e Aplicação de Boas Práticas 🚀

Este documento consolida as alterações arquiteturais e correções de projeto realizadas nesta Sprint em resposta ao último feedback técnico: *"A entrega atende parcialmente os requisitos. Código apresenta acoplamento e quebra de boas práticas de programação."*

## 1. Visão Geral do Desacoplamento (Clean Architecture)

A estrutura anterior do Back-End unificava as regras de negócio junto com a recepção das requisições HTTP, estabelecendo um Altíssimo Acoplamento entre o `Web Controller` e o Banco de Dados. Isso violava a premissa de Responsabilidade Única (SRP) dos princípios SOLID.

Nesta refatoração fomos além, aplicando o padrão Múltiplas Camadas de Ouro do desenvolvimento corporativo Spring Boot MVC. O Fluxo de dados saltou de um tráfego de mão dupla para um **Tráfego Seguro em Camadas**:
- **Antes**: `Web/Insomnia` -> `Controller` -> `Repository` -> `Banco`
- **Agora (Refatorado)**: `Web/Insomnia` -> `Controller` -> `Service` -> `Repository` -> `Banco`

## 2. Padrões de Refatoração Adotados

| Feedback do Professor | Como Operava Antigamente | Como a Aplicação Resolve na Nova Sprint? |
| :--- | :--- | :--- |
| **Alto Acoplamento de Classes** | As requisições caiam em Controladores (`UsuarioControlador`, `RefeicaoControlador`...) que exerciam o próprio fluxo de instanciar o Entity, consultar IDs no banco e orquestrar salvamentos nativos pelo método `.save()`. | Implementada Camada Intermediária. Classes Controladoras encurtadas (Delegadoras puras). Toda lógica de negócio, criação, inserção e orquestração vive unicamente nos novos `UsuarioService`, `IngredienteService` e `RefeicaoService`. |
| **Quebra de Segurança de Dados** | Todos os métodos de requisições GET (`list`, e afins) retornavam Modelagens Puristas do Hibernate / JPA, expondo toda a Tabela de Banco em Arrays JSON completos (Exposição Insegura de Entidade). | Padrão DTO blindado. Foram estabelecidos `UsuarioViewDTO` e `IngredienteViewDTO`. O cliente Web só percebe fatias de Informação segura. A persistência mapeada fica fechada internamente na estrutura da aplicação. |
| **Baixa Performance Computacional (Query N+1 Problem)** | O Controlador da Refeição elaborava um `for do Java` sobre 7 dias, lançando para o Banco via Hibernate uma pesquisa solitária por vez. Se esmagasse, eram 7 Hits em BD por usuário! | Resolvido! Construímos o conector `findByUsuarioIdAndDataBetween` no `RefeicaoRepositorio`. Ele consolida uma pescaria unificada de datas ponta a ponta na query SQL (Múltiplas Refeicoes extraídas com extrema rapidez via `.stream().collect(groupingBy...)`). |

## 3. Impacto Visual da Evolução (O Antes e Depois)

**Como programávamos (Iniciante):**
```java
// O Código de criação e manipulação ocorrem debaixo dos panos Web e batem direto no HW
@PostMapping
public Usuario create(@RequestBody CriarUsuarioDTO dto){
   var u = new Usuario(); // O Controlador instanciando a entidade
   u.setNome(dto.nome()); // Controller modelando a entidade
   return repository.save(u); // Retorno direto de JPA Object Web!
}
```

**Grau de Maturidade Atingido no Código Atual (Avançado):**
```java
// Controller
@PostMapping
public UsuarioViewDTO create(@Valid @RequestBody CriarUsuarioDTO dto){
   return service.create(dto); // Desacoplado, isolado, blindado
}

// Service (Camada Especializada)
@Transactional
public UsuarioViewDTO create(CriarUsuarioDTO dto) {
   var u = new Usuario();
   u.setNome(dto.nome());
   // Processamentos de negócio e validações entram unicamente aqui
   var salvo = repository.save(u);
   return new UsuarioViewDTO(salvo);
}
```

---
*Assinado: Equipe de Nutryon / Victor Rodrigues 560087*
*Este documento comprova o salto lógico de um projeto simples para um projeto Profissional corporativamente funcional antes da apresentação da Próxima Sprint.*
