package com.example.api_crud_android.Usuario

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

class EditarUsuarioActivity : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextContrasena: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextRol: EditText
    private lateinit var buttonGuardar: Button
    private lateinit var buttonRegresar: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editarusuario)

        editTextNombre = findViewById(R.id.editTextNombreUsuario)
        editTextContrasena = findViewById(R.id.editTextPrecioContrasena)
        editTextCorreo = findViewById(R.id.editTextCorreo)
        editTextRol = findViewById(R.id.editTextRol)
        buttonGuardar = findViewById(R.id.buttonActualizar)
        buttonRegresar = findViewById(R.id.buttonRegresar)


        // Obtener datos pasados desde la actividad anterior
        val idUsuario = intent.getIntExtra("ID_USUARIO", -1)
        val nombreUsuario = intent.getStringExtra("NOMBRE_USUARIO")
        val contrasena = intent.getStringExtra("CONTRASENA")
        val correo = intent.getStringExtra("CORREO")
        val rol = intent.getStringExtra("ROL")

        // Mostrar los datos del usuario en los campos de edición
        editTextNombre.setText(nombreUsuario)
        editTextContrasena.setText(contrasena)
        editTextCorreo.setText(correo)
        editTextRol.setText(rol)

        buttonGuardar.setOnClickListener {
            guardarCambios(idUsuario)
        }
        buttonRegresar.setOnClickListener {
            finish()  // Esta línea cerrará la actividad actual y regresará a la anterior
        }
    }

    private fun guardarCambios(idUsuario: Int) {
        val nombreUsuario = editTextNombre.text.toString()
        val contrasena = editTextContrasena.text.toString()
        val correo = editTextCorreo.text.toString()
        val rol = editTextRol.text.toString()

        val call = RetrofitClient.apiService.actualizarUsuario(idUsuario, nombreUsuario, contrasena, correo, rol)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditarUsuarioActivity, "Datos actualizados", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditarUsuarioActivity, "Error al actualizar los datos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EditarUsuarioActivity, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }
}