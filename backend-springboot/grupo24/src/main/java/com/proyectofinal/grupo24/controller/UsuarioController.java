package com.proyectofinal.grupo24.controller;

import com.proyectofinal.grupo24.dao.RepartidorDao;
import com.proyectofinal.grupo24.dao.UsuarioDao;
import com.proyectofinal.grupo24.dto.LoginRequestDto;
import com.proyectofinal.grupo24.dto.LoginResponseDto;
import com.proyectofinal.grupo24.enums.RolUsuario;
import com.proyectofinal.grupo24.model.Repartidor;
import com.proyectofinal.grupo24.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private RepartidorDao repartidorDao;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registro")
    public ResponseEntity<?> crear(@RequestBody Usuario usuario) {
        try {
            String passEncriptada = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(passEncriptada);

            Usuario guardado = usuarioDao.save(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registrar: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        return usuarioDao.login(loginRequestDto.getEmail(), loginRequestDto.getPassword())
                .map(usuario -> {
                    LoginResponseDto resp = new LoginResponseDto(
                            usuario.getId(),
                            usuario.getNombre(),
                            usuario.getRol().toString()
                    );
                    return ResponseEntity.ok(resp);
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PatchMapping("/password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody String nuevaPass){
        try {
            usuarioDao.updatePassword(id, nuevaPass);
            return ResponseEntity.ok("Contraseña actualizada con éxito");
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/rol/{id}")
    public ResponseEntity<?> updateRol(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            RolUsuario nuevoRol = RolUsuario.valueOf(body.get("rol").toUpperCase());
            Usuario update = usuarioDao.updateRol(id, nuevoRol);
            return ResponseEntity.ok("Rol actualizado a: " + update.getRol());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rol inválido o usuario no encontrado");
        }
    }



    // Obtener todos los usuarios
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> lista = usuarioDao.getAllUsuarios();
        return ResponseEntity.ok(lista);
    }

    // Activar o desactivar usuario
    @PatchMapping("/cambiar-estado/{id}")
    public ResponseEntity<?> toggleEstado(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioDao.estado(id);
            String mensaje = usuario.isActivo() ? "activado" : "desactivado";
            return ResponseEntity.ok("Usuario " + mensaje + " con éxito.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Usuario datosNuevos) {
        try {
            Usuario existente = usuarioDao.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

            existente.setNombre(datosNuevos.getNombre());
            existente.setApellido(datosNuevos.getApellido());
            existente.setActivo(datosNuevos.isActivo());
            //existente.setEmail(datosNuevos.getEmail()); // Lo dejó comentado porque el mail no se cambiaría

            if (datosNuevos.getRol() == RolUsuario.REPARTIDOR) {
                Repartidor rep = new Repartidor();
                rep.setId(id);
                String patente = ((Repartidor) datosNuevos).getPatente();
                rep.setPatente(patente);

                repartidorDao.save(rep);
            }
            usuarioDao.save(existente);

            return ResponseEntity.ok(existente);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar: " + e.getMessage());
        }
    }

    @GetMapping("/online")
    public ResponseEntity<List<Usuario>> obtenerRepartidoresOnline() {
        List<Usuario> listaOnline = usuarioDao.getRepartidoresOnline();

        if (listaOnline.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve 204 si está vacía
        }

        return ResponseEntity.ok(listaOnline); // Devuelve 200 + el JSON
    }
}
