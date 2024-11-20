package com.example.crud_hkg

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

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
        val ivFoto: ImageView = itemView.findViewById(R.id.rvFoto)
        val btnBorrar: Button = itemView.findViewById(R.id.btnBorrar)  // Cambiado de ImageButton a Button
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_tarea, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarea = listaTareas[position]

        // Asignar datos a los TextViews con el formato Nombre: Dato
        holder.tvTitulo.text = "Nombre: ${tarea.Nombre}"
        holder.tvDescripcion.text = "Apellido: ${tarea.Apellido}"
        holder.tvCedula.text = "Cédula: ${tarea.Cedula}"
        holder.tvEmail.text = "Correo: ${tarea.Email}"
        holder.tvSexo.text = "Sexo: ${tarea.Sexo}"

        // Decodificar Base64 y cargar la imagen
        if (!tarea.Foto.isNullOrEmpty() && tarea.Foto.startsWith("data:image")) {
            try {
                // Remover el encabezado si existe
                val base64Image = tarea.Foto.substringAfter(",")
                // Decodificar los bytes
                val decodedBytes = Base64.decode(base64Image, Base64.DEFAULT)
                // Convertir a Bitmap
                val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                // Mostrar en el ImageView
                holder.ivFoto.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                // Imagen de error si algo falla
                holder.ivFoto.setImageResource(R.drawable.ic_error)
            }
        } else {
            // Imagen de marcador si no hay imagen válida
            holder.ivFoto.setImageResource(R.drawable.ic_placeholder)
        }

        // Botón de borrar
        holder.btnBorrar.setOnClickListener {
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
