package com.proyectofinal.grupo24.repository;

import com.proyectofinal.grupo24.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT p FROM Pedido p JOIN FETCH p.cliente WHERE p.repartidorAsignado.id = :id")
    List<Pedido> findByRepartidorAsignadoId(@Param("id") Long id);
}