# Guia: Configurar Oracle Cloud Wallet no Backend Nutryon

> Este guia cobre a configuração do Wallet para conexão local e para o Azure.
> **Não commitar nenhum arquivo do wallet no GitHub.**

---

## 1. Baixar o Wallet

1. Acesse o [Oracle Cloud Console](https://cloud.oracle.com)
2. Menu → Oracle Database → Autonomous Database → **NUTRYONDB**
3. Clique em **DB Connection**
4. Em "Download client credentials (Wallet)", clique em **Download Wallet**
5. Defina uma senha para o wallet (guarde-a, pode precisar depois)
6. Baixe o arquivo `Wallet_NUTRYONDB.zip`

---

## 2. Extrair o Wallet (Windows)

1. Crie a pasta: `C:\oracle\wallet\NUTRYONDB`
2. Extraia **todo o conteúdo** do `Wallet_NUTRYONDB.zip` para essa pasta

Resultado esperado após extrair:
```
C:\oracle\wallet\NUTRYONDB\
  ├── cwallet.sso          ← wallet auto-login (sem senha)
  ├── ewallet.p12          ← wallet protegida por senha
  ├── keystore.jks         ← keystore Java
  ├── truststore.jks       ← truststore Java
  ├── ojdbc.properties     ← configurações JDBC
  ├── tnsnames.ora         ← aliases de conexão (contém nutryondb_high)
  └── sqlnet.ora           ← configuração de rede Oracle
```

---

## 3. Atualizar o sqlnet.ora

> Este passo é **obrigatório**. O `sqlnet.ora` baixado do Oracle Cloud aponta para um
> caminho genérico que não funciona com o driver thin JDBC.

Abra `C:\oracle\wallet\NUTRYONDB\sqlnet.ora` em um editor de texto e substitua o conteúdo por:

```
WALLET_LOCATION = (SOURCE = (METHOD = file) (METHOD_DATA = (DIRECTORY="C:\oracle\wallet\NUTRYONDB")))
SSL_SERVER_DN_MATCH=yes
```

**Importante:** o caminho no `WALLET_LOCATION` deve ser o mesmo onde você extraiu o wallet.

---

## 4. Verificar o tnsnames.ora

Abra `C:\oracle\wallet\NUTRYONDB\tnsnames.ora` e confirme que existe a entrada `nutryondb_high`.

Exemplo do que você deve ver (os valores reais vêm do Oracle Cloud):
```
nutryondb_high = (description= (retry_count=20)(retry_delay=3)(address=(protocol=tcps)(port=1522)(host=adb.sa-saopaulo-1.oraclecloud.com))(connect_data=(service_name=xxxxxxxx_nutryondb_high.adb.oraclecloud.com))(security=(ssl_server_dn_match=yes)))
```

Se `nutryondb_high` existir, a URL de conexão do backend está correta.

---

## 5. Rodar o backend localmente

### Opção A — Script PowerShell (recomendado)

Edite `run-local.ps1` na raiz do projeto com sua senha e execute:

```powershell
.\run-local.ps1
```

### Opção B — Variáveis manuais no PowerShell

```powershell
$env:SPRING_DATASOURCE_URL      = "jdbc:oracle:thin:@nutryondb_high?TNS_ADMIN=C:/oracle/wallet/NUTRYONDB"
$env:SPRING_DATASOURCE_USERNAME = "NUTRYON"
$env:SPRING_DATASOURCE_PASSWORD = "SuaSenhaAqui"
$env:SPRING_JPA_HIBERNATE_DDL_AUTO = "none"
$env:SPRING_FLYWAY_ENABLED      = "false"
$env:JWT_SECRET                 = "sua_chave_longa_aqui_minimo_64_chars"
$env:CORS_ALLOWED_ORIGINS       = "http://localhost:4200"

./mvnw spring-boot:run
```

### Opção C — IntelliJ IDEA (Run Configuration)

1. Edit Configurations → Add → Spring Boot
2. Na aba **Environment variables**, adicione:
   ```
   SPRING_DATASOURCE_URL=jdbc:oracle:thin:@nutryondb_high?TNS_ADMIN=C:/oracle/wallet/NUTRYONDB
   SPRING_DATASOURCE_USERNAME=NUTRYON
   SPRING_DATASOURCE_PASSWORD=SuaSenhaAqui
   SPRING_JPA_HIBERNATE_DDL_AUTO=none
   SPRING_FLYWAY_ENABLED=false
   JWT_SECRET=sua_chave_longa_aqui
   CORS_ALLOWED_ORIGINS=http://localhost:4200
   ```

---

## 6. Testar a conexão

Com o backend rodando em `http://localhost:8080`:

### Health check
```
GET http://localhost:8080/health
→ nutryon-ok
```

### Swagger
```
http://localhost:8080/swagger-ui/index.html
```

### Login com admin padrão (seed do DDL)
```
POST http://localhost:8080/auth/login
Content-Type: application/json

{
  "email": "admin@nutryon.com",
  "senha": "123456"
}
→ { "token": "eyJ..." }
```

### Listar ingredientes (com token)
```
GET http://localhost:8080/api/ingredientes
Authorization: Bearer eyJ...
→ Lista com 3 ingredientes do seed
```

---

## 7. Deploy no Azure — Configurar Wallet

> O wallet **não pode ser commitado no GitHub**. Existem duas opções para Azure.

### Opção A — Upload do wallet via Kudu (mais simples para o trabalho acadêmico)

1. Acesse o Azure Portal → App Service **Nutryon** → **Advanced Tools** → Go (abre o Kudu)
2. No Kudu: Debug console → CMD
3. Navegue até `site/wwwroot` (ou `/home`)
4. Crie a pasta: `mkdir wallet`
5. Faça upload dos arquivos do wallet para `site/wwwroot/wallet/`
   - Arraste e solte os arquivos do wallet na interface do Kudu
   - Arquivos necessários: `cwallet.sso`, `ewallet.p12`, `keystore.jks`, `truststore.jks`, `ojdbc.properties`, `tnsnames.ora`, `sqlnet.ora` (atualizado)
6. **Atualize o `sqlnet.ora` que foi feito upload** para usar o caminho do Azure:
   ```
   WALLET_LOCATION = (SOURCE = (METHOD = file) (METHOD_DATA = (DIRECTORY="/home/site/wwwroot/wallet")))
   SSL_SERVER_DN_MATCH=yes
   ```

### Configuração das Application Settings no Azure

No Azure Portal → App Service **Nutryon** → **Configuration** → **Application settings**:

| Nome | Valor |
|---|---|
| `SPRING_DATASOURCE_URL` | `jdbc:oracle:thin:@nutryondb_high?TNS_ADMIN=/home/site/wwwroot/wallet` |
| `SPRING_DATASOURCE_USERNAME` | `NUTRYON` |
| `SPRING_DATASOURCE_PASSWORD` | `<sua senha>` |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | `none` |
| `SPRING_FLYWAY_ENABLED` | `false` |
| `JWT_SECRET` | `<string longa aleatória>` |
| `CORS_ALLOWED_ORIGINS` | `https://ashy-ground-044d2c50f.azurestaticapps.net,http://localhost:4200` |

---

## 8. Checklist antes de fazer push

- [ ] Wallet extraído e `sqlnet.ora` atualizado localmente
- [ ] Backend sobe e conecta ao Oracle Cloud (sem erro de connection refused)
- [ ] `GET /health` retorna `nutryon-ok`
- [ ] `POST /auth/login` com admin@nutryon.com / 123456 retorna token JWT
- [ ] `GET /api/ingredientes` retorna os 3 ingredientes do seed
- [ ] `POST /api/refeicoes` cria refeição com sucesso
- [ ] Dados visíveis no SQL Developer / Oracle Cloud Console
- [ ] Wallet NÃO está commitado no GitHub (`.gitignore` contém `wallet/`)
- [ ] `.env` NÃO está commitado
- [ ] `run-local.ps1` NÃO contém senha real (está com placeholder)

---

## 9. Troubleshooting

### Erro: `ORA-29106: cannot import wallet`
→ O `sqlnet.ora` ainda aponta para o caminho genérico. Atualize conforme seção 3.

### Erro: `NL Exception was generated`
→ Caminho do wallet incorreto. Verifique se `TNS_ADMIN` bate com o diretório real.

### Erro: `ORA-01017: invalid username/password`
→ Usuário ou senha incorretos. Verifique `SPRING_DATASOURCE_USERNAME=NUTRYON` e a senha.

### Erro: `Unable to acquire JDBC Connection`
→ O banco está pausado (Autonomous DB pausa após inatividade). Acesse o Oracle Cloud Console e clique em **Start** no NUTRYONDB.

### Erro: `nutryondb_high: connection refused` / `No entry found for nutryondb_high`
→ O `tnsnames.ora` não tem essa entrada, ou o `TNS_ADMIN` está apontando para o diretório errado.

### Backend sobe mas retorna 401 em tudo
→ `JWT_SECRET` não está configurado (a variável é obrigatória, sem fallback).
