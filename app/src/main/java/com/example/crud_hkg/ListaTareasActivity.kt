package com.example.crud_hkg

import android.os.Bundle
import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crud_hkg.databinding.ActivityListaTareasBinding

class ListaTareasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaTareasBinding
    private val viewModel: TareaViewModel by viewModels()
    private lateinit var tareaAdapter: TareaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaTareasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNuevo.setOnClickListener {
            val intent = Intent(this, FormularioActivity::class.java)
            startActivity(intent)
        }

        // Configuración del botón "Home" para navegar a la actividad principal
        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Configurar RecyclerView
        binding.rvTareas.layoutManager = LinearLayoutManager(this)

        // Observando los cambios en la lista de tareas
        viewModel.listaTareas.observe(this, Observer { tareas ->
            setupRecyclerView(tareas)
        })
    }

    // Método para configurar el RecyclerView
    private fun setupRecyclerView(listaTareas: List<Tarea>) {
        tareaAdapter = TareaAdapter(listaTareas, ::borrarTarea, ::actualizarTarea)
        binding.rvTareas.adapter = tareaAdapter
    }

    // Función para eliminar tarea
    private fun borrarTarea(id: String) {
        viewModel.borrarTarea(id)
    }

    // Función para actualizar tarea
    private fun actualizarTarea(tarea: Tarea) {
        // Implementar lógica para actualizar tarea si es necesario
    }
}
