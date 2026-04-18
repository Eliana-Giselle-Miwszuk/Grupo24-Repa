package frgp.utn.edu.appmobile_flota.model;

public class LoginResponse {
    private Long id;
    private String nombre;
    private String rol;

    // --- AGREGA ESTE GETTER QUE ES EL QUE FALTA ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    // ----------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() { return rol; }

    public void setRol(String rol) {
        this.rol = rol;
    }
}