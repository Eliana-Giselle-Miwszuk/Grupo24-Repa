package com.proyectofinal.grupo24.dao;

import com.proyectofinal.grupo24.model.HojaDeRuta;
import com.proyectofinal.grupo24.repository.HojaDeRutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HojaDeRutaDao {
    @Autowired
    private HojaDeRutaRepository hojaRepo;

    public void save(HojaDeRuta h) { hojaRepo.save(h); }
    public List<HojaDeRuta> getAll() { return hojaRepo.findAll(); }
}
