package com.example.api_crud_android.Producto

data class Producto(
    val idProducto: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val estatus: Int = 1 // Valor por defecto
)