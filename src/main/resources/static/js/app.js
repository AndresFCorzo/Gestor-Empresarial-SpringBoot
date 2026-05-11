/**
 * Aplicación frontend para Gestor Empresarial
 * Consume las APIs REST de Spring Boot
 */

// Configuración base de la API
const API_URL = 'http://localhost:8080/api';

// ============ CLIENTES ============
async function listarClientes() {
    try {
        const response = await fetch(`${API_URL}/clientes`);
        const clientes = await response.json();
        renderizarClientes(clientes);
    } catch (error) {
        console.error('Error al listar clientes:', error);
    }
}

async function registrarCliente(cliente) {
    try {
        const response = await fetch(`${API_URL}/clientes`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cliente)
        });
        
        if (response.ok) {
            alert('Cliente registrado exitosamente');
            listarClientes();
        } else {
            const error = await response.json();
            alert('Error: ' + error.error);
        }
    } catch (error) {
        console.error('Error al registrar cliente:', error);
    }
}

async function actualizarCliente(id, cliente) {
    try {
        const response = await fetch(`${API_URL}/clientes/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(cliente)
        });
        
        if (response.ok) {
            alert('Cliente actualizado exitosamente');
            listarClientes();
        } else {
            const error = await response.json();
            alert('Error: ' + error.error);
        }
    } catch (error) {
        console.error('Error al actualizar cliente:', error);
    }
}

async function eliminarCliente(id) {
    if (!confirm('¿Eliminar este cliente?')) return;
    
    try {
        const response = await fetch(`${API_URL}/clientes/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            alert('Cliente eliminado exitosamente');
            listarClientes();
        } else {
            const error = await response.json();
            alert('Error: ' + error.error);
        }
    } catch (error) {
        console.error('Error al eliminar cliente:', error);
    }
}

// ============ PRODUCTOS ============
async function listarProductos() {
    try {
        const response = await fetch(`${API_URL}/productos`);
        const productos = await response.json();
        renderizarProductos(productos);
    } catch (error) {
        console.error('Error al listar productos:', error);
    }
}

async function registrarProducto(producto) {
    try {
        const response = await fetch(`${API_URL}/productos`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(producto)
        });
        
        if (response.ok) {
            alert('Producto registrado exitosamente');
            listarProductos();
        } else {
            const error = await response.json();
            alert('Error: ' + error.error);
        }
    } catch (error) {
        console.error('Error al registrar producto:', error);
    }
}

async function actualizarStock(id, stock) {
    try {
        const response = await fetch(`${API_URL}/productos/${id}/stock?stock=${stock}`, {
            method: 'PATCH'
        });
        
        if (response.ok) {
            alert('Stock actualizado exitosamente');
            listarProductos();
        } else {
            const error = await response.json();
            alert('Error: ' + error.error);
        }
    } catch (error) {
        console.error('Error al actualizar stock:', error);
    }
}

// ============ FACTURAS ============
async function listarFacturas() {
    try {
        const response = await fetch(`${API_URL}/facturas`);
        const facturas = await response.json();
        renderizarFacturas(facturas);
    } catch (error) {
        console.error('Error al listar facturas:', error);
    }
}

async function emitirFactura(factura) {
    try {
        const response = await fetch(`${API_URL}/facturas`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(factura)
        });
        
        if (response.ok) {
            alert('Factura emitida exitosamente');
            listarFacturas();
        } else {
            const error = await response.json();
            alert('Error: ' + error.error);
        }
    } catch (error) {
        console.error('Error al emitir factura:', error);
    }
}

async function anularFactura(id) {
    if (!confirm('¿Anular esta factura?')) return;
    
    try {
        const response = await fetch(`${API_URL}/facturas/${id}/anular`, {
            method: 'PUT'
        });
        
        if (response.ok) {
            alert('Factura anulada exitosamente');
            listarFacturas();
        } else {
            const error = await response.json();
            alert('Error: ' + error.error);
        }
    } catch (error) {
        console.error('Error al anular factura:', error);
    }
}

