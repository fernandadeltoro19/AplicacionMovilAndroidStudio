package com.example.api_crud_android.Usuario

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.api_crud_android.R
import com.example.api_crud_android.network.LoginActivity
import com.example.api_crud_android.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextContrasena: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextRol: EditText
    private lateinit var buttonRegistrar: Button
    private lateinit var buttonRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTextNombre = findViewById(R.id.editTextNombre)
        editTextContrasena = findViewById(R.id.editTextContrasena)
        editTextCorreo = findViewById(R.id.editTextCorreo)
        editTextRol = findViewById(R.id.editTextRol)
        buttonRegistrar = findViewById(R.id.buttonRegistrar)
        buttonRegresar = findViewById(R.id.buttonRegresar)

        buttonRegistrar.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val contrasena = editTextContrasena.text.toString()
            val correo = editTextCorreo.text.toString()
            val rol = editTextRol.text.toString()

            if (nombre.isNotEmpty() && contrasena.isNotEmpty() && correo.isNotEmpty() && rol.isNotEmpty()) {
                insertarNuevoUsuario(nombre, contrasena, correo, rol)
            } else {
                mostrarMensaje("Por favor completa todos los campos")
            }
        }

        buttonRegresar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun insertarNuevoUsuario(nombre: String, contrasena: String, correo: String, rol: String) {
        val call = RetrofitClient.apiService.agregarUsuario(nombre, contrasena, correo, rol)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    mostrarMensaje("Usuario registrado exitosamente")
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else if (response.code() == 409) {
                    mostrarMensaje("El correo ya está en uso")
                } else {
                    val errorBody = response.errorBody()?.string()
                    mostrarMensaje("Error al registrar usuario: ${response.code()} - $errorBody")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                mostrarMensaje("Error al conectar con el servidor: ${t.message}")
            }
        })
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}