using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace API_CRUD.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ProductoController : ControllerBase
    {
        private readonly DataContext _context;

        public ProductoController(DataContext context)
        {
            _context = context;
        }

        // GET: api/Categoria
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Producto>>> MostrarProducto()
        {
            return await _context.Producto.ToListAsync();
        }

        // GET: api/Categoria/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Producto>> GetProducto(int id)
        {
            var producto = await _context.Producto.FindAsync(id);

            if (producto == null)
            {
                return NotFound();
            }

            return producto;
        }

        // POST: api/Producto
        [HttpPost]
        public async Task<ActionResult<Producto>> AgregarProducto(string nombre, string descripcion, decimal precio, int stock)
        {
            var nuevoProducto = new Producto
            {
                nombre = nombre,
                descripcion = descripcion,
                precio = precio,
                stock = stock,
                estatus = 1, // Establecer estatus a 1 por defecto
            };

            _context.Producto.Add(nuevoProducto);
            await _context.SaveChangesAsync();

            return CreatedAtAction(nameof(GetProducto), new { id = nuevoProducto.idProducto }, nuevoProducto);
        }


        // PUT: api/Producto/5
        [HttpPut("{id}")]
        public async Task<IActionResult> ActualizarProducto(int id, string nombre, string descripcion, decimal precio, int stock)
        {
            var producto = await _context.Producto.FindAsync(id);

            if (producto == null)
            {
                return NotFound();
            }

            producto.nombre = nombre;
            producto.descripcion = descripcion;
            producto.precio = precio;
            producto.stock = stock;
            producto.estatus = 1; // Establecer estatus a 1 por defecto

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ProductoExiste(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }





        // DELETE: api/Categoria/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> EliminarProducto(int id)
        {
            var producto = await _context.Producto.FindAsync(id);
            if (producto == null)
            {
                return NotFound();
            }

            producto.estatus = 0; // Cambiando el status a 0 en lugar de eliminar

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ProductoExiste(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        private bool ProductoExiste(int id)
        {
            return _context.Producto.Any(e => e.idProducto == id);
        }
    }
}
