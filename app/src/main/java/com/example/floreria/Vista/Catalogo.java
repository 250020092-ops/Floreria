package com.example.floreria.Vista;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.floreria.Controlador.ProductoControlador;
import com.example.floreria.Modelo.Producto;
import com.example.floreria.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Vista del catálogo para los usuarios
public class Catalogo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        ProductoControlador controlador = new ProductoControlador();

        // El nombre llega desde el login
        String nombre = getIntent().getStringExtra("nombre");
        if (nombre == null) nombre = "Cliente";
        TextView tvBienvenida = findViewById(R.id.tvBienvenida);
        tvBienvenida.setText("Hola, " + nombre);

        // Se arma un texto por cada producto: nombre, descripción y precio
        List<String> textos = new ArrayList<>();
        for (Producto p : controlador.listar()) {
            textos.add(p.nombre + "\n" + p.descripcion + "\n$"
                    + String.format(Locale.getDefault(), "%.2f", p.precio) + " MXN");
        }

        ListView lista = findViewById(R.id.lvProductos);
        lista.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, textos));

        // Cierra la pantalla y regresa al login
        Button btnSalir = findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(v -> finish());
    }
}