package com.example.floreria.controlador.autenticacion;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.floreria.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Map;

// Vista de registro: captura los datos y muestra los errores
public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        UsuarioControlador controlador = new UsuarioControlador();

        EditText etNombre = findViewById(R.id.etNombre);
        EditText etCorreo = findViewById(R.id.etCorreo);
        EditText etClave = findViewById(R.id.etClave);
        TextInputLayout tilNombre = findViewById(R.id.tilNombre);
        TextInputLayout tilCorreo = findViewById(R.id.tilCorreo);
        TextInputLayout tilClave = findViewById(R.id.tilClave);
        Button btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();
            String clave = etClave.getText().toString();

            // El controlador valida y registra; devuelve los errores por campo
            Map<String, String> errores = controlador.registrar(nombre, correo, clave);

            // Asignar null borra el mensaje anterior
            tilNombre.setError(errores.get("nombre"));
            tilCorreo.setError(errores.get("correo"));
            tilClave.setError(errores.get("clave"));

            if (errores.isEmpty()) {
                Toast.makeText(this, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show();
                finish(); // vuelve a la pantalla anterior
            }
        });
    }
}