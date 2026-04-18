package com.proyectofinal.grupo24.dao;

import com.proyectofinal.grupo24.enums.RolUsuario;
import com.proyectofinal.grupo24.model.Usuario;
import com.proyectofinal.grupo24.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioDao {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    //Buscar TODOS
    public List<Usuario> getAllUsuarios(){
        return (List<Usuario>) repository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return repository.findById(id);
    }

    //Creación NUEVO USUARIO
    public Usuario save(Usuario usuario) {
        // Solo encriptamos si hay un password (evita NullPointerException)
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            String passEncriptada = encoder.encode(usuario.getPassword());
            usuario.setPassword(passEncriptada);
        }
        return repository.save(usuario);
    }

    //Buscar por email en MySQL, si existe recupera el obj usuario con su pass encriptada
    public Optional<Usuario> login(String email, String password) {
        return repository.findByEmail(email)
                .filter(user -> encoder.matches(password, user.getPassword()))
                .map(user -> {
                    user.setOnline(true);
                    return repository.save(user);
                });
    }

    // Desloguear
    public void logout(Long id) {
        repository.findById(id).ifPresent(user -> {
            user.setOnline(false);
            repository.save(user);
        });
    }

    //Modificación de PASSWORD
    public Usuario updatePassword(Long id, String pass){
        return repository.findById(id).map(usuario -> {
            String passLimpia = pass.replace("{", "")
                    .replace("}", "")
                    .replace("\"password\":", "")
                    .replace("\"", "")
                    .trim();

            usuario.setPassword(encoder.encode(passLimpia));
            return repository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("No se encontró el usuario"));
    }

    //Modificación de ROL
    public Usuario updateRol(Long id, RolUsuario nuevoRol){
        return repository.findById(id).map(usuario -> {
            usuario.setRol(nuevoRol);
            return repository.save(usuario);
        }).orElseThrow(() ->new RuntimeException("Error al cambiar el rol: Usuario no encontrado"));
    }

    //Modificación de Estado
    public Usuario estado(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setActivo(!usuario.isActivo());

        return repository.save(usuario);
    }

    public List<Usuario> getRepartidoresOnline() {
        return repository.findUsuariosOnline();
    }
}
