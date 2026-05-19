@echo off
title GESTOR EMPRESARIAL - RESTART
color 0F

echo ========================================
echo   GESTOR EMPRESARIAL INTEGRADO
echo   Reiniciando servicios
echo ========================================
echo.

cd /d "%~dp0"

:: ============================================
:: 1. VERIFICAR DOCKER
:: ============================================
echo [1/3] Verificando Docker...
docker ps >nul 2>nul
if %errorlevel% neq 0 (
    echo ❌ Docker no esta corriendo
    pause
    exit /b 1
)
echo ✅ Docker esta corriendo

:: ============================================
:: 2. REINICIAR SERVICIOS
:: ============================================
echo [2/3] Reiniciando servicios...
docker compose restart

if %errorlevel% neq 0 (
    echo ❌ Error al reiniciar servicios
    pause
    exit /b 1
)

:: ============================================
:: 3. VERIFICAR ESTADO
:: ============================================
echo [3/3] Verificando estado...
timeout /t 5 /nobreak >nul

echo.
echo ========================================
echo   ESTADO DE LOS CONTENEDORES
echo ========================================
docker compose ps

echo.
echo ========================================
echo   ✅ SERVICIOS REINICIADOS
echo ========================================
echo.
echo API disponible en: http://localhost:8080
echo.

pause