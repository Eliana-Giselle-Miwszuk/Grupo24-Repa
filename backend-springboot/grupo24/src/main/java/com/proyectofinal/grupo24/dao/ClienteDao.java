package com.proyectofinal.grupo24.dao;

import com.proyectofinal.grupo24.model.Cliente;
import com.proyectofinal.grupo24.model.Pedido;
import com.proyectofinal.grupo24.model.Usuario;
import com.proyectofinal.grupo24.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteDao {

    @Autowired
    private ClienteRepository repository;

    //Buscar TODOS
    public List<Cliente> getAllClientes(){
        return (List<Cliente>) repository.findAll();
    }
    public Cliente updatePalabraClave(Cliente cliente) {
        if (cliente.getId() != null) {
            return repository.findById(cliente.getId()).map(clienteExistente -> {
                // Si el cliente mandó una palabra clave nueva, se la asignamos al existente
                if (cliente.getPalabraClaveEntrega() != null) {
                    clienteExistente.setPalabraClaveEntrega(cliente.getPalabraClaveEntrega());
                }
                // GUARDAMOS EL EXISTENTE (el que tiene todos los datos del hardcodeo)
                return repository.save(clienteExistente);
            }).orElse(null);
        }
        return null;
    }

    public void save(Cliente cliente) { repository.save(cliente); }
}