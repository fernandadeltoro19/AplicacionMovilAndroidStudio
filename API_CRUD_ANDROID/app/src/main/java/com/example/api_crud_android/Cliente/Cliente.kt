package com.example.api_crud_android.Cliente

data class Cliente(
    val idCliente: Int,
    val nombre: String,
    val apellido: String,
    val direccion: String,
    val telefono: Int,
    val correo: String,
    val estatus: Int = 1 // Valor por defecto
)