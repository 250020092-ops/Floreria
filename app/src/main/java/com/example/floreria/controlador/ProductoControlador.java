package com.example.floreria.controlador;

import com.example.floreria.modelo.Producto;
import com.example.floreria.modelo.Repositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Controlador con la lógica de los productos
public class ProductoControlador {

    public List<Producto> listar() {
        return new ArrayList<>(Repositorio.productos);
    }

    public Map<String, String> agregar(String nombre, String descripcion, String precioTexto) {
        Map<String, String> errores = validar(nombre, precioTexto);
        if (errores.isEmpty()) {
            Repositorio.agregarProducto(nombre, descripcion, Double.parseDouble(precioTexto));
        }
        return errores;
    }

    public Map<String, String> editar(int id, String nombre, String descripcion, String precioTexto) {
        Map<String, String> errores = validar(nombre, precioTexto);
        if (errores.isEmpty()) {
            Repositorio.editarProducto(id, nombre, descripcion, Double.parseDouble(precioTexto));
        }
        return errores;
    }

    public void eliminar(int id) {
        Repositorio.eliminarProducto(id);
    }

    private Map<String, String> validar(String nombre, String precioTexto) {
        Map<String, String> errores = new HashMap<>();
        String errorNombre = Validador.nombreProducto(nombre);
        String errorPrecio = Validador.precio(precioTexto);
        if (errorNombre != null) errores.put("nombre", errorNombre);
        if (errorPrecio != null) errores.put("precio", errorPrecio);
        return errores;
    }
}