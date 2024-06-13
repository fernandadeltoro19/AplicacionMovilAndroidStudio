package com.example.api_crud_android.Cliente

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

class EditarClienteActivity : AppCompatActivity() {
    private lateinit var editTextNombre: EditText
    private lateinit var editTextApellido: EditText
    private lateinit var editTextDireccion: EditText
    private lateinit var editTextTelefono: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var buttonGuardar: Button
    private lateinit var buttonRegresar: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarcliente) // Cambiado a editar_cliente.xml

        editTextNombre = findViewById(R.id.editTextNombre)
        editTextApellido = findViewById(R.id.editTextApellido)
        editTextDireccion = findViewById(R.id.editTextDireccion)
        editTextTelefono = findViewById(R.id.editTextTelefono)
        editTextCorreo = findViewById(R.id.editTextCorreo)
        buttonGuardar = findViewById(R.id.buttonActualizar)
        buttonRegresar = findViewById(R.id.buttonRegresar)


        // Obtener datos pasados desde la actividad anterior
        val idCliente = intent.getIntExtra("ID_CLIENTE", -1)
        val nombre = intent.getStringExtra("NOMBRE")
        val apellido = intent.getStringExtra("APELLIDO")
        val direccion = intent.getStringExtra("DIRECCION")
        val telefono = intent.getStringExtra("TELEFONO")
        val correo = intent.getStringExtra("CORREO")

        // Mostrar los datos del cliente en los campos de edición
        editTextNombre.setText(nombre)
        editTextApellido.setText(apellido)
        editTextDireccion.setText(direccion)
        editTextTelefono.setText(telefono)
        editTextCorreo.setText(correo)

        buttonGuardar.setOnClickListener {
            guardarCambios(idCliente)
        }

        buttonRegresar.setOnClickListener {
            finish()  // Esta línea cerrará la actividad actual y regresará a la anterior
        }
    }

    private fun guardarCambios(idCliente: Int) {
        val nombre = editTextNombre.text.toString()
        val apellido = editTextApellido.text.toString()
        val direccion = editTextDireccion.text.toString()
        val telefono = editTextTelefono.text.toString()
        val correo = editTextCorreo.text.toString()

        val call = RetrofitClient.apiService.actualizarCliente(idCliente, nombre, apellido, direccion, telefono, correo)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditarClienteActivity, "Datos actualizados", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditarClienteActivity, "Error al actualizar los datos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EditarClienteActivity, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }
}