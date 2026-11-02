package com.example.floreria.Modelo;

// Datos de un usuario de la aplicación
public class Usuario {
    public final int id;
    public final String nombre;
    public final String correo;
    public final String clave;
    public final String rol; // "admin" o "usuario"

    public Usuario(int id, String nombre, String correo, String clave, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
    }
}