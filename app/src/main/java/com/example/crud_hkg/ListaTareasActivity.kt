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


        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        binding.rvTareas.layoutManager = LinearLayoutManager(this)


        viewModel.listaTareas.observe(this, Observer { tareas ->
            setupRecyclerView(tareas)
        })
    }


    private fun setupRecyclerView(listaTareas: List<Tarea>) {
        tareaAdapter = TareaAdapter(listaTareas, ::borrarTarea, ::actualizarTarea)
        binding.rvTareas.adapter = tareaAdapter
    }


    private fun borrarTarea(id: String) {
        viewModel.borrarTarea(id)
    }
    private fun actualizarTarea(tarea: Tarea) {
    }
}
