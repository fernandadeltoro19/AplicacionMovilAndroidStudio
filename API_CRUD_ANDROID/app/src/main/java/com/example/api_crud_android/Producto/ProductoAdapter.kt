package com.example.api_crud_android.Producto

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.api_crud_android.R

class ProductoAdapter(
    context: Context,
    private val dataSource: MutableList<Producto>,
    private val eliminarProductoCallback: (Producto) -> Unit
) : ArrayAdapter<Producto>(context, R.layout.item_producto, dataSource) {

    private class ViewHolder {
        //lateinit var idProducto: TextView
        lateinit var nombre: TextView
        lateinit var descripcion: TextView
        lateinit var botonEliminar: ImageButton
        lateinit var botonEditar: ImageButton
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false)
            holder = ViewHolder()
            //holder.idProducto = view.findViewById(R.id.textViewIdProducto)
            holder.nombre = view.findViewById(R.id.textViewNombreProducto)
            holder.descripcion = view.findViewById(R.id.textViewDescripcion)
            holder.botonEliminar = view.findViewById(R.id.btnEliminar)
            holder.botonEditar = view.findViewById(R.id.botonEditarProducto)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val producto = dataSource[position]

        //holder.idProducto.text = producto.idProducto.toString()
        holder.nombre.text = producto.nombre
        holder.descripcion.text = producto.descripcion

        holder.botonEliminar.setOnClickListener {
            eliminarProductoCallback(producto)
        }

        holder.botonEditar.setOnClickListener {
            val intent = Intent(context, EditarProductoActivity::class.java).apply {
                putExtra("ID_PRODUCTO", producto.idProducto)
                putExtra("NOMBRE", producto.nombre)
                putExtra("DESCRIPCION", producto.descripcion)
                putExtra("PRECIO", producto.precio)
                putExtra("STOCK", producto.stock)
                putExtra("ESTATUS", producto.estatus)
            }
            context.startActivity(intent)
        }

        return view
    }
}
