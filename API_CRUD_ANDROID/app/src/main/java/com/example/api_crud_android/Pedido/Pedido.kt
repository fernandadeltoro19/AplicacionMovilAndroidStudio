package com.example.api_crud_android.Pedido

data class Pedido(
    val idPedido: Int,
    val fecha: String,
    val direccion: String,
    val estatus: Int = 1 // Valor por defecto
)