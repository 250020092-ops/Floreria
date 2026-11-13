package com.example.floreria.controlador.catalogo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.floreria.R;
import com.example.floreria.modelo.Producto;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Locale;

// Adaptador de las tarjetas del catálogo
public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.Tarjeta> {

    public interface AlAgregar {
        void agregar(Producto p);
    }

    private final List<Producto> datos;
    private final AlAgregar listener;

    public ProductoAdapter(List<Producto> datos, AlAgregar listener) {
        this.datos = datos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Tarjeta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new Tarjeta(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Tarjeta t, int posicion) {
        Producto p = datos.get(posicion);
        t.nombre.setText(p.nombre);
        t.descripcion.setText(p.descripcion);
        t.precio.setText(String.format(Locale.getDefault(), "$%.2f MXN", p.precio));
        t.btnAgregar.setOnClickListener(v -> listener.agregar(p));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    static class Tarjeta extends RecyclerView.ViewHolder {
        TextView nombre, descripcion, precio;
        MaterialButton btnAgregar;

        Tarjeta(View v) {
            super(v);
            nombre = v.findViewById(R.id.tvNombreProducto);
            descripcion = v.findViewById(R.id.tvDescripcionProducto);
            precio = v.findViewById(R.id.tvPrecioProducto);
            btnAgregar = v.findViewById(R.id.btnAgregar);
        }
    }
}