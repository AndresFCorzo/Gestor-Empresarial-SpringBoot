@echo off
title GESTOR EMPRESARIAL - LOGS
color 0C

echo ========================================
echo   GESTOR EMPRESARIAL INTEGRADO
echo   Visualizacion de logs
echo ========================================
echo.

cd /d "%~dp0"

:: ============================================
:: 1. VERIFICAR DOCKER
:: ============================================
echo [1/2] Verificando Docker...
docker ps >nul 2>nul
if %errorlevel% neq 0 (
    echo ❌ Docker no esta corriendo
    pause
    exit /b 1
)
echo ✅ Docker esta corriendo

:: ============================================
:: 2. SELECCIONAR SERVICIO
:: ============================================
echo [2/2] Seleccione el servicio para ver logs:
echo.
echo 1 - API (gestor-api)
echo 2 - MySQL (gestor-mysql)
echo 3 - PHPMyAdmin (gestor-phpmyadmin)
echo 4 - Todos los servicios
echo.
set /p servicio="Seleccione una opcion (1-4): "

echo.
echo ========================================
echo   MOSTRANDO LOGS (Presiona Ctrl+C para salir)
echo ========================================
echo.

if "%servicio%"=="1" (
    docker compose logs -f api
) else if "%servicio%"=="2" (
    docker compose logs -f mysql
) else if "%servicio%"=="3" (
    docker compose logs -f phpmyadmin
) else if "%servicio%"=="4" (
    docker compose logs -f
) else (
    echo ❌ Opcion invalida
    echo.
    echo Ejecute: docker compose logs -f [servicio]
)

pause