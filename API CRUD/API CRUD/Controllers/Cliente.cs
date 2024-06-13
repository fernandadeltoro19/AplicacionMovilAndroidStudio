using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace API_CRUD.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ClienteController : ControllerBase
    {
        private readonly DataContext _context;

        public ClienteController(DataContext context)
        {
            _context = context;
        }

        // GET: api/Categoria
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Cliente>>> MostrarCliente()
        {
            return await _context.Cliente.ToListAsync();
        }

        // GET: api/Categoria/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Cliente>> GetCliente(int id)
        {
            var cliente = await _context.Cliente.FindAsync(id);

            if (cliente == null)
            {
                return NotFound();
            }

            return cliente;
        }

        // POST: api/Categoria
        [HttpPost]
        public async Task<ActionResult<Cliente>> AgregarCliente(string nombre, string apellido, string direccion, int telefono, string correo)
        {
            var nuevoCliente = new Cliente
            {
                nombre = nombre,
                apellido = apellido,
                direccion = direccion,
                telefono = telefono,
                correo = correo,
                estatus = 1, // Por defecto, estatus activo
            };

            _context.Cliente.Add(nuevoCliente);
            await _context.SaveChangesAsync();

            return CreatedAtAction(nameof(GetCliente), new { id = nuevoCliente.idCliente }, nuevoCliente);
        }

        // PUT: api/Categoria/5
        [HttpPut("{id}")]
        public async Task<IActionResult> ActualizarCliente(int id, string nombre, string apellido, string direccion, int telefono, string correo)
        {
            var cliente = await _context.Cliente.FindAsync(id);

            if (cliente == null)
            {
                return NotFound();
            }

            cliente.nombre = nombre;
            cliente.apellido = apellido;
            cliente.direccion = direccion;
            cliente.telefono = telefono;
            cliente.correo = correo;

            // No se modifica el estatus, se mantiene el valor actual

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ClienteExiste(id))
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
        public async Task<IActionResult> EliminarCliente(int id)
        {
            var cliente = await _context.Cliente.FindAsync(id);
            if (cliente == null)
            {
                return NotFound();
            }

            cliente.estatus = 0; // Cambiando el status a 0 en lugar de eliminar

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ClienteExiste(id))
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

        private bool ClienteExiste(int id)
        {
            return _context.Cliente.Any(e => e.idCliente == id);
        }
    }
}

