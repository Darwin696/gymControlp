package unl.edu.ec.gymcontrol.domain;

import java.io.Serializable;

public class Producto implements Serializable {
    private String id;
    private String nombre;
    private String categoria; // Suplemento, Ropa, Bebida, Accesorio
    private double precio;
    private int stock;

    public Producto() {}

    public Producto(String id, String nombre, String categoria, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    public boolean hayStock(int cantidad) {
        return stock >= cantidad;
    }

    public void vender(int cantidad) {
        if (!hayStock(cantidad)) {
            throw new IllegalStateException("Stock insuficiente de " + nombre);
        }
        this.stock -= cantidad;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
