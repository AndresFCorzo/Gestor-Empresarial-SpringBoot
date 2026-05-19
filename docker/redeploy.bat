@echo off
title GESTOR EMPRESARIAL - REDEPLOY
color 0B

echo ========================================
echo   GESTOR EMPRESARIAL INTEGRADO
echo   Reconstruccion y redespliegue
echo ========================================
echo.

cd /d "%~dp0"

:: ============================================
:: 1. VERIFICAR DOCKER
:: ============================================
echo [1/6] Verificando Docker...
docker ps >nul 2>nul
if %errorlevel% neq 0 (
    echo ❌ Docker no esta corriendo
    pause
    exit /b 1
)
echo ✅ Docker esta corriendo

:: ============================================
:: 2. VERIFICAR ARCHIVO .ENV
:: ============================================
echo [2/6] Verificando archivo .env...
if not exist .env (
    echo ❌ Archivo .env no encontrado
    echo Ejecute primero: build-and-run.bat
    pause
    exit /b 1
)
echo ✅ Archivo .env encontrado

:: ============================================
:: 3. LIMPIAR CONTENEDORES ANTERIORES
:: ============================================
echo [3/6] Limpiando contenedores anteriores...
docker compose down -v
echo ✅ Limpieza completada

:: ============================================
:: 4. LIMPIAR CACHE DE DOCKER
:: ============================================
echo [4/6] Limpiando cache de Docker...
docker system prune -f
echo ✅ Cache limpiado

:: ============================================
:: 5. RECONSTRUIR IMAGENES
:: ============================================
echo [5/6] Reconstruyendo imagenes...
docker compose build --no-cache

if %errorlevel% neq 0 (
    echo ❌ Error al reconstruir las imagenes
    pause
    exit /b 1
)
echo ✅ Imagenes reconstruidas

:: ============================================
:: 6. DESPLEGAR
:: ============================================
echo [6/6] Desplegando servicios...
docker compose up -d

if %errorlevel% neq 0 (
    echo ❌ Error al desplegar
    pause
    exit /b 1
)

:: Esperar a que los servicios inicien
timeout /t 5 /nobreak >nul

echo.
echo ========================================
echo   ESTADO DE LOS CONTENEDORES
echo ========================================
docker compose ps

echo.
echo ========================================
echo   ✅ REDEPLOY COMPLETADO
echo ========================================
echo.
echo API disponible en: http://localhost:8080
echo.
echo Para ver logs: logs.bat
echo.

pause