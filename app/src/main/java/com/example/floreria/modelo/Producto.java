package com.example.floreria.modelo;

// Datos de un arreglo floral del catálogo
public class Producto {
    public final int id;
    public final String nombre;
    public final String descripcion;
    public final double precio;

    public Producto(int id, String nombre, String descripcion, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }
}
