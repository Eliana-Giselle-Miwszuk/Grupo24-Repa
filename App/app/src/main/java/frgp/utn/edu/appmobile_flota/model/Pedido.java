package frgp.utn.edu.appmobile_flota.model;

import com.google.gson.annotations.SerializedName;

public class Pedido {
    private Long id;

    @SerializedName("numeroPedido")
    private String numero_pedido;

    private String estado;
    private Cliente cliente;

    public Pedido() {}

    // --- GETTERS Y SETTERS (Cruciales para que compile) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero_pedido() {
        return numero_pedido;
    }

    public void setNumero_pedido(String numero_pedido) {
        this.numero_pedido = numero_pedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}