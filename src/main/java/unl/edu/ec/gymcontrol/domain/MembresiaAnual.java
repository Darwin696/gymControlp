package unl.edu.ec.gymcontrol.domain;
import java.time.LocalDate;

public class MembresiaAnual extends Membresia {
    public MembresiaAnual() {}
    public MembresiaAnual(String id, double precio, LocalDate fechaInicio, Cliente cliente) {
        super(id, precio, fechaInicio, cliente);
    }
    @Override
    protected void calcularFechaVencimiento() {
        this.fechaVencimiento = this.fechaInicio.plusYears(1);
    }
}
