using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace API_CRUD.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class DetallePedidoController : ControllerBase
    {
        private readonly DataContext _context;

        public DetallePedidoController(DataContext context)
        {
            _context = context;
        }

        // GET: api/Categoria
        [HttpGet]
        public async Task<ActionResult<IEnumerable<DetallePedido>>> MostrarDetallePedido()
        {
            return await _context.DetallePedido.ToListAsync();
        }

        // GET: api/Categoria/5
        [HttpGet("{id}")]
        public async Task<ActionResult<DetallePedido>> GetDetallePedido(int id)
        {
            var detallepedido = await _context.DetallePedido.FindAsync(id);

            if (detallepedido == null)
            {
                return NotFound();
            }

            return detallepedido;
        }

        // POST: api/Categoria
        [HttpPost]
        public async Task<ActionResult<DetallePedido>> AgregarDetallePedido(int cantidad, decimal precioUnitario)
        {
            var nuevoDetallePedido = new DetallePedido
            {
                cantidad = cantidad,
                precioUnitario = precioUnitario,
                estatus = 1 // Establecer estatus en 1 automáticamente
            };

            _context.DetallePedido.Add(nuevoDetallePedido);
            await _context.SaveChangesAsync();

            return CreatedAtAction(nameof(GetDetallePedido), new { id = nuevoDetallePedido.idDetallePedido }, nuevoDetallePedido);
        }



        // PUT: api/Categoria/5
        [HttpPut("{id}")]
        public async Task<IActionResult> ActualizarDetallePedido(int id, int cantidad, decimal precioUnitario)
        {
            var detallePedido = await _context.DetallePedido.FindAsync(id);

            if (detallePedido == null)
            {
                return NotFound();
            }

            detallePedido.cantidad = cantidad;
            detallePedido.precioUnitario = precioUnitario;
            detallePedido.estatus = 1; // Establecer estatus en 1 automáticamente

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!DetallePedidoExiste(id))
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
        public async Task<IActionResult> EliminarDetallePedido(int id)
        {
            var detallepedido = await _context.DetallePedido.FindAsync(id);
            if (detallepedido == null)
            {
                return NotFound();
            }

            detallepedido.estatus = 0; // Cambiando el status a 0 en lugar de eliminar

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!DetallePedidoExiste(id))
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

        private bool DetallePedidoExiste(int id)
        {
            return _context.Cliente.Any(e => e.idCliente == id);
        }
    }
}
