package com.example.api_crud_android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.api_crud_android.Usuario.InsertarUsuario
import com.example.api_crud_android.Usuario.Usuario
import com.example.api_crud_android.network.RetrofitClient
import com.example.proyectoriojas.Usuario.UsuarioAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuarioActivity : AppCompatActivity() {

    private lateinit var listViewUsuario: ListView
    private lateinit var buttonInsertar: Button
    private lateinit var buttonRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mostrarusuarios)

        listViewUsuario = findViewById(R.id.listViewUsuario)
        buttonInsertar = findViewById(R.id.buttonInsertarUsuario)
        buttonRegresar = findViewById(R.id.btnRegresarMenu)

        buttonInsertar.setOnClickListener {
            val intent = Intent(this, InsertarUsuario::class.java)
            startActivity(intent)
        }

        buttonRegresar.setOnClickListener {
            finish()
        }

        obtenerUsuariosActivosDesdeAPI()
    }

    override fun onResume() {
        super.onResume()
        obtenerUsuariosActivosDesdeAPI()
    }

    private fun obtenerUsuariosActivosDesdeAPI() {
        val apiService = RetrofitClient.apiService
        apiService.obtenerUsuarios().enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (response.isSuccessful) {
                    val usuarios = response.body() ?: emptyList()
                    val usuariosActivos = usuarios.filter { it.estatus == 1 }

                    val adapter = UsuarioAdapter(this@UsuarioActivity, usuariosActivos.toMutableList()) { usuario ->
                        confirmarEliminarUsuario(usuario)
                    }

                    listViewUsuario.adapter = adapter
                } else {
                    Toast.makeText(this@UsuarioActivity, "Error al obtener usuarios: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                Toast.makeText(this@UsuarioActivity, "Error al conectar con el servidor: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun confirmarEliminarUsuario(usuario: Usuario) {
        AlertDialog.Builder(this).apply {
            setTitle("Confirmar eliminación")
            setMessage("¿Está seguro de que desea eliminar a ${usuario.correo}?")
            setPositiveButton("Sí") { _, _ ->
                eliminarUsuarioEnAPI(usuario)
            }
            setNegativeButton("No", null)
        }.show()
    }

    private fun eliminarUsuarioEnAPI(usuario: Usuario) {
        val apiService = RetrofitClient.apiService
        apiService.eliminarUsuario(usuario.idUsuario).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@UsuarioActivity, "Usuario eliminado: ${usuario.correo}", Toast.LENGTH_SHORT).show()
                    obtenerUsuariosActivosDesdeAPI()
                } else {
                    Toast.makeText(this@UsuarioActivity, "Error al eliminar usuario: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@UsuarioActivity, "Error al conectar con el servidor: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
