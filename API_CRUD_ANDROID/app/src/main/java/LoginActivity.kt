package com.example.api_crud_android.network
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.api_crud_android.Menu
import com.example.api_crud_android.R
import com.example.api_crud_android.Usuario.RegisterActivity
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class LoginActivity : ComponentActivity() {
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextContrasena: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var apiService: ApiServicee
    private lateinit var btnRegister: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.api_crud_android)

        editTextCorreo = findViewById(R.id.etEmail)
        editTextContrasena = findViewById(R.id.etPassword)
        btnIniciarSesion = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://10.0.2.2:7019/")
            .client(unsafeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiServicee::class.java)

        btnIniciarSesion.setOnClickListener {
            val correoElectronico = editTextCorreo.text.toString()
            val password = editTextContrasena.text.toString()

            if (correoElectronico.isNotEmpty() && password.isNotEmpty()) {
                verificarExistenciaUsuario(correoElectronico, password)
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            }
        }
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun verificarExistenciaUsuario(correoElectronico: String, password: String) {
        val call = apiService.verificarExistenciaUsuario(correoElectronico, password)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val intent = Intent(this@LoginActivity, Menu::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.e("LoginActivity", "Error en la respuesta: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@LoginActivity, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("LoginActivity", "Error al conectar con el servidor", t)
                Toast.makeText(this@LoginActivity, "Error al conectar con el servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun unsafeOkHttpClient(): OkHttpClient {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}

                override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            val hostnameVerifier = HostnameVerifier { _, _ -> true }

            return OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier(hostnameVerifier)
                .build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}