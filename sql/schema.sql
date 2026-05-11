-- =====================================================
-- GESTOR EMPRESARIAL INTEGRADO - BASE DE DATOS
-- =====================================================
-- Autores: Andres Felipe Corzo Angarita
--          Thomas Felipe Colmenares Perdomo
-- Instructor: Francisco Arnaldo Vargas Bermudez
-- Programa: Análisis y Desarrollo de Software
-- Ficha: 3070323
-- SENA - Bogotá 2025
-- =====================================================

-- Eliminar base de datos si existe (opcional - comentar si no se desea)
-- DROP DATABASE IF EXISTS gestor_empresarial;

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS gestor_empresarial;
USE gestor_empresarial;

-- =====================================================
-- 1. TABLA DE USUARIOS
-- =====================================================
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL,
    documento_identidad VARCHAR(20),
    telefono VARCHAR(20),
    direccion VARCHAR(200),
    fecha_registro DATE NOT NULL,
    ultimo_acceso DATETIME,
    activo BOOLEAN DEFAULT TRUE,
    pregunta_seguridad VARCHAR(200),
    respuesta_seguridad VARCHAR(200),
    INDEX idx_correo (correo),
    INDEX idx_rol (rol),
    INDEX idx_activo (activo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 2. TABLA DE CLIENTES
-- =====================================================
CREATE TABLE IF NOT EXISTS clientes (
    id_cliente BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    nit VARCHAR(20) UNIQUE NOT NULL,
    direccion VARCHAR(200),
    correo VARCHAR(100),
    telefono VARCHAR(20),
    fecha_registro DATE NOT NULL,
    INDEX idx_nit (nit),
    INDEX idx_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 3. TABLA DE PRODUCTOS
-- =====================================================
CREATE TABLE IF NOT EXISTS productos (
    id_producto BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    precio DECIMAL(12,2) NOT NULL,
    aplica_iva BOOLEAN DEFAULT TRUE,
    porcentaje_iva DECIMAL(5,2) DEFAULT 19.0,
    stock INT DEFAULT 0,
    categoria VARCHAR(50),
    fecha_registro DATE DEFAULT (CURDATE()),
    INDEX idx_codigo (codigo),
    INDEX idx_categoria (categoria)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 4. TABLA DE FACTURAS
-- =====================================================
CREATE TABLE IF NOT EXISTS facturas (
    id_factura BIGINT PRIMARY KEY AUTO_INCREMENT,
    numero_factura VARCHAR(20) UNIQUE NOT NULL,
    fecha DATE NOT NULL,
    estado ENUM('PENDIENTE', 'EMITIDA', 'ANULADA') DEFAULT 'PENDIENTE',
    subtotal DECIMAL(12,2) DEFAULT 0,
    total_iva DECIMAL(12,2) DEFAULT 0,
    total DECIMAL(12,2) DEFAULT 0,
    id_cliente BIGINT NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente) ON DELETE RESTRICT,
    INDEX idx_numero_factura (numero_factura),
    INDEX idx_fecha (fecha),
    INDEX idx_estado (estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 5. TABLA DE DETALLES DE FACTURA
-- =====================================================
CREATE TABLE IF NOT EXISTS detalles_factura (
    id_detalle BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_factura BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    valor_iva DECIMAL(12,2) NOT NULL,
    total DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (id_factura) REFERENCES facturas(id_factura) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES productos(id_producto) ON DELETE RESTRICT,
    INDEX idx_factura (id_factura)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 6. TABLA DE REGISTROS GENERALES (Entidad Central)
-- =====================================================
CREATE TABLE IF NOT EXISTS registros_generales (
    id_registro BIGINT PRIMARY KEY AUTO_INCREMENT,
    tipo_registro VARCHAR(50) NOT NULL,
    fecha_registro DATE NOT NULL,
    descripcion TEXT,
    id_relacionado BIGINT NOT NULL,
    entidad_relacionada VARCHAR(100) NOT NULL,
    estado VARCHAR(20) DEFAULT 'ACTIVO',
    usuario_registro VARCHAR(100),
    INDEX idx_tipo_registro (tipo_registro),
    INDEX idx_entidad_relacionada (entidad_relacionada, id_relacionado),
    INDEX idx_fecha_registro (fecha_registro)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 7. TABLA DE EMPLEADOS (Para nómina - futura implementación)
-- =====================================================
CREATE TABLE IF NOT EXISTS empleados (
    id_empleado BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    cargo VARCHAR(50) NOT NULL,
    documento VARCHAR(20) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100),
    direccion VARCHAR(200),
    fecha_contratacion DATE NOT NULL,
    salario_base DECIMAL(12,2) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    INDEX idx_documento (documento),
    INDEX idx_cargo (cargo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 8. TABLA DE NÓMINA (Futura implementación)
-- =====================================================
CREATE TABLE IF NOT EXISTS nominas (
    id_nomina BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_empleado BIGINT NOT NULL,
    mes VARCHAR(7) NOT NULL, -- Formato: YYYY-MM
    salario_base DECIMAL(12,2) NOT NULL,
    horas_extras DECIMAL(12,2) DEFAULT 0,
    bonificaciones DECIMAL(12,2) DEFAULT 0,
    deducciones DECIMAL(12,2) DEFAULT 0,
    salud DECIMAL(12,2) DEFAULT 0,
    pension DECIMAL(12,2) DEFAULT 0,
    total DECIMAL(12,2) NOT NULL,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    fecha_generacion DATE NOT NULL,
    FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado),
    INDEX idx_mes (mes),
    INDEX idx_empleado (id_empleado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 9. TABLA DE VIÁTICOS (Futura implementación)
-- =====================================================
CREATE TABLE IF NOT EXISTS viaticos (
    id_viatico BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_empleado BIGINT NOT NULL,
    fecha DATE NOT NULL,
    motivo VARCHAR(200) NOT NULL,
    valor DECIMAL(12,2) NOT NULL,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    soporte_ruta VARCHAR(255),
    observaciones TEXT,
    fecha_aprobacion DATE,
    FOREIGN KEY (id_empleado) REFERENCES empleados(id_empleado),
    INDEX idx_fecha (fecha),
    INDEX idx_estado (estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- 10. TABLA DE DOCUMENTOS (Gestión documental)
-- =====================================================
CREATE TABLE IF NOT EXISTS documentos (
    id_documento BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    ruta_archivo VARCHAR(255) NOT NULL,
    fecha DATE NOT NULL,
    id_usuario BIGINT NOT NULL,
    id_relacionado BIGINT,
    entidad_relacionada VARCHAR(50),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    INDEX idx_tipo (tipo),
    INDEX idx_fecha (fecha)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- =====================================================
-- DATOS DE PRUEBA (INSERTS)
-- =====================================================

-- Insertar usuarios de prueba
INSERT INTO usuarios (nombre, correo, contrasena, rol, documento_identidad, telefono, fecha_registro, activo) VALUES
('Administrador Sistema', 'admin@gestorempresarial.com', 'admin123', 'ADMINISTRADOR', '123456789', '3000000001', CURDATE(), TRUE),
('Empleado Demo', 'empleado@gestorempresarial.com', 'empleado123', 'EMPLEADO', '987654321', '3111111111', CURDATE(), TRUE),
('Contador Principal', 'contador@gestorempresarial.com', 'contador123', 'CONTADOR', '111222333', '3222222222', CURDATE(), TRUE),
('Gerente General', 'gerente@gestorempresarial.com', 'gerente123', 'GERENTE', '444555666', '3333333333', CURDATE(), TRUE),
('RRHH Encargado', 'rrhh@gestorempresarial.com', 'rrhh123', 'RECURSOS_HUMANOS', '777888999', '3444444444', CURDATE(), TRUE);

-- Insertar clientes de prueba
INSERT INTO clientes (nombre, nit, direccion, correo, telefono, fecha_registro) VALUES
('Empresa Demo SAS', '900123456-1', 'Calle 123 #45-67', 'demo@empresa.com', '3001234567', CURDATE()),
('Cliente Prueba LTDA', '901234567-2', 'Carrera 89 #12-34', 'prueba@cliente.com', '3107654321', CURDATE()),
('Distribuciones El Sol', '902345678-3', 'Avenida Siempreviva 742', 'ventas@distribucionessol.com', '3155555555', CURDATE()),
('Tecnología Avanzada SA', '903456789-4', 'Calle 50 #20-30', 'info@tecnologiaavanzada.com', '3201234567', CURDATE()),
('Construcciones Modernas', '904567890-5', 'Carrera 15 #88-12', 'construcciones@modernas.com', '3009876543', CURDATE());

-- Insertar productos de prueba
INSERT INTO productos (nombre, codigo, precio, aplica_iva, porcentaje_iva, stock, categoria) VALUES
('Laptop Gamer', 'LAP-001', 2500000, TRUE, 19.0, 10, 'Electrónica'),
('Mouse USB', 'MOU-001', 25000, TRUE, 19.0, 50, 'Accesorios'),
('Teclado Mecánico', 'TEC-001', 150000, TRUE, 19.0, 30, 'Accesorios'),
('Monitor 24 pulgadas', 'MON-001', 450000, TRUE, 19.0, 15, 'Electrónica'),
('Servicio Consultoría', 'SRV-001', 500000, FALSE, 0, 999, 'Servicios'),
('Silla Ergonómica', 'SIL-001', 350000, TRUE, 19.0, 20, 'Mobiliario'),
('Disco SSD 1TB', 'SSD-001', 280000, TRUE, 19.0, 25, 'Electrónica'),
('Memoria RAM 16GB', 'RAM-001', 180000, TRUE, 19.0, 40, 'Electrónica');

-- Insertar facturas de prueba
INSERT INTO facturas (numero_factura, fecha, estado, subtotal, total_iva, total, id_cliente) VALUES
('FAC-001', DATE_SUB(CURDATE(), INTERVAL 30 DAY), 'EMITIDA', 2500000, 475000, 2975000, 1),
('FAC-002', DATE_SUB(CURDATE(), INTERVAL 20 DAY), 'EMITIDA', 175000, 33250, 208250, 2),
('FAC-003', DATE_SUB(CURDATE(), INTERVAL 15 DAY), 'EMITIDA', 450000, 85500, 535500, 3),
('FAC-004', DATE_SUB(CURDATE(), INTERVAL 10 DAY), 'EMITIDA', 500000, 0, 500000, 1),
('FAC-005', DATE_SUB(CURDATE(), INTERVAL 5 DAY), 'PENDIENTE', 780000, 148200, 928200, 4),
('FAC-006', CURDATE(), 'EMITIDA', 280000, 53200, 333200, 5);

-- Insertar detalles de facturas de prueba
INSERT INTO detalles_factura (id_factura, id_producto, cantidad, precio_unitario, subtotal, valor_iva, total) VALUES
(1, 1, 1, 2500000, 2500000, 475000, 2975000),
(2, 2, 5, 25000, 125000, 23750, 146750),
(2, 3, 1, 50000, 50000, 9500, 59500),
(3, 4, 1, 450000, 450000, 85500, 535500),
(4, 5, 1, 500000, 500000, 0, 500000),
(5, 3, 2, 150000, 300000, 57000, 357000),
(5, 6, 1, 480000, 480000, 91200, 571200),
(6, 7, 1, 280000, 280000, 53200, 333200);

-- Insertar empleados de prueba
INSERT INTO empleados (nombre, cargo, documento, telefono, correo, direccion, fecha_contratacion, salario_base, activo) VALUES
('Juan Pérez', 'Desarrollador', '12345678', '3101111111', 'juan.perez@empresa.com', 'Calle 1 #2-3', DATE_SUB(CURDATE(), INTERVAL 365 DAY), 2500000, TRUE),
('Ana López', 'Contadora', '87654321', '3112222222', 'ana.lopez@empresa.com', 'Carrera 4 #5-6', DATE_SUB(CURDATE(), INTERVAL 500 DAY), 3000000, TRUE),
('Carlos Gómez', 'Gerente', '11112222', '3123333333', 'carlos.gomez@empresa.com', 'Avenida 7 #8-9', DATE_SUB(CURDATE(), INTERVAL 800 DAY), 4500000, TRUE),
('María Rodríguez', 'RRHH', '33334444', '3134444444', 'maria.rodriguez@empresa.com', 'Calle 10 #11-12', DATE_SUB(CURDATE(), INTERVAL 200 DAY), 2800000, TRUE);

-- Insertar registros generales de prueba
INSERT INTO registros_generales (tipo_registro, fecha_registro, descripcion, id_relacionado, entidad_relacionada, estado, usuario_registro) VALUES
('CLIENTE', CURDATE(), 'Cliente registrado: Empresa Demo SAS', 1, 'Cliente', 'ACTIVO', 'Administrador'),
('CLIENTE', CURDATE(), 'Cliente registrado: Cliente Prueba LTDA', 2, 'Cliente', 'ACTIVO', 'Administrador'),
('PRODUCTO', CURDATE(), 'Producto registrado: Laptop Gamer', 1, 'Producto', 'ACTIVO', 'Administrador'),
('FACTURA', CURDATE(), 'Factura emitida: FAC-001', 1, 'Factura', 'ACTIVO', 'Administrador'),
('FACTURA', CURDATE(), 'Factura emitida: FAC-002', 2, 'Factura', 'ACTIVO', 'Administrador'),
('EMPLEADO', CURDATE(), 'Empleado contratado: Juan Pérez', 1, 'Empleado', 'ACTIVO', 'RRHH Encargado');

-- Insertar documentos de prueba
INSERT INTO documentos (nombre, tipo, ruta_archivo, fecha, id_usuario, id_relacionado, entidad_relacionada) VALUES
('Factura FAC-001.pdf', 'FACTURA', '/documentos/facturas/FAC-001.pdf', CURDATE(), 1, 1, 'Factura'),
('Contrato Juan Pérez.pdf', 'CONTRATO', '/documentos/contratos/contrato_juan_perez.pdf', CURDATE(), 5, 1, 'Empleado'),
('Cotización Cliente 1.pdf', 'COTIZACION', '/documentos/cotizaciones/cotizacion_cliente1.pdf', CURDATE(), 1, 1, 'Cliente'),
('Soporte Viático Enero.pdf', 'SOPORTE', '/documentos/viaticos/soporte_enero.pdf', CURDATE(), 2, 1, 'Viatico');

-- =====================================================
-- CONSULTAS DE VERIFICACIÓN
-- =====================================================

-- Verificar tablas creadas
SHOW TABLES;

-- Contar registros por tabla
SELECT 'usuarios' AS tabla, COUNT(*) AS registros FROM usuarios
UNION ALL SELECT 'clientes', COUNT(*) FROM clientes
UNION ALL SELECT 'productos', COUNT(*) FROM productos
UNION ALL SELECT 'facturas', COUNT(*) FROM facturas
UNION ALL SELECT 'detalles_factura', COUNT(*) FROM detalles_factura
UNION ALL SELECT 'empleados', COUNT(*) FROM empleados
UNION ALL SELECT 'registros_generales', COUNT(*) FROM registros_generales;

-- =====================================================
-- VISTAS ÚTILES (Opcionales)
-- =====================================================

-- Vista: Resumen de ventas por producto
CREATE OR REPLACE VIEW vista_ventas_productos AS
SELECT 
    p.codigo,
    p.nombre AS producto,
    p.categoria,
    COALESCE(SUM(df.cantidad), 0) AS total_vendido,
    COALESCE(SUM(df.total), 0) AS total_ventas,
    COUNT(DISTINCT f.id_factura) AS num_facturas
FROM productos p
LEFT JOIN detalles_factura df ON p.id_producto = df.id_producto
LEFT JOIN facturas f ON df.id_factura = f.id_factura AND f.estado = 'EMITIDA'
GROUP BY p.id_producto
ORDER BY total_ventas DESC;

-- Vista: Resumen de clientes
CREATE OR REPLACE VIEW vista_resumen_clientes AS
SELECT 
    c.id_cliente,
    c.nombre,
    c.nit,
    COUNT(f.id_factura) AS num_facturas,
    COALESCE(SUM(f.total), 0) AS total_compras,
    MAX(f.fecha) AS ultima_compra
FROM clientes c
LEFT JOIN facturas f ON c.id_cliente = f.id_cliente AND f.estado = 'EMITIDA'
GROUP BY c.id_cliente
ORDER BY total_compras DESC;

-- Vista: Dashboard de métricas
CREATE OR REPLACE VIEW vista_dashboard_metricas AS
SELECT
    (SELECT COUNT(*) FROM clientes) AS total_clientes,
    (SELECT COUNT(*) FROM productos WHERE stock > 0) AS total_productos,
    (SELECT COUNT(*) FROM productos WHERE stock <= 5) AS productos_stock_bajo,
    (SELECT COALESCE(SUM(total), 0) FROM facturas WHERE estado = 'EMITIDA' AND MONTH(fecha) = MONTH(CURDATE())) AS ventas_mes_actual,
    (SELECT COALESCE(SUM(total), 0) FROM facturas WHERE estado = 'EMITIDA') AS ventas_totales,
    (SELECT COUNT(*) FROM facturas WHERE estado = 'EMITIDA' AND MONTH(fecha) = MONTH(CURDATE())) AS facturas_mes_actual;

-- =====================================================
-- PROCEDIMIENTOS ALMACENADOS (Opcionales)
-- =====================================================

DELIMITER //

-- Procedimiento para generar reporte de ventas por período
CREATE PROCEDURE sp_reporte_ventas(IN fecha_inicio DATE, IN fecha_fin DATE)
BEGIN
    SELECT 
        f.id_factura,
        f.numero_factura,
        f.fecha,
        c.nombre AS cliente,
        p.nombre AS producto,
        df.cantidad,
        df.precio_unitario,
        df.total
    FROM facturas f
    JOIN clientes c ON f.id_cliente = c.id_cliente
    JOIN detalles_factura df ON f.id_factura = df.id_factura
    JOIN productos p ON df.id_producto = p.id_producto
    WHERE f.fecha BETWEEN fecha_inicio AND fecha_fin
    AND f.estado = 'EMITIDA'
    ORDER BY f.fecha DESC;
END //

-- Procedimiento para actualizar stock después de una factura
CREATE PROCEDURE sp_actualizar_stock(IN p_id_factura BIGINT)
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE v_id_producto BIGINT;
    DECLARE v_cantidad INT;
    DECLARE cur CURSOR FOR SELECT id_producto, cantidad FROM detalles_factura WHERE id_factura = p_id_factura;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO v_id_producto, v_cantidad;
        IF done THEN
            LEAVE read_loop;
        END IF;
        UPDATE productos SET stock = stock - v_cantidad WHERE id_producto = v_id_producto;
    END LOOP;
    CLOSE cur;
END //

DELIMITER ;

-- =====================================================
-- TRIGGERS (Opcionales)
-- =====================================================

-- Trigger para calcular automáticamente los totales de factura
DELIMITER //
CREATE TRIGGER tr_calcular_totales_factura
AFTER INSERT ON detalles_factura
FOR EACH ROW
BEGIN
    DECLARE v_subtotal DECIMAL(12,2);
    DECLARE v_total_iva DECIMAL(12,2);
    DECLARE v_total DECIMAL(12,2);
    
    SELECT COALESCE(SUM(subtotal), 0), COALESCE(SUM(valor_iva), 0), COALESCE(SUM(total), 0)
    INTO v_subtotal, v_total_iva, v_total
    FROM detalles_factura
    WHERE id_factura = NEW.id_factura;
    
    UPDATE facturas 
    SET subtotal = v_subtotal, total_iva = v_total_iva, total = v_total
    WHERE id_factura = NEW.id_factura;
END //
DELIMITER ;

-- =====================================================
-- FIN DEL SCRIPT
-- =====================================================

-- Mensaje de confirmación
SELECT '=== BASE DE DATOS GESTOR EMPRESARIAL CREADA EXITOSAMENTE ===' AS mensaje;
SELECT 'Tablas creadas: 10' AS info;
SELECT 'Registros de prueba insertados' AS info;