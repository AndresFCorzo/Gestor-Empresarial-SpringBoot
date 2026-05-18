@echo off
title GESTOR EMPRESARIAL API - DOCKER DEPLOY
color 0A

echo ========================================
echo   GESTOR EMPRESARIAL API
echo   Despliegue con Docker
echo ========================================
echo.

cd /d "%~dp0"

:: Verificar Docker
echo [1/5] Verificando Docker...
docker --version >nul 2>nul
if %errorlevel% neq 0 (
    echo ❌ Docker no está instalado
    echo Descargue Docker Desktop desde: https://www.docker.com/products/docker-desktop/
    pause
    exit /b 1
)
echo ✅ Docker encontrado

:: Verificar Docker Compose
echo [2/5] Verificando Docker Compose...
docker compose version >nul 2>nul
if %errorlevel% neq 0 (
    echo ❌ Docker Compose no disponible
    pause
    exit /b 1
)
echo ✅ Docker Compose disponible

:: Limpiar contenedores anteriores
echo [3/5] Limpiando contenedores anteriores...
docker compose down -v 2>nul
echo ✅ Limpieza completada

:: Construir y ejecutar
echo [4/5] Construyendo imágenes y desplegando...
docker compose up -d --build

:: Verificar estado
echo [5/5] Verificando estado...
timeout /t 10 >nul

echo.
echo ========================================
echo   ✅ DESPLIEGUE COMPLETADO
echo ========================================
echo.
echo  Servicios disponibles:
echo  📊 API: http://localhost:8080
echo  🗄️  MySQL: localhost:3306
echo  🐘 PHPMyAdmin: http://localhost:8081
echo.
echo  Comandos útiles:
echo  - Ver logs: docker compose logs -f api
echo  - Detener: docker compose down
echo  - Reiniciar: docker compose restart
echo.

pause