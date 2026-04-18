package com.proyectofinal.grupo24.dao;

import com.proyectofinal.grupo24.model.Producto;
import com.proyectofinal.grupo24.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductoDao {
    @Autowired
    private ProductoRepository productoRepo;

    public void save(Producto p) { productoRepo.save(p); }
    public List<Producto> getAll() { return productoRepo.findAll(); }
}
