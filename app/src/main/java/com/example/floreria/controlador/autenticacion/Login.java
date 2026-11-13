package com.example.floreria.controlador.autenticacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.floreria.R;
import com.example.floreria.controlador.administracion.Admin;
import com.example.floreria.controlador.catalogo.Catalogo;
import com.example.floreria.modelo.Usuario;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Map;

// Vista de inicio de sesión y bienvenida inspirada en el prototipo
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
        View cardLogin = findViewById(R.id.cardLoginContainer);
        Button btnComenzar = findViewById(R.id.btnComenzar);
        TextView tvYaTengoCuenta = findViewById(R.id.tvYaTengoCuenta);

        // Mostrar formulario de inicio de sesión al pulsar Comenzar o Ya tengo una cuenta
        View.OnClickListener mostrarLogin = v -> {
            cardLogin.setVisibility(View.VISIBLE);
            cardLogin.requestFocus();
        };

        btnComenzar.setOnClickListener(mostrarLogin);
        tvYaTengoCuenta.setOnClickListener(mostrarLogin);

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

            // Se limpian los campos y se oculta el login al volver
            etCorreo.setText("");
            etClave.setText("");
            cardLogin.setVisibility(View.GONE);
        });

        // Abre la pantalla de registro
        tvRegistro.setOnClickListener(v -> startActivity(new Intent(this, Registro.class)));
    }
}
