package com.example.api_crud_android.DetallePedido
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

class DetallePedidoActivity : AppCompatActivity() {

    private lateinit var listViewDetallePedidos: ListView
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrardetallepedido)

        listViewDetallePedidos = findViewById(R.id.listViewDetallePedido)
        buttonInsertar = findViewById(R.id.buttonInsertarDetallePedido)
        buttonRegresar = findViewById(R.id.btnRegresarMenu)

        buttonInsertar.setOnClickListener {
            // Open activity to insert a new detalle pedido
            val intent = Intent(this, InsertarDetallePedido::class.java)
            startActivity(intent)
        }

        buttonRegresar.setOnClickListener {
            // Finish current activity and go back to the previous one
            finish()
        }

        // Initialize loading of detalle pedidos
        obtenerDetallePedidosDesdeAPI()
    }

    override fun onResume() {
        super.onResume()
        // Reload detalle pedidos when activity is back in foreground
        obtenerDetallePedidosDesdeAPI()
    }

    private fun obtenerDetallePedidosDesdeAPI() {
        val apiService = RetrofitClient.apiService
        apiService.obtenerDetallePedidos().enqueue(object : Callback<List<DetallePedido>> {
            override fun onResponse(call: Call<List<DetallePedido>>, response: Response<List<DetallePedido>>) {
                if (response.isSuccessful) {
                    val detallePedidos = response.body() ?: emptyList()
                    val detallePedidosConStatusUno = detallePedidos.filter { it.estatus == 1 }

                    val adapter = DetallePedidoAdapter(this@DetallePedidoActivity, detallePedidosConStatusUno.toMutableList()) { detallePedido ->
                        confirmarEliminarDetallePedido(detallePedido)
                    }

                    listViewDetallePedidos.adapter = adapter
                    Log.d("API_SUCCESS", "Detalle pedidos obtenidos con éxito")

                } else {
                    Log.e("API_ERROR", "Error en la respuesta: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<DetallePedido>>, t: Throwable) {
                Log.e("API_FAILURE", "Error de conexión: ${t.message}")
            }
        })
    }

    private fun confirmarEliminarDetallePedido(detallePedido: DetallePedido) {
        AlertDialog.Builder(this).apply {
            setTitle("Confirmar eliminación")
            setMessage("¿Está seguro de que desea eliminar el detalle pedido con cantidad ${detallePedido.cantidad} y precio unitario ${detallePedido.precioUnitario}?")
            setPositiveButton("Sí") { _, _ ->
                eliminarDetallePedidoEnAPI(detallePedido)
            }
            setNegativeButton("No", null)
        }.show()
    }

    private fun eliminarDetallePedidoEnAPI(detallePedido: DetallePedido) {
        val apiService = RetrofitClient.apiService
        apiService.eliminarDetallePedido(detallePedido.idDetallePedido).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@DetallePedidoActivity, "Detalle pedido eliminado: ${detallePedido.cantidad} ${detallePedido.precioUnitario}", Toast.LENGTH_SHORT).show()
                    obtenerDetallePedidosDesdeAPI() // Reload detalle pedidos after deletion
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