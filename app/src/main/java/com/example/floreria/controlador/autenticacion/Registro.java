package com.example.floreria.controlador.autenticacion;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.floreria.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Map;

// Pantalla 2: Registro. Solo recoge datos; la lógica vive en UsuarioControlador
public class Registro extends AppCompatActivity {

    private TextInputLayout tilNombre, tilCorreo, tilClave, tilConfirmar;
    private TextInputEditText etNombre, etCorreo, etClave, etConfirmar;
    private final UsuarioControlador controlador = new UsuarioControlador();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

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
        tvVolver.setOnClickListener(v -> finish());
    }

    private void crearCuenta() {
        Map<String, String> errores = controlador.registrar(
                textoDe(etNombre), textoDe(etCorreo), textoDe(etClave), textoDe(etConfirmar));

        tilNombre.setError(errores.get("nombre"));
        tilCorreo.setError(errores.get("correo"));
        tilClave.setError(errores.get("clave"));
        tilConfirmar.setError(errores.get("confirmar"));

        if (errores.isEmpty()) {
            Toast.makeText(this, "Cuenta creada. Inicia sesión", Toast.LENGTH_SHORT).show();
            finish(); // vuelve al Login que ya estaba abierto
        }
    }

    private String textoDe(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }
}