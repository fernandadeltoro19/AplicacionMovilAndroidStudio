package com.example.api_crud_android.Categoria

data class Categoria(
    val idCategoria: Int,
    val nombre: String,
    val estatus: Int = 1 // Valor por defecto

)
