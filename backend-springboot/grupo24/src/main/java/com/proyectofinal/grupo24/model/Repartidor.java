package com.proyectofinal.grupo24.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyectofinal.grupo24.enums.EstadoRepartidor;

import jakarta.persistence.*;

import java.util.List;

@Entity
@DiscriminatorValue("Repartidor")
public class Repartidor extends Usuario {

    @Enumerated(EnumType.STRING)
    private EstadoRepartidor estado;

    @OneToMany(mappedBy = "repartidorAsignado")
    @JsonIgnore
    private List<Pedido> pedidosAsignados;

    @Embedded
    private Ubicacion ubicacionActual;

    @OneToOne(mappedBy = "repartidor")
    @JsonIgnore
    private HojaDeRuta hojaDeRuta;

    private String patente;

    public Repartidor() {
    }

    public EstadoRepartidor getEstado() {
        return estado;
    }

    public void setEstado(EstadoRepartidor estado) {
        this.estado = estado;
    }

    public List<Pedido> getPedidosAsignados() {
        return pedidosAsignados;
    }

    public void setPedidosAsignados(List<Pedido> pedidosAsignados) {
        this.pedidosAsignados = pedidosAsignados;
    }

    public Ubicacion getUbicacionActual() {
        return ubicacionActual;
    }

    public void setUbicacionActual(Ubicacion ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

    public HojaDeRuta getHojaDeRuta() {
        return hojaDeRuta;
    }

    public void setHojaDeRuta(HojaDeRuta hojaDeRuta) {
        this.hojaDeRuta = hojaDeRuta;
    }

    public String getPatente() { return patente; }
    public void setPatente(String patente) { this.patente = patente; }

    @Override
    public String toString() {
        return "com.proyectofinal.grupo24.model.Repartidor{" +
                "estado=" + estado +
                ", pedidosAsignados=" + pedidosAsignados +
                ", ubicacionActual=" + ubicacionActual +
                ", hojaDeRuta=" + hojaDeRuta +
                '}';
    }
}
