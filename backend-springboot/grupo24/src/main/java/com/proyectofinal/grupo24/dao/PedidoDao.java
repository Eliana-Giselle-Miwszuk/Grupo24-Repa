package com.proyectofinal.grupo24.dao;

import com.proyectofinal.grupo24.model.Pedido;
import com.proyectofinal.grupo24.enums.EstadoPedido;
import com.proyectofinal.grupo24.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoDao {

    @Autowired
    private PedidoRepository repository;

    // --- AGREGADO: Este es el método que necesita el DataLoader ---
    public Pedido save(Pedido pedido) {
        return repository.save(pedido);
    }
    public List<Pedido> buscarPorRepartidor(Long idRepartidor) {
        return repository.findByRepartidorAsignadoId(idRepartidor);
    }

    public List<Pedido> getAll() {
        return (List<Pedido>) repository.findAll();
    }

    @Transactional
    public boolean validarEntrega(Long pedidoId, String codigo, String fotoBase64) {
        Optional<Pedido> pedidoOpt = repository.findById(pedidoId);

        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();

            // 1. Validar código con la palabra clave del cliente
            if (pedido.getCliente() != null && codigo.equals(pedido.getCliente().getPalabraClaveEntrega())) {

                // 2. Intentar guardar la foto físicamente
                if (fotoBase64 != null && !fotoBase64.isEmpty()) {
                    guardarImagenServidor(pedidoId, fotoBase64);
                }

                // 3. Actualizar estado y fecha
                pedido.setEstado(EstadoPedido.ENTREGADO);
                pedido.setFechaEntrega(LocalDate.now());
                repository.save(pedido);
                return true;
            }
        }
        return false;
    }

    private void guardarImagenServidor(Long pedidoId, String base64) {
        try {
            Path root = Paths.get("uploads");
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
            byte[] data = Base64.getDecoder().decode(base64);
            Path archivo = root.resolve("entrega_" + pedidoId + ".jpg");
            Files.write(archivo, data);
            System.out.println("Imagen guardada en: " + archivo.toAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public boolean registrarIncidencia(Long pedidoId, String motivo, String descripcion) {
        Optional<Pedido> pedidoOpt = repository.findById(pedidoId);
        if (pedidoOpt.isPresent()) {
            Pedido pedido = pedidoOpt.get();
            // ... tu lógica de incidencia que ya funciona ...
            pedido.setEstado(EstadoPedido.NO_ENTREGADO);
            repository.save(pedido);
            return true;
        }
        return false;
    }
}