package com.proyectofinal.grupo24.repository;

import com.proyectofinal.grupo24.model.HojaDeRuta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HojaDeRutaRepository extends JpaRepository<HojaDeRuta, Long> {
}