package com.example.api_crud_android.DetallePedido

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

class InsertarDetallePedido : AppCompatActivity() {

    private lateinit var editTextCantidad: EditText
    private lateinit var editTextPrecioUnitario: EditText
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    private val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insertardetallepedido)

        editTextCantidad = findViewById(R.id.editTextCantidad)
        editTextPrecioUnitario = findViewById(R.id.editTextPrecioUnitario)
        buttonInsertar = findViewById(R.id.buttonInsertar)
        buttonRegresar = findViewById(R.id.buttonRegresar)

        buttonInsertar.setOnClickListener {
            val cantidad = editTextCantidad.text.toString().toIntOrNull()
            val precioUnitario = editTextPrecioUnitario.text.toString().toDoubleOrNull()

            if (cantidad != null && precioUnitario != null) {
                insertarNuevoDetallePedido(cantidad, precioUnitario)
            } else {
                mostrarMensaje("Por favor ingrese todos los datos correctamente")
            }
        }

        buttonRegresar.setOnClickListener {
            finish()
        }
    }

    private fun insertarNuevoDetallePedido(cantidad: Int, precioUnitario: Double) {
        buttonInsertar.isEnabled = false

        apiService.agregarDetallePedido(cantidad, precioUnitario).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    mostrarMensaje("Detalle pedido agregado exitosamente")
                } else {
                    val errorBody = response.errorBody()?.string()
                    mostrarMensaje("Error al agregar detalle pedido: ${response.code()} - ${errorBody}")
                    buttonInsertar.isEnabled = true

                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                mostrarMensaje("Error al agregar detalle pedido: ${t.message}")
                buttonInsertar.isEnabled = true

            }
        })
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}