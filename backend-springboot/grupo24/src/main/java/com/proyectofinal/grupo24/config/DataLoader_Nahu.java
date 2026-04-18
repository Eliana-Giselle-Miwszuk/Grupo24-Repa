package com.proyectofinal.grupo24.config;

import com.proyectofinal.grupo24.dao.*;
import com.proyectofinal.grupo24.enums.EstadoPedido;
import com.proyectofinal.grupo24.enums.RolUsuario;
import com.proyectofinal.grupo24.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class DataLoader_Nahu implements CommandLineRunner {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private PedidoDao pedidoDao;

    @Autowired
    private ItemPedidoDao itemRepo; // Usamos el DAO para mantener la capa intermedia

    @Autowired
    private HojaDeRutaDao hojaDao;

    @Autowired
    private ClienteDao clienteDao;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (usuarioDao.getAllUsuarios().isEmpty()) {

            //Creamos un ADMIN
            Usuario admin = new Usuario();
            admin.setNombre("Nahuel");
            admin.setApellido("Admin");
            admin.setEmail("admin@utn.com");
            admin.setPassword("admin123");
            admin.setRol(RolUsuario.ADMINISTRADOR);
            admin.setActivo(true);
            admin.setOnline(false);
            usuarioDao.save(admin);

            // Creamos la UBICACIÓN
            Ubicacion posJuan = new Ubicacion();
            posJuan.setLatitud(-34.6037);
            posJuan.setLongitud(-58.3816);
            posJuan.setUltimaActualizacion(LocalDateTime.now());

            // Creamos un REPARTIDOR
            Repartidor juan = new Repartidor();
            juan.setNombre("Juan");
            juan.setApellido("Repartidor");
            juan.setEmail("repartidor@utn.com");
            juan.setPassword("user123");
            juan.setRol(RolUsuario.REPARTIDOR);
            juan.setActivo(true);
            juan.setOnline(true);
            juan.setPatente("AF-123-JK");
            juan.setUbicacionActual(posJuan); // Vinculamos el objeto Ubicacion
            usuarioDao.save(juan);

            // Creamos un CLIENTE
            Cliente clienteFerreteria = new Cliente();
            clienteFerreteria.setDni("16698858");
            clienteFerreteria.setEmail("cliente@emial.com");
            clienteFerreteria.setNombre("Ferretería El Tornillo");
            clienteFerreteria.setDireccion("Av. Medrano 951, CABA");
            clienteFerreteria.setTelefono("11-4567-8901");
            clienteDao.save(clienteFerreteria);

            // Cargamos PRODUCTOS random
            Producto p1 = new Producto();
            p1.setCodigo("FER-001");
            p1.setDescripcion("Amoladora Angular 115mm");
            p1.setCantidad(50);
            productoDao.save(p1);

            Producto p2 = new Producto();
            p2.setCodigo("FER-002");
            p2.setDescripcion("Set de Destornilladores x6");
            p2.setCantidad(30);
            productoDao.save(p2);

            //Creamos una HOJA DE RUTA
            HojaDeRuta hoja = new HojaDeRuta();
            hoja.setRepartidor(juan);
            hoja.setFecha(new java.util.Date());
            hoja.setActivo(true);
            hojaDao.save(hoja);

            // Creamos un PEDIDO de prueba
            Pedido ped1 = new Pedido();
            ped1.setNumeroPedido("PED-101");
            ped1.setEstado(EstadoPedido.PENDIENTE);
            ped1.setRepartidorAsignado(juan);
            ped1.setHojaDeRuta(hoja);
            ped1.setCliente(clienteFerreteria);
            pedidoDao.save(ped1);

            // 7. Agregamos los ITEMS al pedido
            ItemPedido item1 = new ItemPedido();
            item1.setProducto(p1);
            item1.setCantidad(1);
            item1.setPedido(ped1);
            itemRepo.save(item1);

            System.out.println(">>> DATOS CARGADOS EXITOSAMENTE <<<");
        }
    }
}