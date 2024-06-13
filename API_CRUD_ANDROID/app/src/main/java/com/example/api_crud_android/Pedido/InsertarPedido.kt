package com.example.api_crud_android.Pedido

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

class InsertarPedido : AppCompatActivity() {

    private lateinit var editTextFecha: EditText
    private lateinit var editTextDireccion: EditText
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    private val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insertarpedido)

        editTextFecha = findViewById(R.id.editTextFecha)
        editTextDireccion = findViewById(R.id.editTextDireccion)
        buttonInsertar = findViewById(R.id.buttonInsertar)
        buttonRegresar = findViewById(R.id.buttonRegresar)

        buttonInsertar.setOnClickListener {
            val fecha = editTextFecha.text.toString()
            val direccion = editTextDireccion.text.toString()

            if (fecha.isNotEmpty() && direccion.isNotEmpty()) {
                insertarNuevoPedido(fecha, direccion)
            } else {
                mostrarMensaje("Por favor completa todos los campos")
            }
        }

        buttonRegresar.setOnClickListener {
            finish()
        }
    }

    private fun insertarNuevoPedido(fecha: String, direccion: String) {
        buttonInsertar.isEnabled = false

        apiService.agregarPedido(fecha, direccion).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    mostrarMensaje("Pedido agregado exitosamente")
                } else {
                    val errorBody = response.errorBody()?.string()
                    mostrarMensaje("Error al agregar pedido: ${response.code()} - ${errorBody}")
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