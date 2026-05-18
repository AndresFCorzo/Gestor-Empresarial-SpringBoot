# =====================================================
# DOCKERFILE - GESTOR EMPRESARIAL API
# =====================================================
# 
# Este Dockerfile construye la imagen de la API
# 
# @author Andres Felipe Corzo Angarita
# @author Thomas Felipe Colmenares Perdomo
# =====================================================

# =====================================================
# ETAPA 1: BUILD (Compilación con Maven)
# =====================================================
FROM maven:3.8.4-openjdk-11-slim AS build

# Información del mantenedor
LABEL maintainer="Andres Felipe Corzo Angarita"
LABEL description="Gestor Empresarial API - Build Stage"

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY src ./src

# Compilar el proyecto y empaquetar JAR
RUN mvn clean package -DskipTests

# =====================================================
# ETAPA 2: RUNTIME (Ejecución)
# =====================================================
FROM eclipse-temurin:11-jre

# Información del mantenedor
LABEL maintainer="Andres Felipe Corzo Angarita"
LABEL description="Gestor Empresarial API - Runtime"

# Establecer directorio de trabajo
WORKDIR /app

# Crear usuario no root para seguridad
RUN addgroup --system --gid 1001 appgroup && \
    adduser --system --uid 1001 --gid 1001 appuser

# Copiar el JAR desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Crear directorio para logs
RUN mkdir -p /app/logs && chown -R appuser:appgroup /app

# Cambiar a usuario no root
USER appuser

# Exponer el puerto de la aplicación
EXPOSE 8080

# Variables de entorno configurables
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE=docker
ENV DB_HOST=mysql
ENV DB_PORT=3306
ENV DB_NAME=gestor_empresarial
ENV DB_USERNAME=root
ENV DB_PASSWORD=root
ENV JWT_SECRET=gestorEmpresarialSecretKey2025
ENV JWT_EXPIRATION=86400000

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
    CMD wget --quiet --tries=1 --spider http://localhost:8080/api/auth/verify || exit 1

# Punto de entrada
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]