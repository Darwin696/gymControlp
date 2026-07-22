package unl.edu.ec.gymcontrol.domain;

public class Instructor extends Empleado {
    private String especialidad;
    private String horario;

    public Instructor() {}
    public Instructor(String id, String nombre, String telefono, String email, String direccion,
                      String cargo, double salario, String especialidad, String horario) {
        super(id, nombre, telefono, email, direccion, cargo, salario);
        this.especialidad = especialidad;
        this.horario = horario;
    }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
}
