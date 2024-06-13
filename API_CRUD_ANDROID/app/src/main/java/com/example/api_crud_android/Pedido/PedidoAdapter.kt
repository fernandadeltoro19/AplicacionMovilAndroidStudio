package com.example.api_crud_android.Pedido

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.api_crud_android.R

class PedidoAdapter(
    context: Context,
    private val dataSource: MutableList<Pedido>,
    private val eliminarPedidoCallback: (Pedido) -> Unit
) : ArrayAdapter<Pedido>(context, R.layout.item_pedido, dataSource) {

    private class ViewHolder {
        //lateinit var idPedido: TextView
        lateinit var fecha: TextView
        lateinit var direccion: TextView
        lateinit var botonEliminar: ImageButton
        lateinit var botonEditar: ImageButton
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_pedido, parent, false)
            holder = ViewHolder()
            //holder.idPedido = view.findViewById(R.id.textViewIdPedido)
            holder.fecha = view.findViewById(R.id.textViewFechaPedido)
            holder.direccion = view.findViewById(R.id.textViewDireccion)
            holder.botonEliminar = view.findViewById(R.id.btnEliminar)
            holder.botonEditar = view.findViewById(R.id.botonEditarPedido)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        // Obtener el pedido actual
        val pedido = dataSource[position]

        // Asignar valores a los TextView
        //holder.idPedido.text = pedido.idPedido.toString()
        holder.fecha.text = pedido.fecha
        holder.direccion.text = pedido.direccion

        // Configurar el botón eliminar
        holder.botonEliminar.setOnClickListener {
            eliminarPedidoCallback(pedido)
        }

        // Configurar el botón editar
        holder.botonEditar.setOnClickListener {
            val intent = Intent(context, EditarPedidoActivity::class.java).apply {
                putExtra("ID_PEDIDO", pedido.idPedido)
                putExtra("FECHA", pedido.fecha)
                putExtra("DIRECCION", pedido.direccion)
                // Agrega más extras si es necesario para la actividad de edición
            }
            context.startActivity(intent)
        }

        return view
    }
}