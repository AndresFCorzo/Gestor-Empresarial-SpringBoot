@echo off
title GESTOR EMPRESARIAL - BUILD AND DEPLOY
color 0A

echo ========================================
echo   GESTOR EMPRESARIAL INTEGRADO
echo   Construccion y despliegue con Docker
echo ========================================
echo.

cd /d "%~dp0"

:: ============================================
:: 1. VERIFICAR DOCKER
:: ============================================
echo [1/6] Verificando Docker...
docker --version >nul 2>nul
if %errorlevel% neq 0 (
    echo ❌ Docker no esta instalado
    echo.
    echo Descargue Docker Desktop desde:
    echo https://www.docker.com/products/docker-desktop/
    echo.
    pause
    exit /b 1
)
echo ✅ Docker encontrado

:: ============================================
:: 2. VERIFICAR DOCKER COMPOSE
:: ============================================
echo [2/6] Verificando Docker Compose...
docker compose version >nul 2>nul
if %errorlevel% neq 0 (
    echo ❌ Docker Compose no disponible
    pause
    exit /b 1
)
echo ✅ Docker Compose disponible

:: ============================================
:: 3. VERIFICAR ARCHIVO .ENV
:: ============================================
echo [3/6] Verificando archivo .env...
if not exist .env (
    echo ⚠️  Archivo .env no encontrado, creando archivo por defecto...
    (
        echo # Gestor Empresarial - Variables de entorno
        echo MYSQL_ROOT_PASSWORD=root
        echo MYSQL_DATABASE=gestor_empresarial
        echo DB_HOST=mysql
        echo DB_PORT=3306
        echo DB_NAME=gestor_empresarial
        echo DB_USERNAME=root
        echo DB_PASSWORD=root
        echo JWT_SECRET=gestorEmpresarialSecretKey2025
        echo JWT_EXPIRATION=86400000
        echo SPRING_PROFILES_ACTIVE=docker
        echo SERVER_PORT=8080
    ) > .env
    echo ✅ Archivo .env creado
) else (
    echo ✅ Archivo .env encontrado
)

:: ============================================
:: 4. LIMPIAR CONTENEDORES ANTERIORES
:: ============================================
echo [4/6] Limpiando contenedores anteriores...
docker compose down -v 2>nul
echo ✅ Limpieza completada

:: ============================================
:: 5. CONSTRUIR Y DESPLEGAR
:: ============================================
echo [5/6] Construyendo imagenes y desplegando...
docker compose up -d --build

if %errorlevel% neq 0 (
    echo.
    echo ❌ Error durante el despliegue
    echo.
    echo Revise los logs con: docker compose logs
    pause
    exit /b 1
)

:: ============================================
:: 6. VERIFICAR ESTADO
:: ============================================
echo [6/6] Verificando estado...
timeout /t 5 /nobreak >nul

echo.
echo ========================================
echo   ESTADO DE LOS CONTENEDORES
echo ========================================
docker compose ps

echo.
echo ========================================
echo   ✅ DESPLIEGUE COMPLETADO
echo ========================================
echo.
echo  Servicios disponibles:
echo  📊 API: http://localhost:8080
echo  🗄️  MySQL: localhost:3306
echo  🐘 PHPMyAdmin: http://localhost:8081 (perfil dev)
echo.
echo  Credenciales de acceso:
echo  👤 Usuario: admin
echo  🔑 Contraseña: admin123
echo.
echo  Comandos utiles:
echo  - Ver logs:     logs.bat
echo  - Ver estado:   status.bat
echo  - Detener:      stop.bat
echo  - Reiniciar:    restart.bat
echo  - Redeploy:     redeploy.bat
echo.

pause