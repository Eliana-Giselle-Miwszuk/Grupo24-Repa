package com.proyectofinal.grupo24.repository;

import com.proyectofinal.grupo24.model.Repartidor;
import com.proyectofinal.grupo24.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepartidorRepository extends CrudRepository<Repartidor, Long> {
    @Query("SELECT u FROM Usuario u WHERE u.online = true AND u.rol = 'REPARTIDOR'")
    List<Usuario> findUsuariosOnline();

}
