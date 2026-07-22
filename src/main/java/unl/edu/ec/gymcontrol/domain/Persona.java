package unl.edu.ec.gymcontrol.domain;

import java.io.Serializable;

public abstract class Persona implements Serializable {
    protected String id;
    protected String nombre;
    protected String telefono;
    protected String email;
    protected String direccion;

    public Persona() {}

    public Persona(String id, String nombre, String telefono, String email, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }

    public String getDatosPersonales() {
        return "ID: " + id + " | Nombre: " + nombre;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
