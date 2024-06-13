package com.example.api_crud_android.Categoria

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

class EditarCategoriaActivity : AppCompatActivity() {
    private lateinit var editTextNombre: EditText
    private lateinit var buttonGuardar: Button
    private lateinit var buttonRegresar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarcategoria) // Cambiado a editar_categoria.xml

        editTextNombre = findViewById(R.id.editTextNombre)
        buttonGuardar = findViewById(R.id.buttonActualizar)
        buttonRegresar = findViewById(R.id.buttonRegresar)


        // Obtener datos pasados desde la actividad anterior
        val idCategoria = intent.getIntExtra("ID_CATEGORIA", -1)
        val nombre = intent.getStringExtra("NOMBRE")

        // Mostrar los datos de la categoría en los campos de edición
        editTextNombre.setText(nombre)

        buttonGuardar.setOnClickListener {
            guardarCambios(idCategoria)
        }
        buttonRegresar.setOnClickListener {
            finish()  // Esta línea cerrará la actividad actual y regresará a la anterior
        }
    }

    private fun guardarCambios(idCategoria: Int) {
        val nombre = editTextNombre.text.toString()

        val call = RetrofitClient.apiService.actualizarCategoria(idCategoria, nombre)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditarCategoriaActivity, "Datos actualizados", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditarCategoriaActivity, "Error al actualizar los datos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EditarCategoriaActivity, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
