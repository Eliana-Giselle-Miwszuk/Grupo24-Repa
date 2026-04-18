package com.proyectofinal.grupo24.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.proyectofinal.grupo24.enums.EstadoPedido;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroPedido;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ItemPedido> listaItems;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "repartidor_id")
    @JsonIgnoreProperties({"pedidos", "hojaDeRuta"})
    private Repartidor repartidorAsignado;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate fechaCreacion;

    private LocalDate fechaEntrega;

    @OneToOne
    @JoinColumn(name = "evidencia_entrega_id")
    private EvidenciaEntrega evidenciaEntrega;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "incidencia_id")
    private Incidencia incidencia;

    @ManyToOne
    @JoinColumn(name = "hoja_id")
    @JsonIgnoreProperties({"pedidos", "repartidor"})
    private HojaDeRuta hojaDeRuta;

    // --- GETTERS Y SETTERS (Asegúrate de que estén TODOS estos) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroPedido() { return numeroPedido; }
    public void setNumeroPedido(String numeroPedido) { this.numeroPedido = numeroPedido; }

    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }

    // ESTE ES EL QUE FALTABA PARA VER AL CLIENTE EN ANDROID
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<ItemPedido> getListaItems() { return listaItems; }
    public void setListaItems(List<ItemPedido> listaItems) { this.listaItems = listaItems; }

    public Incidencia getIncidencia() { return incidencia; }
    public void setIncidencia(Incidencia incidencia) { this.incidencia = incidencia; }

    public void setRepartidorAsignado(Repartidor repartidorAsignado) { this.repartidorAsignado = repartidorAsignado; }
    public void setHojaDeRuta(HojaDeRuta hojaDeRuta) { this.hojaDeRuta = hojaDeRuta; }
// Busca donde están tus otros métodos y pega este:

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    // También asegúrate de tener el Getter por si lo necesitas luego:
    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }
}