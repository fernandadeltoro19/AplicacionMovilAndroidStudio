package com.example.api_crud_android


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.api_crud_android.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertarProducto : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextDescripcion: EditText
    private lateinit var editTextPrecio: EditText
    private lateinit var editTextStock: EditText
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    private val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insertarproducto)

        editTextNombre = findViewById(R.id.editTextNombreProducto)
        editTextDescripcion = findViewById(R.id.editTextDescripcion)
        editTextPrecio = findViewById(R.id.editTextPrecioProducto)
        editTextStock = findViewById(R.id.editTextStock)
        buttonInsertar = findViewById(R.id.buttonInsertar)
        buttonRegresar = findViewById(R.id.buttonRegresar)

        buttonInsertar.setOnClickListener {
            val nombre = editTextNombre.text.toString()
            val descripcion = editTextDescripcion.text.toString()
            val precio = editTextPrecio.text.toString().toDoubleOrNull()
            val stock = editTextStock.text.toString().toIntOrNull()

            if (nombre.isNotEmpty() && descripcion.isNotEmpty() && precio != null && stock != null) {
                insertarNuevoProducto(nombre, descripcion, precio, stock)
            } else {
                mostrarMensaje("Por favor completa todos los campos correctamente")
            }
        }

        buttonRegresar.setOnClickListener {
            finish()
        }
    }

    private fun insertarNuevoProducto(nombre: String, descripcion: String, precio: Double, stock: Int) {
        buttonInsertar.isEnabled = false

        apiService.agregarProducto(nombre, descripcion, precio, stock)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        mostrarMensaje("Producto agregado exitosamente")
                    } else {
                        val errorBody = response.errorBody()?.string()
                        mostrarMensaje("Error al agregar producto: ${response.code()} - ${errorBody}")
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