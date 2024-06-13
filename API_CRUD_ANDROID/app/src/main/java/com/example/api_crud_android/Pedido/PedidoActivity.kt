package com.example.api_crud_android.Pedido

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.api_crud_android.R
import com.example.api_crud_android.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PedidoActivity : AppCompatActivity() {

    private lateinit var listViewPedidos: ListView
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarpedido)

        listViewPedidos = findViewById(R.id.listViewPedido)
        buttonInsertar = findViewById(R.id.buttonInsertarPedido)
        buttonRegresar = findViewById(R.id.btnRegresarMenu)

        buttonInsertar.setOnClickListener {
            // Open activity to insert a new pedido
            val intent = Intent(this, InsertarPedido::class.java)
            startActivity(intent)
        }

        buttonRegresar.setOnClickListener {
            // Finish current activity and go back to the previous one
            finish()
        }

        // Initialize loading of pedidos
        obtenerPedidosDesdeAPI()
    }

    override fun onResume() {
        super.onResume()
        // Reload pedidos when activity is back in foreground
        obtenerPedidosDesdeAPI()
    }

    private fun obtenerPedidosDesdeAPI() {
        val apiService = RetrofitClient.apiService
        apiService.obtenerPedidos().enqueue(object : Callback<List<Pedido>> {
            override fun onResponse(call: Call<List<Pedido>>, response: Response<List<Pedido>>) {
                if (response.isSuccessful) {
                    val pedidos = response.body() ?: emptyList()
                    val pedidosConStatusUno = pedidos.filter { it.estatus == 1 }

                    val adapter = PedidoAdapter(this@PedidoActivity, pedidosConStatusUno.toMutableList()) { pedido ->
                        confirmarEliminarPedido(pedido)
                    }

                    listViewPedidos.adapter = adapter
                    Log.d("API_SUCCESS", "Pedidos obtenidos con éxito")

                } else {
                    Log.e("API_ERROR", "Error en la respuesta: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Pedido>>, t: Throwable) {
                Log.e("API_FAILURE", "Error de conexión: ${t.message}")
            }
        })
    }

    private fun confirmarEliminarPedido(pedido: Pedido) {
        AlertDialog.Builder(this).apply {
            setTitle("Confirmar eliminación")
            setMessage("¿Está seguro de que desea eliminar el pedido del ${pedido.fecha} con dirección ${pedido.direccion}?")
            setPositiveButton("Sí") { _, _ ->
                eliminarPedidoEnAPI(pedido)
            }
            setNegativeButton("No", null)
        }.show()
    }

    private fun eliminarPedidoEnAPI(pedido: Pedido) {
        val apiService = RetrofitClient.apiService
        apiService.eliminarPedido(pedido.idPedido).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@PedidoActivity, "Pedido eliminado: ${pedido.fecha} - ${pedido.direccion}", Toast.LENGTH_SHORT).show()
                    obtenerPedidosDesdeAPI() // Reload pedidos after deletion
                } else {
                    // Handle response errors
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Handle connection errors
            }
        })
    }
}