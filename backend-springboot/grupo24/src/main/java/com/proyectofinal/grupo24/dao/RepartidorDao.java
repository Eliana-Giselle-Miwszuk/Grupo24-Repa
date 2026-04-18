package com.proyectofinal.grupo24.dao;



import com.proyectofinal.grupo24.model.Repartidor;
import com.proyectofinal.grupo24.model.Ubicacion;
import com.proyectofinal.grupo24.model.Usuario;
import com.proyectofinal.grupo24.repository.RepartidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RepartidorDao {

    @Autowired
    private RepartidorRepository repository;

    public Repartidor save(Repartidor repartidor){
        return repository.save(repartidor);
    }

    public List<Repartidor> getAllRepartidores(){
        return (List<Repartidor>) repository.findAll();
    }

    public void delete(Repartidor repartidor){
        repository.delete(repartidor);
    }

    public Repartidor update(Repartidor repartidor) {
        if (repartidor.getId() != null && repository.existsById(repartidor.getId())) {
            return repository.save(repartidor);
        }
        return null;
    }

    public Repartidor actualizarPosicion(Long id, Double lat, Double lon) {
        return repository.findById(id).map(repartidor -> {
            Ubicacion nuevaUbi = new Ubicacion();
            nuevaUbi.setLatitud(lat);
            nuevaUbi.setLongitud(lon);
            nuevaUbi.setUltimaActualizacion(LocalDateTime.now());

            repartidor.setUbicacionActual(nuevaUbi);

            return repository.save(repartidor);
        }).orElseThrow(() -> new RuntimeException("Repartidor no encontrado"));
    }
}

