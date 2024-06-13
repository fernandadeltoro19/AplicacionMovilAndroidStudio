package com.example.api_crud_android.Usuario

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.api_crud_android.R
import com.example.api_crud_android.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertarUsuario : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextContrasena: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextRol: EditText
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    private val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insertarusuario)

        editTextNombre = findViewById(R.id.editTextNombreUsuario)
        editTextContrasena = findViewById(R.id.editTextPrecioContrasena)
        editTextCorreo = findViewById(R.id.editTextCorreo)
        editTextRol = findViewById(R.id.editTextRol)
        buttonInsertar = findViewById(R.id.buttonInsertar)
        buttonRegresar = findViewById(R.id.buttonRegresar)

        buttonInsertar.setOnClickListener {
            if (verificarCamposVacios()) {
                val nombreUsuario = editTextNombre.text.toString()
                val contrasena = editTextContrasena.text.toString()
                val correo = editTextCorreo.text.toString()
                val rol = editTextRol.text.toString()
                insertarNuevoUsuario(nombreUsuario, contrasena, correo, rol)
            } else {
                mostrarMensaje("Por favor completa todos los campos")
            }
        }

        buttonRegresar.setOnClickListener {
            finish()
        }
    }

    private fun insertarNuevoUsuario(nombreUsuario: String, contrasena: String, correo: String, rol: String) {
        // Desactivar el botón de insertar
        buttonInsertar.isEnabled = false

        apiService.agregarUsuario(nombreUsuario, contrasena, correo, rol)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        mostrarMensaje("Usuario agregado exitosamente")
                        //limpiarCampos()
                        // No reactivar el botón en caso de éxito
                    } else if (response.code() == 409) {
                        mostrarMensaje("El correo ya está en uso")
                        // Reactivar el botón de insertar en caso de error
                        buttonInsertar.isEnabled = true
                    } else {
                        val errorBody = response.errorBody()?.string()
                        mostrarMensaje("Error al agregar usuario: ${response.code()} - ${errorBody}")
                        // Reactivar el botón de insertar en caso de error
                        buttonInsertar.isEnabled = true
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    mostrarMensaje("Error al conectar con el servidor: ${t.message}")
                    // Reactivar el botón de insertar en caso de fallo en la conexión
                    buttonInsertar.isEnabled = true
                }
            })
    }

    private fun limpiarCampos() {
        editTextNombre.text.clear()
        editTextContrasena.text.clear()
        editTextCorreo.text.clear()
        editTextRol.text.clear()
    }

    private fun verificarCamposVacios(): Boolean {
        return editTextNombre.text.isNotEmpty() &&
                editTextContrasena.text.isNotEmpty() &&
                editTextCorreo.text.isNotEmpty() &&
                editTextRol.text.isNotEmpty()
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}