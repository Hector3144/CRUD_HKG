package com.example.crud_hkg

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crud_hkg.databinding.ActivityFormularioBinding
import java.io.ByteArrayOutputStream
import java.io.InputStream

class FormularioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormularioBinding
    private lateinit var viewModel: TareaViewModel
    private var fotoUri: Uri? = null // Variable para almacenar la URI de la imagen seleccionada
    private var fotoBase64: String? = null // Variable para almacenar la imagen en formato Base64

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el Spinner para selección de sexo
        configurarSpinnerSexo()

        // Configurar el botón para volver a Home
        binding.btnVolverHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Configurar el botón para seleccionar una foto
        binding.btnSeleccionarFoto.setOnClickListener {
            abrirGaleria()
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
                Cedula = binding.Cedula.text.toString(),
                Email = binding.Email.text.toString(),
                Sexo = binding.spinnerSexo.selectedItem.toString(),
                Foto = fotoBase64 ?: "" // Guardar la imagen en formato Base64
            )
            viewModel.agregarTarea(tarea)
            limpiarCampos()
            val intent = Intent(this, ListaTareasActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun configurarSpinnerSexo() {
        val opcionesSexo = listOf("Tipo de Sexo", "Hombre", "Mujer", "Freddy", "nose")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opcionesSexo)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSexo.adapter = adapter
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        startActivityForResult(intent, 100) // Código de solicitud único para identificar la respuesta
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            fotoUri = data?.data // Obtener la URI de la imagen seleccionada
            fotoUri?.let { uri ->
                val bitmap = obtenerBitmapDesdeUri(uri)
                val circularBitmap = transformarImagenCircular(bitmap)
                binding.imgFoto.setImageBitmap(circularBitmap) // Mostrar la imagen en forma circular
                fotoBase64 = convertirImagenABase64(bitmap) // Convertir la imagen circular a Base64
            }
        }
    }

    private fun obtenerBitmapDesdeUri(uri: Uri): Bitmap {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream!!)
    }

    private fun transformarImagenCircular(bitmap: Bitmap): Bitmap {
        val size = Math.min(bitmap.width, bitmap.height)
        val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)

        val canvas = android.graphics.Canvas(output)
        val paint = android.graphics.Paint()
        val shader = android.graphics.BitmapShader(bitmap, android.graphics.Shader.TileMode.CLAMP, android.graphics.Shader.TileMode.CLAMP)

        paint.isAntiAlias = true
        paint.shader = shader

        val radius = size / 2f
        canvas.drawCircle(radius, radius, radius, paint)

        return output
    }

    private fun convertirImagenABase64(bitmap: Bitmap): String? {
        return try {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            val byteArray = outputStream.toByteArray()

            // Prefijo para Base64
            "data:image/jpeg;base64," + Base64.encodeToString(byteArray, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al convertir la imagen", Toast.LENGTH_SHORT).show()
            null
        }
    }

    private fun validarCampos(): Boolean {
        return !(binding.Nombre.text.isNullOrBlank() ||
                binding.Apellido.text.isNullOrBlank() ||
                binding.Cedula.text.isNullOrBlank() ||
                binding.Email.text.isNullOrBlank() ||
                binding.Nacimiento.text.isNullOrBlank() ||
                binding.spinnerSexo.selectedItem == null)
    }

    private fun limpiarCampos() {
        binding.Nombre.setText("")
        binding.Apellido.setText("")
        binding.Cedula.setText("")
        binding.Email.setText("")
        binding.Nacimiento.setText("")
        binding.spinnerSexo.setSelection(0)
        fotoUri = null
        binding.imgFoto.setImageResource(android.R.color.transparent) // Limpiar la imagen seleccionada
    }
}
