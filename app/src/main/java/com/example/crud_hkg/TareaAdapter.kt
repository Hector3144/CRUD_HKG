package com.example.crud_hkg

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TareaAdapter(
    var listaTareas: List<Tarea>,
    val onBorrarClick: (String) -> Unit,
    val onActualizarClick: (Tarea) -> Unit
) : RecyclerView.Adapter<TareaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvTarea: CardView = itemView.findViewById(R.id.cvTarea)
        val tvTitulo: TextView = itemView.findViewById(R.id.rvNombre)
        val tvDescripcion: TextView = itemView.findViewById(R.id.rvApellido)
        val tvCedula: TextView = itemView.findViewById(R.id.rvCedula)
        val tvEmail: TextView = itemView.findViewById(R.id.rvEmail)
        val tvSexo: TextView = itemView.findViewById(R.id.rvSexo)
        val ivFoto: ImageView = itemView.findViewById(R.id.rvFoto) // ImageView para la foto
        val ibtnBorrar: ImageButton = itemView.findViewById(R.id.ibtnBorrar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_tarea, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarea = listaTareas[position]

        // Asignar datos a los TextViews
        holder.tvTitulo.text = tarea.Nombre
        holder.tvDescripcion.text = tarea.Apellido
        holder.tvCedula.text = tarea.Cedula
        holder.tvEmail.text = tarea.Email
        holder.tvSexo.text = tarea.Sexo

        // Cargar la imagen desde la URI `content://`
        val uri = Uri.parse(tarea.Foto)
        Glide.with(holder.itemView.context)
            .load(uri) // Glide maneja la URI directamente
            .apply(RequestOptions()
                .placeholder(R.drawable.ic_placeholder) // Imagen mientras carga
                .error(R.drawable.ic_error) // Imagen si hay error
                .centerCrop()) // Ajustar la imagen al tamaño del ImageView
            .into(holder.ivFoto)

        // Botón de borrar
        holder.ibtnBorrar.setOnClickListener {
            onBorrarClick(tarea.id)
        }

        // Evento al hacer clic en la tarjeta
        holder.cvTarea.setOnClickListener {
            onActualizarClick(tarea)
        }

    }

    override fun getItemCount(): Int {
        return listaTareas.size
    }
}
