@echo off
echo ========================================
echo   DIAGNOSTICO DE MAVEN
echo ========================================
echo.

echo [1] Verificando MAVEN_HOME...
if defined MAVEN_HOME (
    echo MAVEN_HOME = %MAVEN_HOME%
) else (
    echo MAVEN_HOME NO DEFINIDA
)

echo.
echo [2] Verificando M2_HOME...
if defined M2_HOME (
    echo M2_HOME = %M2_HOME%
) else (
    echo M2_HOME NO DEFINIDA
)

echo.
echo [3] Verificando PATH...
echo %PATH% | findstr maven >nul
if %errorlevel% equ 0 (
    echo Maven encontrado en PATH
) else (
    echo Maven NO encontrado en PATH
)

echo.
echo [4] Verificando carpeta de Maven...
if exist "C:\apache-maven-3.9.16\bin\mvn.cmd" (
    echo ✅ Maven encontrado en C:\apache-maven-3.9.16
) else (
    echo ❌ Maven NO encontrado en C:\apache-maven-3.9.16
)

echo.
echo [5] Probando ejecución directa...
if exist "C:\apache-maven-3.9.16\bin\mvn.cmd" (
    echo Probando mvn -version...
    "C:\apache-maven-3.9.16\bin\mvn.cmd" -version
) else (
    echo No se puede probar - Maven no encontrado
)

echo.
pause