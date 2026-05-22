# ==============================================================
# run-local.ps1 -- Script para rodar o Nutryon backend localmente
# conectado ao Oracle Cloud Autonomous Database (NUTRYONDB)
#
# COMO USAR:
#   1. Edite as variaveis abaixo com suas credenciais reais
#   2. No PowerShell: .\run-local.ps1
#   3. Acesse: http://localhost:8080/swagger-ui/index.html
#
# PRE-REQUISITOS:
#   - Java 17+ instalado e no PATH
#   - Wallet do Oracle Cloud extraido na pasta indicada em WALLET_PATH
#   - sqlnet.ora deve apontar para o mesmo WALLET_PATH
# ==============================================================

# --- CONFIGURE AQUI ---

# Caminho onde voce extraiu o Wallet_NUTRYONDB.zip
$WALLET_PATH = "C:/oracle/wallet/NUTRYONDB"

# Usuario e senha do schema Oracle Cloud
$DB_USER = "NUTRYON"
$DB_PASS = "SUA_SENHA_AQUI"   # Preencha com a senha do schema NUTRYON no Oracle Cloud

# JWT Secret (qualquer string longa)
$JWT = "nutryon-jwt-secret-chave-super-secreta-para-desenvolvimento-local-2026"

# CORS -- origens permitidas
$CORS = "http://localhost:4200"

# --- FIM DA CONFIGURACAO ---

$env:SPRING_DATASOURCE_URL         = "jdbc:oracle:thin:@nutryondb_high?TNS_ADMIN=$WALLET_PATH"
$env:SPRING_DATASOURCE_USERNAME    = $DB_USER
$env:SPRING_DATASOURCE_PASSWORD    = $DB_PASS
$env:SPRING_JPA_HIBERNATE_DDL_AUTO = "none"
$env:SPRING_JPA_SHOW_SQL           = "false"
$env:SPRING_FLYWAY_ENABLED         = "false"
$env:JWT_SECRET                    = $JWT
$env:CORS_ALLOWED_ORIGINS          = $CORS

Write-Host ""
Write-Host "=== Nutryon Backend -- Oracle Cloud Local ===" -ForegroundColor Cyan
Write-Host "Banco  : Oracle Cloud Autonomous Database (NUTRYONDB)" -ForegroundColor Green
Write-Host "URL    : $env:SPRING_DATASOURCE_URL" -ForegroundColor Yellow
Write-Host "User   : $DB_USER" -ForegroundColor Yellow
Write-Host "Swagger: http://localhost:8080/swagger-ui/index.html" -ForegroundColor Green
Write-Host ""
Write-Host "Aguarde 'Started NutryonAplicacao' para acessar a API..." -ForegroundColor Cyan
Write-Host "Ignore o aviso 'HHH000342' -- e nao-fatal." -ForegroundColor DarkGray
Write-Host ""

.\mvnw spring-boot:run
