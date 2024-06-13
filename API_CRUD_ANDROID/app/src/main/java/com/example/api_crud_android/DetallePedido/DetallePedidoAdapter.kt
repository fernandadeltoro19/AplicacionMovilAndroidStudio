package com.example.api_crud_android.DetallePedido

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.api_crud_android.R

class DetallePedidoAdapter(
    context: Context,
    private val dataSource: MutableList<DetallePedido>,
    private val eliminarDetallePedidoCallback: (DetallePedido) -> Unit
) : ArrayAdapter<DetallePedido>(context, R.layout.item_detallepedido, dataSource) {

    private class ViewHolder {
        //lateinit var idDetallePedido: TextView
        lateinit var cantidad: TextView
        lateinit var precioUnitario: TextView
        lateinit var botonEliminar: ImageButton
        lateinit var botonEditar: ImageButton
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_detallepedido, parent, false)
            holder = ViewHolder()
            //holder.idDetallePedido = view.findViewById(R.id.textViewIdDetallePedidoo)
            holder.cantidad = view.findViewById(R.id.textViewCantidadd)
            holder.precioUnitario = view.findViewById(R.id.textViewPrecioUnitarioo)
            holder.botonEliminar = view.findViewById(R.id.btnEliminar)
            holder.botonEditar = view.findViewById(R.id.botonEditarCategoria)

            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        // Obtener el detalle de pedido actual
        val detallePedido = dataSource[position]

        // Asignar valores a los TextView
        //holder.idDetallePedido.text = detallePedido.idDetallePedido.toString()
        holder.cantidad.text = detallePedido.cantidad.toString()
        holder.precioUnitario.text = detallePedido.precioUnitario.toString()

        // Configurar el botón eliminar
        holder.botonEliminar.setOnClickListener {
            eliminarDetallePedidoCallback(detallePedido)
        }

        // Configurar el botón editar
        holder.botonEditar.setOnClickListener {
            val intent = Intent(context, EditarDetallePedidoActivity::class.java).apply {
                putExtra("ID_DETALLE_PEDIDO", detallePedido.idDetallePedido)
                putExtra("CANTIDAD", detallePedido.cantidad)
                putExtra("PRECIO_UNITARIO", detallePedido.precioUnitario)
            }
            context.startActivity(intent)
        }

        return view
    }
}