package com.example.api_crud_android.Producto

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

class EditarProductoActivity : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextDescripcion: EditText
    private lateinit var editTextPrecio: EditText
    private lateinit var editTextStock: EditText
    private lateinit var buttonGuardar: Button
    private lateinit var buttonRegresar: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarproducto) // Cambiado a editar_producto.xml

        editTextNombre = findViewById(R.id.editTextNombreProducto)
        editTextDescripcion = findViewById(R.id.editTextDescripcion)
        editTextPrecio = findViewById(R.id.editTextPrecioProducto)
        editTextStock = findViewById(R.id.editTextStock)
        buttonGuardar = findViewById(R.id.buttonActualizar)
        buttonRegresar = findViewById(R.id.buttonRegresar)


        // Obtener datos pasados desde la actividad anterior
        val idProducto = intent.getIntExtra("ID_PRODUCTO", -1)
        val nombre = intent.getStringExtra("NOMBRE")
        val descripcion = intent.getStringExtra("DESCRIPCION")
        val precio = intent.getDoubleExtra("PRECIO", 0.0)
        val stock = intent.getIntExtra("STOCK", 0)

        // Mostrar los datos del producto en los campos de edición
        editTextNombre.setText(nombre)
        editTextDescripcion.setText(descripcion)
        editTextPrecio.setText(precio.toString())
        editTextStock.setText(stock.toString())

        buttonGuardar.setOnClickListener {
            guardarCambios(idProducto)
        }
        buttonRegresar.setOnClickListener {
            finish()  // Esta línea cerrará la actividad actual y regresará a la anterior
        }
    }

    private fun guardarCambios(idProducto: Int) {
        val nombre = editTextNombre.text.toString()
        val descripcion = editTextDescripcion.text.toString()
        val precio = editTextPrecio.text.toString().toDoubleOrNull()
        val stock = editTextStock.text.toString().toIntOrNull()

        if (nombre.isNotEmpty() && descripcion.isNotEmpty() && precio != null && stock != null) {
            val call = RetrofitClient.apiService.actualizarProducto(idProducto, nombre, descripcion, precio, stock)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@EditarProductoActivity, "Datos actualizados", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@EditarProductoActivity, "Error al actualizar los datos", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@EditarProductoActivity, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "Por favor completa todos los campos correctamente", Toast.LENGTH_SHORT).show()
        }
    }
}