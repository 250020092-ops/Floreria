package com.example.floreria.Controlador;

import com.example.floreria.Modelo.Repositorio;
import com.example.floreria.Modelo.Usuario;

import java.util.HashMap;
import java.util.Map;

// Controlador con la lógica de registro e inicio de sesión
public class UsuarioControlador {

    // Valida los datos y registra al usuario.
    // Devuelve un mapa campo -> error; si viene vacío, el registro fue exitoso
    public Map<String, String> registrar(String nombre, String correo, String clave) {
        Map<String, String> errores = new HashMap<>();

        String errorNombre = Validador.nombre(nombre);
        String errorCorreo = Validador.correo(correo);
        String errorClave = Validador.clave(clave);

        if (errorNombre != null) errores.put("nombre", errorNombre);
        if (errorCorreo != null) errores.put("correo", errorCorreo);
        if (errorClave != null) errores.put("clave", errorClave);

        // Solo se intenta guardar si todos los campos son válidos
        if (errores.isEmpty() && !Repositorio.agregarUsuario(nombre, correo, clave)) {
            errores.put("correo", "Ese correo ya está registrado");
        }
        return errores;
    }

    // Valida que los campos del login estén completos
    public Map<String, String> validarLogin(String correo, String clave) {
        Map<String, String> errores = new HashMap<>();

        String errorCorreo = Validador.correo(correo);
        if (errorCorreo != null) errores.put("correo", errorCorreo);
        if (clave.isEmpty()) errores.put("clave", "Escribe tu contraseña");

        return errores;
    }

    // Devuelve el usuario si las credenciales son correctas, o null si no
    public Usuario iniciarSesion(String correo, String clave) {
        return Repositorio.buscarUsuario(correo, clave);
    }
}