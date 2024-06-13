package com.example.api_crud_android
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.api_crud_android.Categoria.CategoriaActivity
import com.example.api_crud_android.DetallePedido.DetallePedidoActivity
import com.example.api_crud_android.Pedido.PedidoActivity
import com.example.api_crud_android.Producto.ProductoActivity
import com.example.api_crud_android.network.LoginActivity


class Menu : ComponentActivity() {
    private lateinit var btnCategoria: Button
    private lateinit var btnCliente: Button
    private lateinit var btnDetallePedido: Button
    private lateinit var btnProducto: Button
    private lateinit var btnPedido: Button
    private lateinit var btnUsuarioo: Button
    private lateinit var btnCerrarSesion: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layoutmenu)

        btnCategoria = findViewById(R.id.btnCategoria)
        btnCliente = findViewById(R.id.btnCliente)
        btnDetallePedido = findViewById(R.id.btnDetallePedido)
        btnPedido = findViewById(R.id.btnPedido)
        btnProducto = findViewById(R.id.btnProducto)
        btnUsuarioo = findViewById(R.id.btnUsuarioo)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)



        btnCategoria.setOnClickListener {
            val intent = Intent(this@Menu, CategoriaActivity::class.java)
            startActivity(intent)
        }

        btnCliente.setOnClickListener {
            val intent = Intent(this@Menu, ClienteActivity::class.java)
            startActivity(intent)
        }
        btnDetallePedido.setOnClickListener {
            val intent = Intent(this@Menu, DetallePedidoActivity::class.java)
            startActivity(intent)
        }
        btnPedido.setOnClickListener {
            val intent = Intent(this@Menu, PedidoActivity::class.java)
            startActivity(intent)
        }
        btnProducto.setOnClickListener {
            val intent = Intent(this@Menu, ProductoActivity::class.java)
            startActivity(intent)
        }

        btnUsuarioo.setOnClickListener {
            val intent = Intent(this@Menu, UsuarioActivity::class.java)
            startActivity(intent)
        }

        btnCerrarSesion.setOnClickListener {
            // Crear un intent para iniciar la actividad del inicio de sesión
            val intent = Intent(this, LoginActivity::class.java)
            // Establecer la bandera FLAG_ACTIVITY_CLEAR_TOP para limpiar la pila de actividades y evitar que se abra una nueva instancia de LoginActivity
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            // Finalizar la actividad actual
            finish()
        }



    }
}
