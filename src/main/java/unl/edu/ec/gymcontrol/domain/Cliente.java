package unl.edu.ec.gymcontrol.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Persona {
    private LocalDate fechaRegistro;
    private List<Membresia> membresias = new ArrayList<>();

    public Cliente() {}

    public Cliente(String id, String nombre, String telefono, String email, String direccion) {
        super(id, nombre, telefono, email, direccion);
        this.fechaRegistro = LocalDate.now();
    }

    public void agregarMembresia(Membresia membresia) {
        membresias.add(membresia);
        membresia.setCliente(this);
    }

    public List<Membresia> getMembresiasActivas() {
        return membresias.stream().filter(Membresia::esActiva).toList();
    }

    public List<Membresia> getMembresias() { return membresias; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    @Override
    public String getDatosPersonales() {
        return super.getDatosPersonales() + " | Cliente desde: " + fechaRegistro;
    }
}
