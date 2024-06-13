package com.example.api_crud_android.Producto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.api_crud_android.InsertarProducto
import com.example.api_crud_android.R
import com.example.api_crud_android.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoActivity : AppCompatActivity() {

    private lateinit var listViewProductos: ListView
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarproducto)

        listViewProductos = findViewById(R.id.listViewProducto)
        buttonInsertar = findViewById(R.id.buttonInsertarProducto)
        buttonRegresar = findViewById(R.id.btnRegresarMenu)

        buttonInsertar.setOnClickListener {
            val intent = Intent(this, InsertarProducto::class.java)
            startActivity(intent)
        }

        buttonRegresar.setOnClickListener {
            finish()
        }

        obtenerProductosDesdeAPI()
    }

    override fun onResume() {
        super.onResume()
        obtenerProductosDesdeAPI()
    }

    private fun obtenerProductosDesdeAPI() {
        val apiService = RetrofitClient.apiService
        apiService.obtenerProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    val productos = response.body() ?: emptyList()
                    val productosConStatusUno = productos.filter { it.estatus == 1 }

                    val adapter = ProductoAdapter(this@ProductoActivity, productosConStatusUno.toMutableList()) { producto ->
                        confirmarEliminarProducto(producto)
                    }

                    listViewProductos.adapter = adapter
                    Log.d("API_SUCCESS", "Productos obtenidos con éxito")
                } else {
                    Log.e("API_ERROR", "Error en la respuesta: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.e("API_FAILURE", "Error de conexión: ${t.message}")
            }
        })
    }

    private fun confirmarEliminarProducto(producto: Producto) {
        AlertDialog.Builder(this).apply {
            setTitle("Confirmar eliminación")
            setMessage("¿Está seguro de que desea eliminar el producto ${producto.nombre}?")
            setPositiveButton("Sí") { _, _ ->
                eliminarProductoEnAPI(producto)
            }
            setNegativeButton("No", null)
        }.show()
    }

    private fun eliminarProductoEnAPI(producto: Producto) {
        val apiService = RetrofitClient.apiService
        apiService.eliminarProducto(producto.idProducto).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ProductoActivity, "Producto eliminado: ${producto.nombre}", Toast.LENGTH_SHORT).show()
                    obtenerProductosDesdeAPI()
                } else {
                    Log.e("API_ERROR", "Error en la respuesta: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("API_FAILURE", "Error de conexión: ${t.message}")
            }
        })
    }
}
