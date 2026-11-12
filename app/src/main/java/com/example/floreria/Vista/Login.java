package com.example.floreria.Vista;
//Pantalla de inicio de sesion

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.floreria.Controlador.UsuarioControlador;
import com.example.floreria.Modelo.Usuario;
import com.example.floreria.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Map;

// Vista de inicio de sesión
public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UsuarioControlador controlador = new UsuarioControlador();

        EditText etCorreo = findViewById(R.id.etCorreoLogin);
        EditText etClave = findViewById(R.id.etClaveLogin);
        TextInputLayout tilCorreo = findViewById(R.id.tilCorreoLogin);
        TextInputLayout tilClave = findViewById(R.id.tilClaveLogin);
        Button btnIngresar = findViewById(R.id.btnIngresar);
        TextView tvRegistro = findViewById(R.id.tvIrRegistro);

        btnIngresar.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString().trim();
            String clave = etClave.getText().toString();

            // Primero se validan los campos
            Map<String, String> errores = controlador.validarLogin(correo, clave);
            tilCorreo.setError(errores.get("correo"));
            tilClave.setError(errores.get("clave"));
            if (!errores.isEmpty()) return;

            // Después se comprueban las credenciales
            Usuario usuario = controlador.iniciarSesion(correo, clave);
            if (usuario == null) {
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Según el rol se abre el panel de administrador o el catálogo
            Intent destino;
            if (usuario.rol.equals("admin")) {
                destino = new Intent(this, Admin.class);
            } else {
                destino = new Intent(this, Catalogo.class);
            }
            destino.putExtra("nombre", usuario.nombre);
            startActivity(destino);

            // Se limpian los campos para que no queden al volver
            etCorreo.setText("");
            etClave.setText("");
        });

        // Abre la pantalla de registro
        tvRegistro.setOnClickListener(v -> startActivity(new Intent(this, Registro.class)));
    }
}