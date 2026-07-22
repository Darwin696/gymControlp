package unl.edu.ec.gymcontrol.domain;
import java.time.LocalDate;

public class MembresiaMensual extends Membresia {
    public MembresiaMensual() {}
    public MembresiaMensual(String id, double precio, LocalDate fechaInicio, Cliente cliente) {
        super(id, precio, fechaInicio, cliente);
    }
    @Override
    protected void calcularFechaVencimiento() {
        this.fechaVencimiento = this.fechaInicio.plusMonths(1);
    }
}
