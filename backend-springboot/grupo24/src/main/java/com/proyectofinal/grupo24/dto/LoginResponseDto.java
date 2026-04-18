package com.proyectofinal.grupo24.dto;

public class LoginResponseDto {
    private Long id;
    private String nombre;
    private String rol;

    public LoginResponseDto() {
    }

    public LoginResponseDto(Long id, String nombre, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}