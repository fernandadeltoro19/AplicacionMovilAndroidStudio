using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace API_CRUD.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsuarioController : ControllerBase
    {
        private readonly DataContext _context;

        public UsuarioController(DataContext context)
        {
            _context = context;
        }

        // GET: api/Categoria
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Usuario>>> MostrarUsuario()
        {
            return await _context.Usuario.ToListAsync();
        }

        // GET: api/Categoria/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Usuario>> GetUsuario(int id)
        {
            var usuario = await _context.Usuario.FindAsync(id);

            if (usuario == null)
            {
                return NotFound();
            }

            return usuario;
        }

        // POST: api/Usuario
        [HttpPost]
        public async Task<ActionResult<Usuario>> AgregarUsuario(string nombreUsuario, string contrasena, string correo, string rol)
        {
            // Verificar si el correo ya existe
            var usuarioExistente = await _context.Usuario.FirstOrDefaultAsync(u => u.correo == correo);
            if (usuarioExistente != null)
            {
                return Conflict("El correo ya está en uso.");
            }

            var nuevoUsuario = new Usuario
            {
                nombreUsuario = nombreUsuario,
                contrasena = contrasena,
                correo = correo,
                rol = rol,
                estatus = 1, // Establecer estatus a 1 por defecto
            };

            _context.Usuario.Add(nuevoUsuario);
            await _context.SaveChangesAsync();

            return CreatedAtAction(nameof(GetUsuario), new { id = nuevoUsuario.idUsuario }, nuevoUsuario);
        }


        // PUT: api/Usuario/5
        [HttpPut("{id}")]
        public async Task<IActionResult> ActualizarUsuario(int id, string nombreUsuario, string contrasena, string correo, string rol)
        {
            var usuario = await _context.Usuario.FindAsync(id);

            if (usuario == null)
            {
                return NotFound();
            }

            usuario.nombreUsuario = nombreUsuario;
            usuario.contrasena = contrasena;
            usuario.correo = correo;
            usuario.rol = rol;
            usuario.estatus = 1; // Establecer estatus a 1 por defecto

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UsuarioExiste(id))
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
        public async Task<IActionResult> EliminarUsuario(int id)
        {
            var usuario = await _context.Usuario.FindAsync(id);
            if (usuario == null)
            {
                return NotFound();
            }

            usuario.estatus = 0; // Cambiando el status a 0 en lugar de eliminar

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UsuarioExiste(id))
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

        private bool UsuarioExiste(int id)
        {
            return _context.Usuario.Any(e => e.idUsuario == id);
        }

        // POST: api/Usuario/VerificarExistencia
        [HttpGet("VerificarExistencia")]
        public async Task<ActionResult<string>> VerificarExistenciaUsuario(string correo, string contrasena)
        {
            var usuario = await _context.Usuario.FirstOrDefaultAsync(u => u.correo == correo && u.contrasena == contrasena);

            if (usuario != null)
            {
                // El usuario existe en la base de datos
                return Ok("El usuario existe.");
            }

            // El usuario no existe en la base de datos o la contraseña es incorrecta
            return NotFound("El usuario no existe o la contraseña es incorrecta.");
        }
    }
}

