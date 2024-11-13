package com.example.crud_hkg

data class Tarea(
    var id: String = "",
    var Nombre: String = "",
    var Apellido: String = ""
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "titulo" to Nombre,
            "descripcion" to Apellido
        )
    }
}