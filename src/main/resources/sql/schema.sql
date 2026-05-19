-- Crear base de datos
CREATE DATABASE IF NOT EXISTS gestor_empresarial;
USE gestor_empresarial;

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(20) DEFAULT 'USER',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_acceso TIMESTAMP NULL,
    activo BOOLEAN DEFAULT TRUE
);

-- Tabla de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    nit VARCHAR(20) UNIQUE NOT NULL,
    direccion VARCHAR(200),
    email VARCHAR(100),
    telefono VARCHAR(20),
    fecha_registro DATE NOT NULL
);

-- Tabla de productos
CREATE TABLE IF NOT EXISTS productos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    precio DECIMAL(12,2) NOT NULL,
    aplica_iva BOOLEAN DEFAULT TRUE,
    porcentaje_iva DECIMAL(5,2) DEFAULT 19.0,
    stock INT DEFAULT 0,
    categoria VARCHAR(50)
);

-- Tabla de facturas
CREATE TABLE IF NOT EXISTS facturas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    numero_factura VARCHAR(20) UNIQUE NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    subtotal DECIMAL(12,2) DEFAULT 0,
    total_iva DECIMAL(12,2) DEFAULT 0,
    total DECIMAL(12,2) DEFAULT 0,
    cliente_id BIGINT NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

-- Tabla de detalles de factura
CREATE TABLE IF NOT EXISTS detalles_factura (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    factura_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    valor_iva DECIMAL(12,2) NOT NULL,
    total DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (factura_id) REFERENCES facturas(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id)
);

-- Insertar usuario admin (password: admin123 encriptado)
INSERT INTO usuarios (username, email, password, rol) VALUES
('admin', 'admin@gestorempresarial.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'ADMIN');

-- Insertar datos de prueba
INSERT INTO clientes (nombre, nit, direccion, email, telefono, fecha_registro) VALUES
('Empresa Demo SAS', '900123456-1', 'Calle 123 #45-67', 'demo@empresa.com', '3001234567', CURDATE());

INSERT INTO productos (nombre, codigo, precio, stock, categoria) VALUES
('Laptop Gamer', 'LAP-001', 2500000, 10, 'Electrónica'),
('Mouse USB', 'MOU-001', 25000, 50, 'Accesorios');