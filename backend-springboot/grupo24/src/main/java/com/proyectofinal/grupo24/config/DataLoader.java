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
import java.util.Date;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioDao usuarioDao;
    @Autowired
    private ProductoDao productoDao;
    @Autowired
    private PedidoDao pedidoDao;
    @Autowired
    private ItemPedidoDao itemRepo;
    @Autowired
    private HojaDeRutaDao hojaDao;
    @Autowired
    private ClienteDao clienteDao;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (usuarioDao.getAllUsuarios().isEmpty()) {

            // 1. ADMIN
            Usuario admin = new Usuario();
            admin.setNombre("Nahuel");
            admin.setApellido("Admin");
            admin.setEmail("admin@utn.com");
            admin.setPassword("admin123");
            admin.setRol(RolUsuario.ADMINISTRADOR);
            admin.setActivo(true);
            admin.setOnline(false);
            usuarioDao.save(admin);

            // 2. REPARTIDOR (Juan)
            Ubicacion posJuan = new Ubicacion();
            posJuan.setLatitud(-34.6037);
            posJuan.setLongitud(-58.3816);
            posJuan.setUltimaActualizacion(LocalDateTime.now());

            Repartidor juan = new Repartidor();
            juan.setNombre("Juan");
            juan.setApellido("Repartidor");
            juan.setEmail("repartidor@utn.com");
            juan.setPassword("user123");
            juan.setRol(RolUsuario.REPARTIDOR);
            juan.setActivo(true);
            juan.setOnline(true);
            juan.setPatente("AF-123-JK");
            juan.setUbicacionActual(posJuan);
            usuarioDao.save(juan);

            // 3. CLIENTES (Con palabras clave agregadas)
            Cliente c1 = new Cliente();
            c1.setDni("11111111");
            c1.setNombre("Ferretería El Tornillo");
            c1.setDireccion("Av. Medrano 951, CABA");
            c1.setTelefono("4444-1111");
            c1.setPalabraClaveEntrega("TORNILLO123"); // <-- CLAVE PARA PED-101
            clienteDao.save(c1);

            Cliente c2 = new Cliente();
            c2.setDni("22222222");
            c2.setNombre("Bazar Avenida");
            c2.setDireccion("Av. Corrientes 1234, CABA");
            c2.setTelefono("4444-2222");
            c2.setPalabraClaveEntrega("BAZAR456");
            clienteDao.save(c2);

            Cliente c3 = new Cliente();
            c3.setDni("33333333");
            c3.setNombre("Constructora Delta");
            c3.setDireccion("Calle Falsa 123, Avellaneda");
            c3.setTelefono("4444-3333");
            c3.setPalabraClaveEntrega("DELTA789");
            clienteDao.save(c3);

            // 4. PRODUCTOS
            Producto p1 = new Producto();
            p1.setCodigo("FER-001");
            p1.setDescripcion("Amoladora Angular");
            p1.setCantidad(50);
            productoDao.save(p1);

            Producto p2 = new Producto();
            p2.setCodigo("FER-002");
            p2.setDescripcion("Taladro Percutor");
            p2.setCantidad(30);
            productoDao.save(p2);

            // 5. HOJA DE RUTA
            HojaDeRuta hoja = new HojaDeRuta();
            hoja.setRepartidor(juan);
            hoja.setFecha(new Date());
            hoja.setActivo(true);
            hojaDao.save(hoja);

            // 6. PEDIDOS

            // Pedido 1 - PENDIENTE
            Pedido ped1 = new Pedido();
            ped1.setNumeroPedido("PED-101");
            ped1.setEstado(EstadoPedido.PENDIENTE);
            ped1.setRepartidorAsignado(juan);
            ped1.setHojaDeRuta(hoja);
            ped1.setCliente(c1);
            pedidoDao.save(ped1);

            // Pedido 2 - ENTREGADO
            Pedido ped2 = new Pedido();
            ped2.setNumeroPedido("PED-102");
            ped2.setEstado(EstadoPedido.ENTREGADO);
            ped2.setRepartidorAsignado(juan);
            ped2.setHojaDeRuta(hoja);
            ped2.setCliente(c2);
            pedidoDao.save(ped2);

            // Pedido 3 - NO_ENTREGADO
            Pedido ped3 = new Pedido();
            ped3.setNumeroPedido("PED-103");
            ped3.setEstado(EstadoPedido.NO_ENTREGADO);
            ped3.setRepartidorAsignado(juan);
            ped3.setHojaDeRuta(hoja);
            ped3.setCliente(c3);
            pedidoDao.save(ped3);

            // Pedido 4 - PENDIENTE
            Pedido ped4 = new Pedido();
            ped4.setNumeroPedido("PED-104");
            ped4.setEstado(EstadoPedido.PENDIENTE);
            ped4.setRepartidorAsignado(juan);
            ped4.setHojaDeRuta(hoja);
            ped4.setCliente(c1);
            pedidoDao.save(ped4);

            // 7. ITEMS
            ItemPedido i1 = new ItemPedido();
            i1.setProducto(p1); i1.setCantidad(1); i1.setPedido(ped1);
            itemRepo.save(i1);

            ItemPedido i2 = new ItemPedido();
            i2.setProducto(p2); i2.setCantidad(2); i2.setPedido(ped2);
            itemRepo.save(i2);

            System.out.println(">>> DATOS DE PRUEBA CARGADOS CORRECTAMENTE <<<");
        }
    }
}