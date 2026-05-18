#!/bin/bash
# =====================================================
# ENTRYPOINT SCRIPT - GESTOR EMPRESARIAL API
# =====================================================
#
# Este script se ejecuta al iniciar el contenedor
# y prepara el entorno antes de iniciar la aplicación
#
# @author Andres Felipe Corzo Angarita
# @author Thomas Felipe Colmenares Perdomo
# =====================================================

set -e

echo "========================================="
echo "  GESTOR EMPRESARIAL API"
echo "  Iniciando servicio..."
echo "========================================="

# Esperar a que MySQL esté listo
echo "[1/4] Esperando por MySQL..."
while ! mysqladmin ping -h"$DB_HOST" --silent; do
    echo "    Aguardando conexión a MySQL..."
    sleep 2
done
echo "    ✅ MySQL disponible"

# Verificar si la base de datos está inicializada
echo "[2/4] Verificando base de datos..."
mysql -h"$DB_HOST" -u"$DB_USERNAME" -p"$DB_PASSWORD" -e "USE $DB_NAME" 2>/dev/null
if [ $? -ne 0 ]; then
    echo "    ⚠️  Base de datos no existe, creando..."
    mysql -h"$DB_HOST" -u"$DB_USERNAME" -p"$DB_PASSWORD" -e "CREATE DATABASE IF NOT EXISTS $DB_NAME"
    echo "    ✅ Base de datos creada"
else
    echo "    ✅ Base de datos existente"
fi

# Ejecutar migraciones si es necesario
echo "[3/4] Ejecutando migraciones..."
mysql -h"$DB_HOST" -u"$DB_USERNAME" -p"$DB_PASSWORD" "$DB_NAME" < /docker-entrypoint-initdb.d/schema.sql 2>/dev/null || true
echo "    ✅ Migraciones aplicadas"

# Mostrar configuración (ocultando contraseña)
echo "[4/4] Configuración:"
echo "    DB Host: $DB_HOST"
echo "    DB Name: $DB_NAME"
echo "    DB User: $DB_USERNAME"
echo "    JWT Secret: [CONFIGURADO]"
echo ""

echo "========================================="
echo "  ✅ API lista para ejecutarse"
echo "  Puerto: 8080"
echo "========================================="

# Ejecutar la aplicación
exec java $JAVA_OPTS -jar /app/app.jar