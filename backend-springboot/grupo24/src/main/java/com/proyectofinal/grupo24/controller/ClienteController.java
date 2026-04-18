package com.proyectofinal.grupo24.controller;

import com.proyectofinal.grupo24.dao.ClienteDao;
import com.proyectofinal.grupo24.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClienteController {

    @Autowired
    private ClienteDao clienteDao;

    @PostMapping("/cliente/palabra-clave")
    public Cliente updatePalabraClave(@RequestBody Cliente cliente){
        return clienteDao.updatePalabraClave(cliente);
    }

    @GetMapping("/cliente/get-all")
    public List<Cliente> getAllClientes(){
        return clienteDao.getAllClientes();
    }
}
