package com.example.floreria.controlador.catalogo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.floreria.R;
import com.example.floreria.controlador.ProductoControlador;
import com.example.floreria.modelo.Producto;

import java.util.List;

// Pantalla 3: catálogo de arreglos florales para los usuarios
public class Catalogo extends AppCompatActivity {

    private TextView tvCarrito;
    private int seleccionados = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ProductoControlador controlador = new ProductoControlador();

        tvCarrito = findViewById(R.id.tvCarrito);
        TextView tvSalir = findViewById(R.id.tvSalirCatalogo);
        TextView tvVacio = findViewById(R.id.tvVacioCatalogo);
        RecyclerView rv = findViewById(R.id.rvCatalogo);

        actualizarContador();

        List<Producto> productos = controlador.listar();

        // Si no hay productos se muestra el mensaje de lista vacía
        if (productos.isEmpty()) {
            tvVacio.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }

        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setAdapter(new ProductoAdapter(productos, producto -> {
            seleccionados++;
            actualizarContador();
            Toast.makeText(this, producto.nombre + " agregado", Toast.LENGTH_SHORT).show();
        }));

        // Cierra la pantalla y regresa al login
        tvSalir.setOnClickListener(v -> finish());
    }

    private void actualizarContador() {
        tvCarrito.setText(getString(R.string.cat_carrito, seleccionados));
    }
}
