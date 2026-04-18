package com.proyectofinal.grupo24.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Embeddable //Los datos van a estar dentro de la tabla repartidor. Al actualizarse constantemente, que se sobreescriba
public class Ubicacion {

    private double latitud;
    private double longitud;
    private LocalDateTime ultimaActualizacion;

    public Ubicacion(){

    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public LocalDateTime getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(LocalDateTime ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }
}
