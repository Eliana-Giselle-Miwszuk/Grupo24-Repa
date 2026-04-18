package com.proyectofinal.grupo24.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Incidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String motivo;

    @Column(name = "descripcion_adicional")
    private String descripcionAdicional;

    private LocalDateTime fechaHora;

    public Incidencia() {
        this.fechaHora = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getDescripcionAdicional() { return descripcionAdicional; }
    public void setDescripcionAdicional(String descripcionAdicional) { this.descripcionAdicional = descripcionAdicional; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    // Método puente para el DAO
    public void setDescripcion(String descripcion) {
        this.descripcionAdicional = descripcion;
    }
}