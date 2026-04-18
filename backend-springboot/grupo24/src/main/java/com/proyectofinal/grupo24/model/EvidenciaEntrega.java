package com.proyectofinal.grupo24.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class EvidenciaEntrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foto;
    private boolean palabraClaveValidada;
    private LocalDateTime fechaHoraEntrega;
    private Ubicacion ubicacionEntrega;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public boolean isPalabraClaveValidada() {
        return palabraClaveValidada;
    }

    public void setPalabraClaveValidada(boolean palabraClaveValidada) {
        this.palabraClaveValidada = palabraClaveValidada;
    }

    public LocalDateTime getFechaHoraEntrega() {
        return fechaHoraEntrega;
    }

    public void setFechaHoraEntrega(LocalDateTime fechaHoraEntrega) {
        this.fechaHoraEntrega = fechaHoraEntrega;
    }

    public Ubicacion getUbicacionEntrega() {
        return ubicacionEntrega;
    }

    public void setUbicacionEntrega(Ubicacion ubicacionEntrega) {
        this.ubicacionEntrega = ubicacionEntrega;
    }
}
