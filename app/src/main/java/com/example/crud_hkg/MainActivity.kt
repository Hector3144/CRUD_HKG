package com.example.crud_hkg

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.crud_hkg.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TareaAdapter
    private lateinit var viewModel: TareaViewModel

    var tareaEdit = Tarea()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TareaViewModel::class.java]

        viewModel.listaTareas.observe(this) { tareas ->
            setupRecyclerView(tareas)
        }

        binding.btnAgregarTarea.setOnClickListener {
            val tarea = Tarea(
                Nombre = binding.Nombre.text.toString(),
                Apellido = binding.Apellido.text.toString()
            )

            viewModel.agregarTarea(tarea)

            binding.Nombre.setText("")
            binding.Apellido.setText("")
        }

        binding.btnActualizarTarea.setOnClickListener {
            tareaEdit.Nombre = ""
            tareaEdit.Apellido = ""

            tareaEdit.Nombre = binding.Nombre.text.toString()
            tareaEdit.Apellido = binding.Apellido.text.toString()

            viewModel.actualizarTarea(tareaEdit)

            adapter.notifyDataSetChanged()

            binding.Nombre.setText("")
            binding.Apellido.setText("")
        }
    }

    fun setupRecyclerView(listaTareas: List<Tarea>) {
        adapter = TareaAdapter(listaTareas, ::borrarTarea, ::actualizarTarea)
        binding.rvTareas.adapter = adapter
    }

    fun borrarTarea(id: String) {
        viewModel.borrarTarea(id)
    }

    fun actualizarTarea(tarea: Tarea) {
        tareaEdit = tarea

        binding.Nombre.setText(tareaEdit.Nombre)
        binding.Apellido.setText(tareaEdit.Apellido)
    }
}