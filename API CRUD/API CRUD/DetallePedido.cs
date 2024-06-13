using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

public class DetallePedido
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int idDetallePedido { get; set; }

    public int cantidad { get; set; }

    public decimal precioUnitario { get; set; }

    public int estatus { get; set; } = 1;
}