async function generarReporteVentas(inicio, fin) {
    try {
        const response = await fetch(`${API_URL}/facturas/reporte/ventas?inicio=${inicio}&fin=${fin}`);
        const reporte = await response.json();
        renderizarReporte(reporte);
    } catch (error) {
        console.error('Error al generar reporte:', error);
    }
}

// ============ AUTENTICACIÓN ============
async function login(correo, contrasena) {
    try {
        const response = await fetch(`${API_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ correo, contrasena })
        });
        
        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('usuario', JSON.stringify(data.usuario));
            window.location.href = '/dashboard.html';
        } else {
            const error = await response.json();
            alert('Error: ' + error.error);
        }
    } catch (error) {
        console.error('Error en login:', error);
    }
}

async function registrarUsuario(usuario) {
    try {
        const response = await fetch(`${API_URL}/auth/registro`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(usuario)
        });
        
        if (response.ok) {
            alert('Usuario registrado exitosamente');
            window.location.href = '/login.html';
        } else {
            const error = await response.json();
            alert('Error: ' + error.error);
        }
    } catch (error) {
        console.error('Error al registrar usuario:', error);
    }
}

// Funciones de renderizado
function renderizarClientes(clientes) {
    const tbody = document.querySelector('#clientesTable tbody');
    if (!tbody) return;
    
    tbody.innerHTML = '';
    clientes.forEach(cliente => {
        tbody.innerHTML += `
            <tr>
                <td>${cliente.idCliente}</td>
                <td>${cliente.nombre}</td>
                <td>${cliente.nit}</td>
                <td>${cliente.telefono || '-'}</td>
                <td>
                    <button class="btn btn-sm btn-warning" onclick="editarCliente(${cliente.idCliente})">Editar</button>
                    <button class="btn btn-sm btn-danger" onclick="eliminarCliente(${cliente.idCliente})">Eliminar</button>
                </td>
            </tr>
        `;
    });
}

function renderizarProductos(productos) {
    const tbody = document.querySelector('#productosTable tbody');
    if (!tbody) return;
    
    tbody.innerHTML = '';
    productos.forEach(producto => {
        tbody.innerHTML += `
            <tr>
                <td>${producto.idProducto}</td>
                <td>${producto.codigo}</td>
                <td>${producto.nombre}</td>
                <td>$${producto.precio.toLocaleString()}</td>
                <td>${producto.stock}</td>
                <td>${producto.aplicaIva ? producto.porcentajeIva + '%' : 'Exento'}</td>
                <td>
                    <button class="btn btn-sm btn-warning" onclick="editarProducto(${producto.idProducto})">Editar</button>
                    <button class="btn btn-sm btn-danger" onclick="eliminarProducto(${producto.idProducto})">Eliminar</button>
                </td>
            </tr>
        `;
    });
}

function renderizarFacturas(facturas) {
    const tbody = document.querySelector('#facturasTable tbody');
    if (!tbody) return;
    
    tbody.innerHTML = '';
    facturas.forEach(factura => {
        tbody.innerHTML += `
            <tr>
                <td>${factura.idFactura}</td>
                <td>${factura.numeroFactura}</td>
                <td>${new Date(factura.fecha).toLocaleDateString()}</td>
                <td>${factura.cliente?.nombre || 'N/A'}</td>
                <td>$${factura.total.toLocaleString()}</td>
                <td><span class="badge ${factura.estado === 'EMITIDA' ? 'bg-success' : 'bg-danger'}">${factura.estado}</span></td>
                <td>
                    <button class="btn btn-sm btn-info" onclick="verFactura(${factura.idFactura})">Ver</button>
                    ${factura.estado !== 'ANULADA' ? `<button class="btn btn-sm btn-danger" onclick="anularFactura(${factura.idFactura})">Anular</button>` : ''}
                </td>
            </tr>
        `;
    });
}