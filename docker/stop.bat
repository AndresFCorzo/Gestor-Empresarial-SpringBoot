@echo off
title DETENER GESTOR EMPRESARIAL API
color 0E

echo ========================================
echo   Deteniendo Gestor Empresarial API
echo ========================================
echo.

cd /d "%~dp0"

docker compose down

echo.
echo ✅ Contenedores detenidos
echo.
pause