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

class EditarPedidoActivity : AppCompatActivity() {
    private lateinit var editTextFecha: EditText
    private lateinit var editTextDireccion: EditText
    private lateinit var buttonGuardar: Button
    private lateinit var buttonRegresar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarpedido) // Asegúrate de que el layout se llame editar_pedido.xml

        editTextFecha = findViewById(R.id.editTextFecha)
        editTextDireccion = findViewById(R.id.editTextDireccion)
        buttonGuardar = findViewById(R.id.buttonActualizar)
        buttonRegresar = findViewById(R.id.buttonRegresar)


        // Obtener datos pasados desde la actividad anterior
        val idPedido = intent.getIntExtra("ID_PEDIDO", -1)
        val fecha = intent.getStringExtra("FECHA")
        val direccion = intent.getStringExtra("DIRECCION")

        // Mostrar los datos del pedido en los campos de edición
        editTextFecha.setText(fecha)
        editTextDireccion.setText(direccion)

        buttonGuardar.setOnClickListener {
            guardarCambios(idPedido)
        }

        buttonRegresar.setOnClickListener {
            finish()  // Esta línea cerrará la actividad actual y regresará a la anterior
        }
    }

    private fun guardarCambios(idPedido: Int) {
        val fecha = editTextFecha.text.toString()
        val direccion = editTextDireccion.text.toString()

        val call = RetrofitClient.apiService.actualizarPedido(idPedido, fecha, direccion, 1) // Establecer el estatus como 1 por defecto
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditarPedidoActivity, "Datos actualizados", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditarPedidoActivity, "Error al actualizar los datos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EditarPedidoActivity, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
