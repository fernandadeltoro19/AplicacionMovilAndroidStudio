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

class InsertarCategoria : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    private val apiService = RetrofitClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.insertarcategoria)

        editTextNombre = findViewById(R.id.editTextNombre)
        buttonInsertar = findViewById(R.id.buttonInsertar)
        buttonRegresar = findViewById(R.id.buttonRegresar)

        buttonInsertar.setOnClickListener {
            val nombre = editTextNombre.text.toString()

            if (nombre.isNotEmpty()) {
                insertarNuevaCategoria(nombre)
            } else {
                mostrarMensaje("Por favor ingresa el nombre de la categoría")
            }
        }

        buttonRegresar.setOnClickListener {
            finish()
        }
    }

    private fun insertarNuevaCategoria(nombre: String) {
        buttonInsertar.isEnabled = false
        apiService.agregarCategoria(nombre)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        mostrarMensaje("Categoría agregada exitosamente")
                    } else {
                        val errorBody = response.errorBody()?.string()
                        mostrarMensaje("Error al agregar categoría: ${response.code()} - ${errorBody}")
                        buttonInsertar.isEnabled = true
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    mostrarMensaje("Error al agregar categoría: ${t.message}")
                    buttonInsertar.isEnabled = true
                }
            })
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
