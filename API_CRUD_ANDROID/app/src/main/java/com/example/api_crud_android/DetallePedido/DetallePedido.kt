package com.example.api_crud_android.DetallePedido

data class DetallePedido(
    val idDetallePedido: Int,
    val cantidad: Int,
    val precioUnitario: Double,
    val estatus: Int = 1 // Valor por defecto
)