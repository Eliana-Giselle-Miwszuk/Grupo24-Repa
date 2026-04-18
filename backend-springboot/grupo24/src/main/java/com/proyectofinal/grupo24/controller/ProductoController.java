package com.proyectofinal.grupo24.controller;

import com.proyectofinal.grupo24.dao.ProductoDao;
import com.proyectofinal.grupo24.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoDao productoDao;

    @GetMapping
    public List<Producto> listarProductos() {
        return productoDao.getAll();
    }
}
