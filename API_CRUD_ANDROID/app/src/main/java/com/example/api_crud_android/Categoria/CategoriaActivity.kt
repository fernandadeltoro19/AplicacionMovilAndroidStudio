package com.example.api_crud_android.Categoria

import CategoriaAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import com.example.api_crud_android.InsertarCategoria
import com.example.api_crud_android.R
import com.example.api_crud_android.network.RetrofitClient
//import com.example.proyectoriojas.InsertarCategoria
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoriaActivity : ComponentActivity() {

    private lateinit var listViewCategorias: ListView
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarcategoria)

        listViewCategorias = findViewById(R.id.listViewCategorias)
        buttonInsertar = findViewById(R.id.button)
        buttonRegresar = findViewById(R.id.btnRegresar)

        buttonInsertar.setOnClickListener {
            // Abrir la actividad para insertar una nueva categoría
            val intent = Intent(this, InsertarCategoria::class.java)
            startActivity(intent)
        }

        buttonRegresar.setOnClickListener {
            // Finalizar la actividad actual y regresar a la anterior
            finish()
        }

        // Inicializar la carga de categorías
        obtenerCategoriasDesdeAPI()
    }

    override fun onResume() {
        super.onResume()
        // Volver a cargar las categorías cuando la actividad vuelva a estar en primer plano
        obtenerCategoriasDesdeAPI()
    }

    private fun obtenerCategoriasDesdeAPI() {
        val apiService = RetrofitClient.apiService
        apiService.obtenerCategoria().enqueue(object : Callback<List<Categoria>> {
            override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                if (response.isSuccessful) {
                    val categorias = response.body() ?: emptyList()
                    val categoriasConStatusUno = categorias.filter { it.estatus == 1 }

                    val adapter = CategoriaAdapter(this@CategoriaActivity, categoriasConStatusUno.toMutableList()) { categoria ->
                        confirmarEliminarCategoria(categoria)
                    }

                    listViewCategorias.adapter = adapter
                    Log.d("API_SUCCESS", "Categorías obtenidas con éxito")

                } else {
                    Log.e("API_ERROR", "Error en la respuesta: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
                Log.e("API_FAILURE", "Error de conexión: ${t.message}")
            }
        })
    }

    private fun confirmarEliminarCategoria(categoria: Categoria) {
        AlertDialog.Builder(this).apply {
            setTitle("Confirmar eliminación")
            setMessage("¿Está seguro de que desea eliminar la categoría ${categoria.nombre}?")
            setPositiveButton("Sí") { _, _ ->
                eliminarCategoriaEnAPI(categoria)
            }
            setNegativeButton("No", null)
        }.show()
    }

    private fun eliminarCategoriaEnAPI(categoria: Categoria) {
        val apiService = RetrofitClient.apiService
        apiService.eliminarCategoria(categoria.idCategoria).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CategoriaActivity, "Categoría eliminada: ${categoria.nombre}", Toast.LENGTH_SHORT).show()
                    obtenerCategoriasDesdeAPI() // Volver a cargar las categorías después de eliminar
                } else {
                    // Manejar errores de la respuesta
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Manejar errores de conexión
            }
        })
    }
}