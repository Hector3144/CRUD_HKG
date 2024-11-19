package com.example.crud_hkg

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crud_hkg.databinding.ActivityFormularioBinding

class FormularioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormularioBinding
    private lateinit var viewModel: TareaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el botón para volver a Home
        binding.btnVolverHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Termina la actividad actual para evitar que el usuario regrese a ella usando el botón de atrás
        }



        viewModel = TareaViewModel()

        binding.btnAgregarTarea.setOnClickListener {
            if (!validarCampos()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tarea = Tarea(
                Nombre = binding.Nombre.text.toString(),
                Apellido = binding.Apellido.text.toString(),
                Cedula = binding.Cedula.text.toString()
            )
            viewModel.agregarTarea(tarea)
            limpiarCampos()


        }
    }

    private fun validarCampos(): Boolean {
        return !(binding.Nombre.text.isNullOrBlank() ||
                binding.Apellido.text.isNullOrBlank() ||
                binding.Cedula.text.isNullOrBlank())
    }

    private fun limpiarCampos() {
        binding.Nombre.setText("")
        binding.Apellido.setText("")
        binding.Cedula.setText("")
    }
}
