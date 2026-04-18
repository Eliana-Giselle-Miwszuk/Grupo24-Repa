package com.proyectofinal.grupo24.dao;

import com.proyectofinal.grupo24.model.ItemPedido;
import com.proyectofinal.grupo24.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ItemPedidoDao {
    @Autowired
    private ItemPedidoRepository itemRepo;

    public void save(ItemPedido i) { itemRepo.save(i); }
}
