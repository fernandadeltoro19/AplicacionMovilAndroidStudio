package com.example.api_crud_android.Cliente

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

class InsertarCliente : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextApellido: EditText
    private lateinit var editTextDireccion: EditText
    private lateinit var editTextTelefono: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    private val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insertarcliente)

        editTextNombre = findViewById(R.id.editTextNombre)
        editTextApellido = findViewById(R.id.editTextApellido)
        editTextDireccion = findViewById(R.id.editTextDireccion)
        editTextTelefono = findViewById(R.id.editTextTelefono)
        editTextCorreo = findViewById(R.id.editTextCorreo)
        buttonInsertar = findViewById(R.id.buttonInsertar)
        buttonRegresar = findViewById(R.id.buttonRegresar)

        buttonInsertar.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val apellido = editTextApellido.text.toString()
            val direccion = editTextDireccion.text.toString()
            val telefono = editTextTelefono.text.toString()
            val correo = editTextCorreo.text.toString()

            if (nombre.isNotEmpty() && apellido.isNotEmpty() && direccion.isNotEmpty() && telefono.isNotEmpty() && correo.isNotEmpty()) {
                insertarNuevoCliente(nombre, apellido, direccion, telefono, correo)
            } else {
                mostrarMensaje("Por favor completa todos los campos")
            }
        }

        buttonRegresar.setOnClickListener {
            finish()
        }
    }

    private fun insertarNuevoCliente(nombre: String, apellido: String, direccion: String, telefono: String, correo: String) {
        buttonInsertar.isEnabled = false

        apiService.agregarCliente(nombre, apellido, direccion, telefono, correo)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        mostrarMensaje("Cliente agregado exitosamente")
                    } else {
                        val errorBody = response.errorBody()?.string()
                        mostrarMensaje("Error al agregar cliente: ${response.code()} - ${errorBody}")
                        buttonInsertar.isEnabled = true

                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    mostrarMensaje("Error al conectar con el servidor: ${t.message}")
                    buttonInsertar.isEnabled = true

                }
            })
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}