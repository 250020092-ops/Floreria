package com.example.floreria.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.floreria.R;
import com.example.floreria.Controlador.UsuarioControlador;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/** Pantalla 2: Registro. Solo recoge datos; la lógica vive en UsuarioControlador. */
public class Registro extends AppCompatActivity {

    private TextInputLayout tilNombre, tilCorreo, tilClave, tilConfirmar;
    private TextInputEditText etNombre, etCorreo, etClave, etConfirmar;
    private UsuarioControlador controlador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        controlador = new UsuarioControlador(this);

        tilNombre = findViewById(R.id.tilNombreReg);
        tilCorreo = findViewById(R.id.tilCorreoReg);
        tilClave = findViewById(R.id.tilClaveReg);
        tilConfirmar = findViewById(R.id.tilConfirmarReg);
        etNombre = findViewById(R.id.etNombreReg);
        etCorreo = findViewById(R.id.etCorreoReg);
        etClave = findViewById(R.id.etClaveReg);
        etConfirmar = findViewById(R.id.etConfirmarReg);

        MaterialButton btnCrear = findViewById(R.id.btnCrearCuenta);
        TextView tvVolver = findViewById(R.id.tvVolverLogin);

        btnCrear.setOnClickListener(v -> crearCuenta());
        tvVolver.setOnClickListener(v -> volverAlLogin());
    }

    private void crearCuenta() {
        // Limpia errores anteriores
        tilNombre.setError(null);
        tilCorreo.setError(null);
        tilClave.setError(null);
        tilConfirmar.setError(null);

        String nombre = textoDe(etNombre);
        String correo = textoDe(etCorreo);
        String clave = textoDe(etClave);
        String confirmar = textoDe(etConfirmar);

        // El controlador valida y guarda; devuelve null si todo salió bien
        String error = controlador.registrar(nombre, correo, clave, confirmar);

        if (error == null) {
            Toast.makeText(this, "Cuenta creada. Inicia sesión", Toast.LENGTH_SHORT).show();
            volverAlLogin();
        } else {
            mostrarError(error);
        }
    }

    /** Pone el mensaje en el campo que corresponde según su contenido. */
    private void mostrarError(String error) {
        String e = error.toLowerCase();
        if (e.contains("nombre")) tilNombre.setError(error);
        else if (e.contains("correo")) tilCorreo.setError(error);
        else if (e.contains("coinciden") || e.contains("confirm")) tilConfirmar.setError(error);
        else if (e.contains("contrase")) tilClave.setError(error);
        else Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private void volverAlLogin() {
        startActivity(new Intent(this, Login.class));
        finish();
    }

    private String textoDe(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }
}