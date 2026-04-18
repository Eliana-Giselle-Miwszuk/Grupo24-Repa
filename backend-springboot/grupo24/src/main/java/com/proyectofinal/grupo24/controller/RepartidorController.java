package com.proyectofinal.grupo24.controller;

import com.proyectofinal.grupo24.dao.RepartidorDao;
import com.proyectofinal.grupo24.dao.UsuarioDao;
import com.proyectofinal.grupo24.model.Repartidor;
import com.proyectofinal.grupo24.model.Ubicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/repartidor")
public class RepartidorController {

    @Autowired
    private RepartidorDao repartidorDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/registro")
    public ResponseEntity<?> registrarRepartidor(@RequestBody Repartidor repartidor) {
        try {
            if (repartidor.getPatente() == null || repartidor.getPatente().isEmpty()) {
                return ResponseEntity.badRequest().body("La patente es obligatoria para repartidores.");
            }
            String passEncriptada = passwordEncoder.encode(repartidor.getPassword());
            repartidor.setPassword(passEncriptada);

            Repartidor guardado = repartidorDao.save(repartidor);
            return ResponseEntity.ok(guardado);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al registrar: " + e.getMessage());
        }
    }

    @PostMapping("/save")
    public Repartidor save(@RequestBody Repartidor repartidor){
        return repartidorDao.save(repartidor);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody Repartidor repartidor){
        repartidorDao.delete(repartidor);
    }

    @GetMapping("/repartidor/get-all")
    public List<Repartidor> getAllRepartidores(){
        return repartidorDao.getAllRepartidores();
    }

    @PutMapping("/{id}/ubicacion")
    public ResponseEntity<Repartidor> actualizarUbicacion(
            @PathVariable Long id,
            @RequestParam Double lat,
            @RequestParam Double lon) {

        Repartidor actualizado = repartidorDao.actualizarPosicion(id, lat, lon);
        return ResponseEntity.ok(actualizado);
    }

    @PostMapping("/{id}/ubicacion")
    public ResponseEntity<?> actualizarUbicacion(@PathVariable Long id, @RequestBody Ubicacion nuevaUbicacion) {
        try {
            // Aprovechamos el método que ya escribiste en el Dao
            Repartidor actualizado = repartidorDao.actualizarPosicion(
                    id,
                    nuevaUbicacion.getLatitud(),
                    nuevaUbicacion.getLongitud()
            );
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error inesperado.");
        }
    }


}
