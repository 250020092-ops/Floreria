package com.example.floreria.Modelo;

import java.util.ArrayList;
import java.util.List;

// Almacén temporal en memoria.
// Se reemplazará por la base de datos cuando esté lista
public class Repositorio {

    public static final List<Usuario> usuarios = new ArrayList<>();
    public static final List<Producto> productos = new ArrayList<>();

    private static int siguienteIdUsuario = 2;
    private static int siguienteIdProducto = 4;

    // Se ejecuta una sola vez: carga el administrador y productos de ejemplo
    static {
        usuarios.add(new Usuario(1, "Administrador", "admin@blossom.com", "admin123", "admin"));

        productos.add(new Producto(1, "Ramo de rosas", "12 rosas rosadas con follaje", 350.0));
        productos.add(new Producto(2, "Girasoles", "Ramo de 6 girasoles", 280.0));
        productos.add(new Producto(3, "Caja de tulipanes", "Caja con 15 tulipanes", 420.0));
    }

    // Devuelve true si se agregó, false si el correo ya existe
    public static boolean agregarUsuario(String nombre, String correo, String clave) {
        for (Usuario u : usuarios) {
            if (u.correo.equalsIgnoreCase(correo)) {
                return false;
            }
        }
        usuarios.add(new Usuario(siguienteIdUsuario++, nombre, correo, clave, "usuario"));
        return true;
    }

    // Devuelve el usuario si el correo y la clave coinciden, o null si no
    public static Usuario buscarUsuario(String correo, String clave) {
        for (Usuario u : usuarios) {
            if (u.correo.equalsIgnoreCase(correo) && u.clave.equals(clave)) {
                return u;
            }
        }
        return null;
    }

    // Agrega el producto al inicio de la lista
    public static void agregarProducto(String nombre, String descripcion, double precio) {
        productos.add(0, new Producto(siguienteIdProducto++, nombre, descripcion, precio));
    }

    // Elimina un producto por su id
    public static void eliminarProducto(int id) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).id == id) {
                productos.remove(i);
                return;
            }
        }
    }
}