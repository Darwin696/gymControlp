package unl.edu.ec.gymcontrol.domain;

import java.time.LocalDate;

public abstract class Membresia {
    protected String id;
    protected double precio;
    protected LocalDate fechaInicio;
    protected LocalDate fechaVencimiento;
    protected EstadoMembresia estado;
    protected Cliente cliente;

    public Membresia() {}

    public Membresia(String id, double precio, LocalDate fechaInicio, Cliente cliente) {
        this.id = id;
        this.precio = precio;
        this.fechaInicio = fechaInicio;
        this.cliente = cliente;
        this.estado = EstadoMembresia.ACTIVA;
        calcularFechaVencimiento();
    }

    protected abstract void calcularFechaVencimiento();

    public boolean esActiva() {
        return estado == EstadoMembresia.ACTIVA &&
               (fechaVencimiento == null || !LocalDate.now().isAfter(fechaVencimiento));
    }

    public void renovar() {
        this.fechaInicio = LocalDate.now();
        calcularFechaVencimiento();
        this.estado = EstadoMembresia.ACTIVA;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public EstadoMembresia getEstado() { return estado; }
    public void setEstado(EstadoMembresia estado) { this.estado = estado; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}
