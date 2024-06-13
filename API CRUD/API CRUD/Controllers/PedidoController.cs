using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace API_CRUD.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PedidoController : ControllerBase
    {
        private readonly DataContext _context;

        public PedidoController(DataContext context)
        {
            _context = context;
        }

        // GET: api/Categoria
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Pedido>>> MostrarPedido()
        {
            return await _context.Pedido.ToListAsync();
        }

        // GET: api/Categoria/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Pedido>> GetPedido(int id)
        {
            var pedido = await _context.Pedido.FindAsync(id);

            if (pedido == null)
            {
                return NotFound();
            }

            return pedido;
        }

        // POST: api/Pedido
        [HttpPost]
        public async Task<ActionResult<Pedido>> AgregarPedido(string fecha, string direccion)
        {
            var nuevoPedido = new Pedido
            {
                fecha = fecha,
                direccion = direccion,
                estatus = 1, // Establecer estatus a 1 por defecto
            };

            _context.Pedido.Add(nuevoPedido);
            await _context.SaveChangesAsync();

            return CreatedAtAction(nameof(GetPedido), new { id = nuevoPedido.idPedido }, nuevoPedido);
        }


        // PUT: api/Pedido/5
        [HttpPut("{id}")]
        public async Task<IActionResult> ActualizarPedido(int id, string fecha, string direccion)
        {
            var pedido = await _context.Pedido.FindAsync(id);

            if (pedido == null)
            {
                return NotFound();
            }

            pedido.fecha = fecha;
            pedido.direccion = direccion;
            pedido.estatus = 1; // Establecer estatus a 1 por defecto

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PedidoExiste(id))
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
        public async Task<IActionResult> EliminarPedido(int id)
        {
            var pedido = await _context.Pedido.FindAsync(id);
            if (pedido == null)
            {
                return NotFound();
            }

            pedido.estatus = 0; // Cambiando el status a 0 en lugar de eliminar

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PedidoExiste(id))
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

        private bool PedidoExiste(int id)
        {
            return _context.Cliente.Any(e => e.idCliente == id);
        }
    }
}
