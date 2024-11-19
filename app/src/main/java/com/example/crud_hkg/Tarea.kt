package com.example.crud_hkg

data class Tarea(
    var id: String = "",
    var Nombre: String = "",
    var Apellido: String = "",
    var Cedula: String = ""
) {
    fun toMap(): Map<String, String> {
        return mapOf(
            "Nombre" to Nombre,
            "Apellido" to Apellido,
            "Cedula" to Cedula
        )
    }
}