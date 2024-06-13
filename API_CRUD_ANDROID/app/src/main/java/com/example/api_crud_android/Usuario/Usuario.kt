package com.example.api_crud_android.Usuario


data class Usuario(
    val idUsuario: Int,
    val nombreUsuario: String,
    val correo: String,
    val contrasena: String,
    val rol: String,
    val estatus: Int
)
