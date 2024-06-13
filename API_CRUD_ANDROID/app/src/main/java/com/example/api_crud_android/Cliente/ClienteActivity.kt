package com.example.api_crud_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.api_crud_android.Cliente.Cliente
import com.example.api_crud_android.Cliente.ClienteAdapter
import com.example.api_crud_android.Cliente.InsertarCliente
import com.example.api_crud_android.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ClienteActivity : AppCompatActivity() {

    private lateinit var listViewClientes: ListView
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarcliente)

        listViewClientes = findViewById(R.id.listViewClientes)
        buttonInsertar = findViewById(R.id.buttonInsertarCliente)
        buttonRegresar = findViewById(R.id.btnRegresarMenu)

        buttonInsertar.setOnClickListener {
            // Open activity to insert a new cliente
            val intent = Intent(this, InsertarCliente::class.java)
            startActivity(intent)
        }

        buttonRegresar.setOnClickListener {
            // Finish current activity and go back to the previous one
            finish()
        }

        // Initialize loading of clientes
        obtenerClientesDesdeAPI()
    }

    override fun onResume() {
        super.onResume()
        // Reload clientes when activity is back in foreground
        obtenerClientesDesdeAPI()
    }

    private fun obtenerClientesDesdeAPI() {
        val apiService = RetrofitClient.apiService
        apiService.obtenerClientes().enqueue(object : Callback<List<Cliente>> {
            override fun onResponse(call: Call<List<Cliente>>, response: Response<List<Cliente>>) {
                if (response.isSuccessful) {
                    val clientes = response.body() ?: emptyList()
                    val clientesConStatusUno = clientes.filter { it.estatus == 1 }

                    val adapter = ClienteAdapter(this@ClienteActivity, clientesConStatusUno.toMutableList()) { cliente ->
                        confirmarEliminarCliente(cliente)
                    }

                    listViewClientes.adapter = adapter
                    Log.d("API_SUCCESS", "Clientes obtenidos con éxito")

                } else {
                    Log.e("API_ERROR", "Error en la respuesta: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Cliente>>, t: Throwable) {
                Log.e("API_FAILURE", "Error de conexión: ${t.message}")
            }
        })
    }

    private fun confirmarEliminarCliente(cliente: Cliente) {
        AlertDialog.Builder(this).apply {
            setTitle("Confirmar eliminación")
            setMessage("¿Está seguro de que desea eliminar el cliente ${cliente.nombre} ${cliente.apellido}?")
            setPositiveButton("Sí") { _, _ ->
                eliminarClienteEnAPI(cliente)
            }
            setNegativeButton("No", null)
        }.show()
    }

    private fun eliminarClienteEnAPI(cliente: Cliente) {
        val apiService = RetrofitClient.apiService
        apiService.eliminarCliente(cliente.idCliente).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ClienteActivity, "Cliente eliminado: ${cliente.nombre} ${cliente.apellido}", Toast.LENGTH_SHORT).show()
                    obtenerClientesDesdeAPI() // Reload clientes after deletion
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
