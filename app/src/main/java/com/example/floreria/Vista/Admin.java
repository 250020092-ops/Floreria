package com.example.floreria.Vista;
//pantalla del panel de administracion

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.floreria.Controlador.ProductoControlador;
import com.example.floreria.Modelo.Producto;
import com.example.floreria.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

// Vista del administrador: alta y baja de productos
public class Admin extends AppCompatActivity {

    private ProductoControlador controlador;
    private ListView lvAdmin;
    private List<Producto> productos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        controlador = new ProductoControlador();
        lvAdmin = findViewById(R.id.lvAdmin);

        EditText etNombre = findViewById(R.id.etNombreProd);
        EditText etDesc = findViewById(R.id.etDescProd);
        EditText etPrecio = findViewById(R.id.etPrecioProd);
        TextInputLayout tilNombre = findViewById(R.id.tilNombreProd);
        TextInputLayout tilPrecio = findViewById(R.id.tilPrecioProd);
        Button btnAgregar = findViewById(R.id.btnAgregar);
        Button btnSalir = findViewById(R.id.btnSalirAdmin);

        cargarLista();

        btnAgregar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString().trim();
            String descripcion = etDesc.getText().toString().trim();
            String precio = etPrecio.getText().toString().trim();

            // El controlador valida y guarda; devuelve los errores por campo
            Map<String, String> errores = controlador.agregar(nombre, descripcion, precio);

            tilNombre.setError(errores.get("nombre"));
            tilPrecio.setError(errores.get("precio"));

            if (errores.isEmpty()) {
                // Se limpian los campos y se refresca la lista
                etNombre.setText("");
                etDesc.setText("");
                etPrecio.setText("");
                cargarLista();
                Toast.makeText(this, "Producto agregado", Toast.LENGTH_SHORT).show();
            }
        });

        // Pulsación larga sobre un producto para eliminarlo
        lvAdmin.setOnItemLongClickListener((parent, vista, posicion, id) -> {
            Producto producto = productos.get(posicion);
            new AlertDialog.Builder(this)
                    .setTitle("Eliminar producto")
                    .setMessage("¿Eliminar \"" + producto.nombre + "\"?")
                    .setPositiveButton("Eliminar", (dialogo, cual) -> {
                        controlador.eliminar(producto.id);
                        cargarLista();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
            return true;
        });

        btnSalir.setOnClickListener(v -> finish());
    }

    // Pide los productos al controlador y los muestra en la lista
    private void cargarLista() {
        productos = controlador.listar();
        List<String> textos = new ArrayList<>();
        for (Producto p : productos) {
            textos.add(p.nombre + " - $" + String.format(Locale.getDefault(), "%.2f", p.precio)
                    + "\n" + p.descripcion);
        }
        lvAdmin.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, textos));
    }
}
