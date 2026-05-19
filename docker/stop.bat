@echo off
title GESTOR EMPRESARIAL - STOP
color 0E

echo ========================================
echo   GESTOR EMPRESARIAL INTEGRADO
echo   Deteniendo servicios
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
:: 2. MOSTAR CONTENEDORES ACTIVOS
:: ============================================
echo [2/3] Contenedores activos actualmente:
echo.
docker compose ps
echo.

:: ============================================
:: 3. DETENER CONTENEDORES
:: ============================================
echo [3/3] Deteniendo contenedores...
echo.

echo ¿Desea eliminar tambien los volumenes de datos?
echo 1 - Solo detener (mantener datos)
echo 2 - Detener y eliminar volumenes (perder datos)
echo.
set /p opcion="Seleccione una opcion (1 o 2): "

if "%opcion%"=="2" (
    echo.
    echo ⚠️  Eliminando contenedores y volumenes...
    docker compose down -v
    echo ✅ Contenedores y volumenes eliminados
) else (
    echo.
    echo Deteniendo contenedores...
    docker compose down
    echo ✅ Contenedores detenidos
)

echo.
echo ========================================
echo   ✅ SERVICIOS DETENIDOS
echo ========================================
echo.
echo Para iniciar nuevamente: build-and-run.bat
echo.

pause