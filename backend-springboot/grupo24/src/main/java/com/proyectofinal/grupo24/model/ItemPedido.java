package com.proyectofinal.grupo24.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonBackReference
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private int cantidad;

    // Getters y Setters normales...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    @Override
    public String toString() {
        // CORREGIDO: Usamos la descripción del producto y NO imprimimos el objeto 'pedido'
        String desc = (producto != null) ? producto.getDescripcion() : "Producto";
        return "ItemPedido{" +
                "id=" + id +
                ", producto=" + desc +
                ", cantidad=" + cantidad +
                '}';
    }
}