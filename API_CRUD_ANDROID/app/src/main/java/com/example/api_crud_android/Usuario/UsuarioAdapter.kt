package com.example.proyectoriojas.Usuario

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.api_crud_android.R
import com.example.api_crud_android.Usuario.EditarUsuarioActivity
import com.example.api_crud_android.Usuario.Usuario

class UsuarioAdapter(
    context: Context,
    private val dataSource: MutableList<Usuario>,
    private val eliminarUsuarioCallback: (Usuario) -> Unit
) : ArrayAdapter<Usuario>(context, R.layout.item_usuario, dataSource) {

    private class ViewHolder {
        lateinit var nombreUsuario: TextView
        lateinit var correoUsuario: TextView
        lateinit var botonEliminar: ImageButton
        lateinit var botonEditar: ImageButton
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_usuario, parent, false)
            holder = ViewHolder()
            holder.nombreUsuario = view.findViewById(R.id.textViewNombreUsuario)
            holder.correoUsuario = view.findViewById(R.id.textViewCorreo)
            holder.botonEliminar = view.findViewById(R.id.botonEliminar)
            holder.botonEditar = view.findViewById(R.id.botonEditarUsuario)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val usuario = dataSource[position]

        holder.nombreUsuario.text = usuario.nombreUsuario
        holder.correoUsuario.text = usuario.correo

        holder.botonEliminar.setOnClickListener {
            eliminarUsuarioCallback(usuario)
        }

        holder.botonEditar.setOnClickListener {
            val intent = Intent(context, EditarUsuarioActivity::class.java)
            intent.putExtra("ID_USUARIO", usuario.idUsuario)
            intent.putExtra("NOMBRE_USUARIO", usuario.nombreUsuario)
            intent.putExtra("CONTRASENA", usuario.contrasena)
            intent.putExtra("CORREO", usuario.correo)
            intent.putExtra("ROL", usuario.rol)
            context.startActivity(intent)
        }

        return view
    }
}
