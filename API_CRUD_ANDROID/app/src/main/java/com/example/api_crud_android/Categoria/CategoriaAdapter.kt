import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.example.api_crud_android.Categoria.Categoria
import com.example.api_crud_android.Categoria.EditarCategoriaActivity
import com.example.api_crud_android.R


class CategoriaAdapter(
    context: Context,
    private val dataSource: MutableList<Categoria>,
    private val eliminarCategoriaCallback: (Categoria) -> Unit
) : ArrayAdapter<Categoria>(context, R.layout.item_categoria, dataSource) {

    private class ViewHolder {
        //lateinit var idCategoria: TextView
        lateinit var nombre: TextView
        lateinit var botonEliminar: ImageButton
        lateinit var botonEditar: ImageButton
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_categoria, parent, false)
            holder = ViewHolder()
            //holder.idCategoria = view.findViewById(R.id.textViewIdCategoria)
            holder.nombre = view.findViewById(R.id.textViewNombreCategoria)
            holder.botonEliminar = view.findViewById(R.id.btnEliminar)
            holder.botonEditar = view.findViewById(R.id.botonEditarCategoria)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        // Obtener la categoria actual
        val categoria = dataSource[position]

        // Asignar valores a los TextView
        //holder.idCategoria.text = categoria.idCategoria.toString()
        holder.nombre.text = categoria.nombre

        // Configurar el botón eliminar
        holder.botonEliminar.setOnClickListener {
            eliminarCategoriaCallback(categoria)
        }

        // Configurar el botón editar
        holder.botonEditar.setOnClickListener {
            val intent = Intent(context, EditarCategoriaActivity::class.java).apply {
                putExtra("ID_CATEGORIA", categoria.idCategoria)
                putExtra("NOMBRE", categoria.nombre)
                putExtra("ESTATUS", categoria.estatus)
            }
            context.startActivity(intent)
        }

        return view
    }
}
