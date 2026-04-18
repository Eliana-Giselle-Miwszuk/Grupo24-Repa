package com.proyectofinal.grupo24.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class HojaDeRuta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha;

    @OneToOne
    @JoinColumn(name = "repartidor_id")
    @JsonIgnoreProperties({"hojaDeRuta", "pedidos"})
    private Repartidor repartidor;

    @OneToMany(mappedBy = "hojaDeRuta")
    @JsonIgnoreProperties("hojaDeRuta")
    private List<Pedido> listaPedido;

    private boolean activo;

    public HojaDeRuta(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    public List<Pedido> getListaPedido() {
        return listaPedido;
    }

    public void setListaPedido(List<Pedido> listaPedido) {
        this.listaPedido = listaPedido;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "HojaDeRuta{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", repartidor=" + repartidor +
                ", listaPedido=" + listaPedido +
                ", activo=" + activo +
                '}';
    }
}
