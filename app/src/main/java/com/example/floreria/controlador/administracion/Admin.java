package com.example.floreria.controlador.administracion;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.floreria.R;
import com.example.floreria.controlador.ProductoControlador;
import com.example.floreria.modelo.Producto;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Map;

// Pantalla 4: alta, edición y baja de productos
public class Admin extends AppCompatActivity {

    private final ProductoControlador controlador = new ProductoControlador();
    private ProductoAdminAdapter adapter;
    private Producto enEdicion = null; // null = modo "nuevo"

    private TextInputEditText etNombre, etDesc, etPrecio;
    private TextInputLayout tilNombre, tilPrecio;
    private TextView tvTitulo;
    private MaterialButton btnGuardar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        etNombre = findViewById(R.id.etNombreProd);
        etDesc = findViewById(R.id.etDescProd);
        etPrecio = findViewById(R.id.etPrecioProd);
        tilNombre = findViewById(R.id.tilNombreProd);
        tilPrecio = findViewById(R.id.tilPrecioProd);
        tvTitulo = findViewById(R.id.tvTituloFormulario);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelarEdicion);
        TextView tvSalir = findViewById(R.id.tvSalirAdmin);
        RecyclerView rv = findViewById(R.id.rvAdmin);

        adapter = new ProductoAdminAdapter(controlador.listar(), new ProductoAdminAdapter.Acciones() {
            @Override
            public void editar(Producto p) {
                entrarEnEdicion(p);
            }

            @Override
            public void eliminar(Producto p) {
                confirmarEliminar(p);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        btnGuardar.setOnClickListener(v -> guardar());
        btnCancelar.setOnClickListener(v -> salirDeEdicion());
        tvSalir.setOnClickListener(v -> finish());
    }

    private void guardar() {
        String nombre = textoDe(etNombre);
        String desc = textoDe(etDesc);
        String precio = textoDe(etPrecio);

        Map<String, String> errores = (enEdicion == null)
                ? controlador.agregar(nombre, desc, precio)
                : controlador.editar(enEdicion.id, nombre, desc, precio);

        tilNombre.setError(errores.get("nombre"));
        tilPrecio.setError(errores.get("precio"));

        if (errores.isEmpty()) {
            Toast.makeText(this,
                    enEdicion == null ? "Producto agregado" : "Producto actualizado",
                    Toast.LENGTH_SHORT).show();
            salirDeEdicion();
            adapter.actualizar(controlador.listar());
        }
    }

    // Carga el producto en el formulario para modificarlo
    private void entrarEnEdicion(Producto p) {
        enEdicion = p;
        etNombre.setText(p.nombre);
        etDesc.setText(p.descripcion);
        etPrecio.setText(String.valueOf(p.precio));
        tvTitulo.setText(R.string.admin_editando);
        btnCancelar.setVisibility(View.VISIBLE);
    }

    // Limpia el formulario y vuelve al modo "nuevo"
    private void salirDeEdicion() {
        enEdicion = null;
        etNombre.setText("");
        etDesc.setText("");
        etPrecio.setText("");
        tilNombre.setError(null);
        tilPrecio.setError(null);
        tvTitulo.setText(R.string.admin_nuevo);
        btnCancelar.setVisibility(View.GONE);
    }

    private void confirmarEliminar(Producto p) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar producto")
                .setMessage("¿Eliminar \"" + p.nombre + "\"?")
                .setPositiveButton("Eliminar", (d, c) -> {
                    controlador.eliminar(p.id);
                    adapter.actualizar(controlador.listar());
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private String textoDe(TextInputEditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }
}