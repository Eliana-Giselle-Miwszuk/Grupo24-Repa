package frgp.utn.edu.appmobile_flota.model; // ESTO ARREGLA EL "Missing package"

public class Cliente {
    private String nombre;
    private String direccion;

    // Constructor necesario para los datos de prueba
    public Cliente(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    // Getters (El Adapter los necesita para leer los datos)
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
}