package com.proyectofinal.grupo24.controller;

import com.proyectofinal.grupo24.dao.PedidoDao;
import com.proyectofinal.grupo24.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoDao pedidoDao;

    @GetMapping("/repartidor/{id}")
    public ResponseEntity<List<Pedido>> listarPorRepartidor(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoDao.buscarPorRepartidor(id));
    }

    @PostMapping("/validar-entrega")
    public ResponseEntity<Boolean> validarEntrega(@RequestBody Map<String, Object> datos) {
        try {
            Long pedidoId = Long.valueOf(datos.get("pedidoId").toString());
            String codigo = (String) datos.get("codigo");
            String fotoBase64 = (String) datos.get("fotoBase64");

            boolean exito = pedidoDao.validarEntrega(pedidoId, codigo, fotoBase64);
            return ResponseEntity.ok(exito);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping("/reportar-incidencia")
    public ResponseEntity<Boolean> reportarIncidencia(@RequestBody Map<String, Object> datos) {
        Long pedidoId = Long.valueOf(datos.get("pedidoId").toString());
        String motivo = (String) datos.get("motivo");
        String descripcion = (String) datos.get("descripcionAdicional");
        return ResponseEntity.ok(pedidoDao.registrarIncidencia(pedidoId, motivo, descripcion));
    }
}