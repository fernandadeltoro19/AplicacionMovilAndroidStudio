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

class EditarDetallePedidoActivity : AppCompatActivity() {
    private lateinit var editTextCantidad: EditText
    private lateinit var editTextPrecioUnitario: EditText
    private lateinit var buttonGuardar: Button
    private lateinit var buttonRegresar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editardetallepedido) // Asegúrate de que este es el layout correcto

        editTextCantidad = findViewById(R.id.editTextCantidad)
        editTextPrecioUnitario = findViewById(R.id.editTextPrecioUnitario)
        buttonGuardar = findViewById(R.id.buttonActualizarr)
        buttonRegresar = findViewById(R.id.buttonRegresar)


        // Obtener datos pasados desde la actividad anterior
        val idDetallePedido = intent.getIntExtra("ID_DETALLE_PEDIDO", -1)
        val cantidad = intent.getIntExtra("CANTIDAD", 0)
        val precioUnitario = intent.getDoubleExtra("PRECIO_UNITARIO", 0.0)

        // Mostrar los datos del detalle pedido en los campos de edición
        editTextCantidad.setText(cantidad.toString())
        editTextPrecioUnitario.setText(precioUnitario.toString())

        buttonGuardar.setOnClickListener {
            guardarCambios(idDetallePedido)
        }
        buttonRegresar.setOnClickListener {
            finish()  // Esta línea cerrará la actividad actual y regresará a la anterior
        }
    }

    private fun guardarCambios(idDetallePedido: Int) {
        val cantidad = editTextCantidad.text.toString().toIntOrNull()
        val precioUnitario = editTextPrecioUnitario.text.toString().toDoubleOrNull()

        if (cantidad == null || precioUnitario == null) {
            Toast.makeText(this, "Por favor ingrese todos los datos correctamente", Toast.LENGTH_SHORT).show()
            return
        }

        val call = RetrofitClient.apiService.actualizarDetallePedido(idDetallePedido, cantidad, precioUnitario)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditarDetallePedidoActivity, "Datos actualizados", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditarDetallePedidoActivity, "Error al actualizar los datos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EditarDetallePedidoActivity, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }
}