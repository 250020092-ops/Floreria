package com.example.floreria.controlador.administracion;

import android.annotation.SuppressLint;
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

public class ProductoAdminAdapter extends RecyclerView.Adapter<ProductoAdminAdapter.TarjetaAdmin> {

    public interface Acciones {
        void editar(Producto p);
        void eliminar(Producto p);
    }

    private List<Producto> datos;
    private final Acciones acciones;

    public ProductoAdminAdapter(List<Producto> datos, Acciones acciones) {
        this.datos = datos;
        this.acciones = acciones;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void actualizar(List<Producto> nuevosDatos) {
        this.datos = nuevosDatos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TarjetaAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto_admin, parent, false);
        return new TarjetaAdmin(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TarjetaAdmin holder, int position) {
        Producto p = datos.get(position);
        holder.nombre.setText(p.nombre);
        holder.descripcion.setText(p.descripcion);
        holder.precio.setText(String.format(Locale.getDefault(), "$%.2f MXN", p.precio));

        holder.btnEditar.setOnClickListener(v -> acciones.editar(p));
        holder.btnEliminar.setOnClickListener(v -> acciones.eliminar(p));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class TarjetaAdmin extends RecyclerView.ViewHolder {
        TextView nombre, descripcion, precio;
        MaterialButton btnEditar, btnEliminar;

        TarjetaAdmin(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tvNombreProducto);
            descripcion = itemView.findViewById(R.id.tvDescripcionProducto);
            precio = itemView.findViewById(R.id.tvPrecioProducto);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
