@echo off
title GESTOR EMPRESARIAL - STATUS
color 0D

echo ========================================
echo   GESTOR EMPRESARIAL INTEGRADO
echo   Estado de servicios
echo ========================================
echo.

cd /d "%~dp0"

:: ============================================
:: 1. VERIFICAR DOCKER
:: ============================================
echo [1/4] Verificando Docker...
docker ps >nul 2>nul
if %errorlevel% neq 0 (
    echo ❌ Docker no esta corriendo
    echo.
    echo Inicie Docker Desktop manualmente
    pause
    exit /b 1
)
echo ✅ Docker esta corriendo

:: ============================================
:: 2. MOSTRAR CONTENEDORES
:: ============================================
echo [2/4] Contenedores activos:
echo.
docker compose ps

:: ============================================
:: 3. PROBAR API
:: ============================================
echo [3/4] Probando conexion a la API...
echo.

:: Probar endpoint de verificacion
curl -s http://localhost:8080/api/auth/verify >nul 2>nul

if %errorlevel% equ 0 (
    echo ✅ API esta respondiendo
) else (
    echo ❌ API no responde - Puede que no este corriendo
)

:: ============================================
:: 4. PROBAR LOGIN
:: ============================================
echo [4/4] Probando autenticacion...
echo.

:: Probar login
curl -s -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "{\"username\":\"admin\",\"password\":\"admin123\"}" > temp.json 2>nul

findstr "token" temp.json >nul
if %errorlevel% equ 0 (
    echo ✅ Autenticacion funcionando correctamente
) else (
    echo ⚠️  Autenticacion no disponible
)
del temp.json 2>nul

echo.
echo ========================================
echo   INFORMACION ADICIONAL
echo ========================================
echo.
echo  API URL: http://localhost:8080
echo  MySQL: localhost:3306
echo.
echo  Credenciales:
echo  Usuario: admin
echo  Contraseña: admin123
echo.
echo  Comandos utiles:
echo  - Ver logs:     logs.bat
echo  - Detener:      stop.bat
echo  - Reiniciar:    restart.bat
echo  - Redeploy:     redeploy.bat
echo.

pause