package com.example.floreria.controlador;

import android.util.Patterns;

import java.util.regex.Pattern;

// Reglas de validación reutilizables.
// Cada método devuelve null si el dato es correcto, o el mensaje de error si no
public class Validador {

    // Solo letras (con acentos y ñ) y espacios
    private static final Pattern SOLO_LETRAS = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúÑñÜü ]+$");

    public static String nombre(String texto) {
        if (texto.isEmpty()) return "Escribe tu nombre";
        if (texto.length() < 3) return "El nombre debe tener al menos 3 letras";
        if (!SOLO_LETRAS.matcher(texto).matches()) return "El nombre solo puede tener letras";
        return null;
    }

    public static String correo(String texto) {
        if (texto.isEmpty()) return "Escribe tu correo";
        if (!Patterns.EMAIL_ADDRESS.matcher(texto).matches()) {
            return "El correo no tiene un formato válido";
        }
        return null;
    }

    public static String clave(String texto) {
        if (texto.isEmpty()) return "Escribe tu contraseña";
        if (texto.length() < 6) return "Debe tener al menos 6 caracteres";

        boolean hayLetra = false;
        boolean hayNumero = false;
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) hayLetra = true;
            if (Character.isDigit(c)) hayNumero = true;
        }
        if (!hayLetra) return "Debe incluir al menos una letra";
        if (!hayNumero) return "Debe incluir al menos un número";
        return null;
    }

    public static String nombreProducto(String texto) {
        if (texto.isEmpty()) return "Escribe el nombre del arreglo";
        return null;
    }

    public static String precio(String texto) {
        if (texto.isEmpty()) return "Escribe el precio";
        double valor;
        try {
            valor = Double.parseDouble(texto);
        } catch (NumberFormatException e) {
            return "El precio no es un número válido";
        }
        if (valor <= 0) return "El precio debe ser mayor a 0";
        return null;
    }
}
