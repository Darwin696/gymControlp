package unl.edu.ec.gymcontrol.domain;

public class Empleado extends Persona {
    private String cargo;
    private double salario;

    public Empleado() {}
    public Empleado(String id, String nombre, String telefono, String email, String direccion, String cargo, double salario) {
        super(id, nombre, telefono, email, direccion);
        this.cargo = cargo;
        this.salario = salario;
    }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
}
