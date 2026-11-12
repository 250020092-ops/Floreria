package com.example.floreria.controlador;

import com.example.floreria.modelo.Producto;
import com.example.floreria.modelo.Repositorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Controlador con la lógica de los productos
public class ProductoControlador {

    // Devuelve una copia de la lista de productos
    public List<Producto> listar() {
        return new ArrayList<>(Repositorio.productos);
    }

    // Valida los datos y agrega el producto.
    // Devuelve un mapa campo -> error; si viene vacío, el producto se guardó
    public Map<String, String> agregar(String nombre, String descripcion, String precioTexto) {
        Map<String, String> errores = new HashMap<>();

        String errorNombre = Validador.nombreProducto(nombre);
        String errorPrecio = Validador.precio(precioTexto);

        if (errorNombre != null) errores.put("nombre", errorNombre);
        if (errorPrecio != null) errores.put("precio", errorPrecio);

        if (errores.isEmpty()) {
            Repositorio.agregarProducto(nombre, descripcion, Double.parseDouble(precioTexto));
        }
        return errores;
    }

    // Elimina un producto por su id
    public void eliminar(int id) {
        Repositorio.eliminarProducto(id);
    }
}