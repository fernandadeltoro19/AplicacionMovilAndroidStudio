package com.example.api_crud_android.network

import com.example.api_crud_android.Categoria.Categoria
import com.example.api_crud_android.Cliente.Cliente
import com.example.api_crud_android.DetallePedido.DetallePedido
import com.example.api_crud_android.Pedido.Pedido
import com.example.api_crud_android.Producto.Producto
import com.example.api_crud_android.Usuario.Usuario
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServicee {

    @GET("/api/Categoria")
    fun obtenerCategoria(): Call<List<Categoria>>

    @GET("/api/Cliente")
    fun obtenerClientes(): Call<List<Cliente>>

    @GET("api/Usuario/VerificarExistencia")
    fun verificarExistenciaUsuario(
        @Query("correo") correo: String,
        @Query("contrasena") contrasena: String
    ): Call<ResponseBody>

    @POST("/api/Cliente")
    fun agregarCliente(
        @Query("nombre") nombre: String,
        @Query("apellido") apellido: String,
        @Query("direccion") direccion: String,
        @Query("telefono") telefono: String,
        @Query("correo") correo: String,
        @Query("estatus") estatus: Int = 1
    ): Call<Void>

    @DELETE("/api/Cliente/{id}")
    fun eliminarCliente(@Path("id") idCliente: Int): Call<Void>

    @PUT("/api/Cliente/{id}")
    fun actualizarCliente(
        @Path("id") idCliente: Int,
        @Query("nombre") nombre: String,
        @Query("apellido") apellido: String,
        @Query("direccion") direccion: String,
        @Query("telefono") telefono: String,
        @Query("correo") correo: String,
        @Query("estatus") estatus: Int = 1
    ): Call<Void>

    @GET("api/Producto")
    fun obtenerProductos(): Call<List<Producto>>

    @POST("/api/Producto")
    fun agregarProducto(
        @Query("nombre") nombre: String,
        @Query("descripcion") descripcion: String,
        @Query("precio") precio: Double,
        @Query("stock") stock: Int,
        @Query("estatus") estatus: Int = 1
    ): Call<Void>

    @DELETE("/api/Producto/{id}")
    fun eliminarProducto(@Path("id") idProducto: Int): Call<Void>

    @PUT("/api/Producto/{id}")
    fun editarProducto(
        @Path("id") idProducto: Int,
        @Query("nombre") nombre: String,
        @Query("descripcion") descripcion: String,
        @Query("precio") precio: Double,
        @Query("stock") stock: Int,
        @Query("estatus") estatus: Int = 1
    ): Call<Void>

    @GET("api/Pedido")
    fun obtenerPedidos(): Call<List<Pedido>>

    @POST("/api/Pedido")
    fun agregarPedido(
        @Query("fecha") fecha: String,
        @Query("direccion") direccion: String,
        @Query("estatus") estatus: Int = 1
    ): Call<Void>

    @DELETE("/api/Pedido/{id}")
    fun eliminarPedido(@Path("id") idPedido: Int): Call<Void>

    @PUT("/api/Pedido/{id}")
    fun actualizarPedido(
        @Path("id") idPedido: Int,
        @Query("fecha") fecha: String,
        @Query("direccion") direccion: String,
        @Query("estatus") estatus: Int = 1
    ): Call<Void>

    @GET("api/DetallePedido")
    fun obtenerDetallePedidos(): Call<List<DetallePedido>>

    @POST("/api/DetallePedido")
    fun agregarDetallePedido(
        @Query("cantidad") cantidad: Int,
        @Query("precioUnitario") precioUnitario: Double,
        @Query("estatus") estatus: Int = 1
    ): Call<Void>

    @DELETE("/api/DetallePedido/{id}")
    fun eliminarDetallePedido(@Path("id") idDetallePedido: Int): Call<Void>

    @PUT("/api/DetallePedido/{id}")
    fun actualizarDetallePedido(
        @Path("id") idDetallePedido: Int,
        @Query("cantidad") cantidad: Int,
        @Query("precioUnitario") precioUnitario: Double,
        @Query("estatus") estatus: Int = 1
    ): Call<Void>

    @GET("api/Usuario")
    fun obtenerUsuarios(): Call<List<Usuario>>

    @POST("/api/Usuario")
    fun agregarUsuario(
        @Query("nombreUsuario") nombreUsuario: String,
        @Query("contrasena") contrasena: String,
        @Query("correo") correo: String,
        @Query("rol") rol: String,
        @Query("estatus") estatus: Int = 1
    ): Call<Void>

    @DELETE("/api/Usuario/{id}")
    fun eliminarUsuario(@Path("id") idUsuario: Int): Call<Void>

    @PUT("/api/Usuario/{id}")
    fun actualizarUsuario(
        @Path("id") idUsuario: Int,
        @Query("nombreUsuario") nombreUsuario: String,
        @Query("contrasena") contrasena: String,
        @Query("correo") correo: String,
        @Query("rol") rol: String,
        @Query("estatus") estatus: Int = 1
    ): Call<Void>

    interface ApiService {
        @DELETE("/api/Producto/{id}")
        fun eliminarProducto(@Path("id") idProducto: Int): Call<Void>
    }


    @POST("/api/Categoria")
    fun agregarCategoria(
        @Query("nombre") nombre: String,
        @Query("estatus") estatus: Int = 1 // Valor por defecto
    ): Call<Void>

    @DELETE("/api/Categoria/{id}")
    fun eliminarCategoria(@Path("id") idCategoria: Int): Call<Void>

    @PUT("/api/Categoria/{id}")
    fun actualizarCategoria(
        @Path("id") idCategoria: Int,
        @Query("nombre") nombre: String,
        @Query("estatus") estatus: Int = 1
    ): Call<Void>

    @PUT("/api/Producto/{id}")
    fun actualizarProducto(
        @Path("id") idProducto: Int,
        @Query("nombre") nombre: String,
        @Query("descripcion") descripcion: String,
        @Query("precio") precio: Double,
        @Query("stock") stock: Int,
        @Query("estatus") estatus: Int = 1
    ): Call<Void>

}


