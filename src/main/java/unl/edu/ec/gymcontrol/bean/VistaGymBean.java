package unl.edu.ec.gymcontrol.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("vistaGymBean")
@SessionScoped
public class VistaGymBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<PlanVista> planes;
    private List<MiembroVista> miembros;
    private List<InstructorVista> instructores;
    private List<CheckInVista> checkIns;
    private List<PaymentVista> pagos;
    private List<ProductoVista> productos;

    // Form miembro
    private String nuevoNombre, nuevoTelefono, nuevoEmail, nuevoEstado = "Active";
    // Form plan
    private String nuevoPlanNombre, nuevoPlanTipo = "Basic", nuevoPlanDescripcion;
    private double nuevoPlanPrecio;
    // Form instructor
    private String nuevoInstNombre, nuevoInstEspecialidad, nuevoInstHorario;
    // Form pago/factura membresia
    private String nuevoPagoNombre, nuevoPagoPlan, nuevoPagoMetodo = "Card", nuevoPagoEstado = "Paid";
    private double nuevoPagoMonto;
    // Form producto
    private String nuevoProdNombre, nuevoProdCategoria = "Suplemento";
    private double nuevoProdPrecio;
    private int nuevoProdStock;
    // Form venta producto
    private String ventaCliente, ventaProductoId, ventaMetodo = "Card";
    private int ventaCantidad = 1;

    @PostConstruct
    public void init() {
        planes = new ArrayList<>();
        planes.add(new PlanVista("Mensual Básico", "Basic", 50.00, "Acceso al Gym", List.of("Locker", "Ducha"), true));
        planes.add(new PlanVista("Trimestral Pro", "Pro", 120.00, "Gym + Piscina", List.of("Piscina", "Locker", "Clases"), true));
        planes.add(new PlanVista("Anual Premium", "Premium", 300.00, "Gym + Piscina + Spa", List.of("Piscina", "Spa", "Clases", "Entrenador"), true));

        miembros = new ArrayList<>();
        miembros.add(new MiembroVista("AF-20934", "Marcus Thorne", "+593 99 123 4567", "m.thorne@apex.com", "Active"));
        miembros.add(new MiembroVista("AF-20935", "Sofia Rivera", "+593 99 234 5678", "s.rivera@apex.com", "Expired"));
        miembros.add(new MiembroVista("AF-20936", "Daniel Cruz", "+593 99 345 6789", "d.cruz@apex.com", "Active"));
        miembros.add(new MiembroVista("AF-20937", "Ana López", "+593 98 111 2222", "a.lopez@apex.com", "Active"));

        instructores = new ArrayList<>();
        instructores.add(new InstructorVista("Ana Morales", "CrossFit", "09:00", "4.9", 18, "Active", "https://images.unsplash.com/photo-1518611012118-696072aa579a", true));
        instructores.add(new InstructorVista("Luis Ortega", "Yoga", "11:00", "4.8", 12, "Active", "https://images.unsplash.com/photo-1517836357463-d25dfeac3438", true));
        instructores.add(new InstructorVista("Marta Silva", "Fuerza", "18:00", "5.0", 21, "On Break", "https://images.unsplash.com/photo-1526401485004-5f7ddf94c6b8", false));

        checkIns = new ArrayList<>();
        checkIns.add(new CheckInVista("Marcus Thorne", "Active Membership • Platinum", "10:05 AM", "Entry Approved"));
        checkIns.add(new CheckInVista("Sofia Rivera", "Paused Membership • Basic", "09:40 AM", "Entry Denied"));
        checkIns.add(new CheckInVista("Daniel Cruz", "Active Membership • Pro", "08:20 AM", "Entry Approved"));

        pagos = new ArrayList<>();
        pagos.add(new PaymentVista("Julianna Dougherty", "Premium Plan", "Card", 120.00, "12 Oct 2025", "Paid"));
        pagos.add(new PaymentVista("Marcus Sterling", "Basic Plan", "Transfer", 75.00, "11 Oct 2025", "Pending"));
        pagos.add(new PaymentVista("Helena Suarez", "Pro Plan", "Card", 180.00, "10 Oct 2025", "Paid"));
        pagos.add(new PaymentVista("Carlos Medina", "Basic Plan", "Transfer", 50.00, "09 Oct 2025", "Paid"));

        productos = new ArrayList<>();
        productos.add(new ProductoVista("P-001", "Whey Protein 2lb", "Suplemento", 35.00, 20));
        productos.add(new ProductoVista("P-002", "Creatina 300g", "Suplemento", 18.00, 15));
        productos.add(new ProductoVista("P-003", "Botella Gym 1L", "Accesorio", 8.50, 40));
        productos.add(new ProductoVista("P-004", "Camiseta Apex", "Ropa", 22.00, 12));
        productos.add(new ProductoVista("P-005", "Agua 500ml", "Bebida", 1.50, 100));
        productos.add(new ProductoVista("P-006", "Toalla Gym", "Accesorio", 12.00, 25));
        productos.add(new ProductoVista("P-007", "Pre-Workout", "Suplemento", 28.00, 10));
    }

    private String fechaHoy() {
        LocalDate h = LocalDate.now();
        return h.getDayOfMonth() + " " + h.getMonth().name().substring(0, 3) + " " + h.getYear();
    }

    private void msg(String texto, boolean error) {
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(error ? FacesMessage.SEVERITY_ERROR : FacesMessage.SEVERITY_INFO, texto, null));
    }

    // ===== MIEMBROS =====
    public String agregarMiembro() {
        if (nuevoNombre == null || nuevoNombre.isBlank()) { msg("Nombre obligatorio", true); return null; }
        String id = "AF-" + (20000 + miembros.size() + 1);
        miembros.add(new MiembroVista(id, nuevoNombre, nvl(nuevoTelefono), nvl(nuevoEmail), nvl(nuevoEstado, "Active")));
        nuevoNombre = nuevoTelefono = nuevoEmail = null; nuevoEstado = "Active";
        msg("Miembro agregado", false);
        return "miembros?faces-redirect=true";
    }
    public String eliminarMiembro(MiembroVista m) { miembros.remove(m); msg("Miembro eliminado", false); return null; }

    // ===== PLANES =====
    public String agregarPlan() {
        if (nuevoPlanNombre == null || nuevoPlanNombre.isBlank()) { msg("Nombre del plan obligatorio", true); return null; }
        planes.add(new PlanVista(nuevoPlanNombre, nuevoPlanTipo, nuevoPlanPrecio, nvl(nuevoPlanDescripcion), List.of("Acceso básico"), true));
        nuevoPlanNombre = nuevoPlanDescripcion = null; nuevoPlanTipo = "Basic"; nuevoPlanPrecio = 0;
        msg("Plan agregado", false);
        return "planes?faces-redirect=true";
    }
    public String eliminarPlan(PlanVista p) { planes.remove(p); return null; }

    // ===== INSTRUCTORES =====
    public String agregarInstructor() {
        if (nuevoInstNombre == null || nuevoInstNombre.isBlank()) { msg("Nombre obligatorio", true); return null; }
        instructores.add(new InstructorVista(nuevoInstNombre, nvl(nuevoInstEspecialidad, "General"),
            nvl(nuevoInstHorario, "09:00"), "5.0", 0, "Active",
            "https://images.unsplash.com/photo-1518611012118-696072aa579a", true));
        nuevoInstNombre = nuevoInstEspecialidad = nuevoInstHorario = null;
        msg("Instructor agregado", false);
        return "instructores?faces-redirect=true";
    }
    public String eliminarInstructor(InstructorVista i) { instructores.remove(i); return null; }

    // ===== PAGOS / FACTURA MEMBRESIA =====
    public String agregarPago() {
        if (nuevoPagoNombre == null || nuevoPagoNombre.isBlank()) { msg("Cliente obligatorio", true); return null; }
        if (nuevoPagoMonto <= 0) { msg("Monto debe ser mayor a 0", true); return null; }
        pagos.add(0, new PaymentVista(nuevoPagoNombre, nvl(nuevoPagoPlan, "Membresía"),
            nvl(nuevoPagoMetodo, "Card"), nuevoPagoMonto, fechaHoy(), nvl(nuevoPagoEstado, "Paid")));
        nuevoPagoNombre = nuevoPagoPlan = null; nuevoPagoMetodo = "Card"; nuevoPagoMonto = 0; nuevoPagoEstado = "Paid";
        msg("Factura registrada", false);
        return "pagos?faces-redirect=true";
    }
    public String eliminarPago(PaymentVista p) { pagos.remove(p); return null; }

    // ===== PRODUCTOS =====
    public String agregarProducto() {
        if (nuevoProdNombre == null || nuevoProdNombre.isBlank()) { msg("Nombre obligatorio", true); return null; }
        String id = "P-" + String.format("%03d", productos.size() + 1);
        productos.add(new ProductoVista(id, nuevoProdNombre, nuevoProdCategoria, nuevoProdPrecio, nuevoProdStock));
        nuevoProdNombre = null; nuevoProdCategoria = "Suplemento"; nuevoProdPrecio = 0; nuevoProdStock = 0;
        msg("Producto agregado", false);
        return "productos?faces-redirect=true";
    }
    public String eliminarProducto(ProductoVista p) { productos.remove(p); return null; }

    // ===== VENTA PRODUCTO (factura tienda) =====
    public String registrarVenta() {
        if (ventaCliente == null || ventaCliente.isBlank()) { msg("Cliente obligatorio", true); return null; }
        ProductoVista prod = productos.stream().filter(p -> p.getId().equals(ventaProductoId)).findFirst().orElse(null);
        if (prod == null) { msg("Seleccione un producto", true); return null; }
        if (prod.getStock() < ventaCantidad) { msg("Stock insuficiente de " + prod.getNombre(), true); return null; }
        prod.setStock(prod.getStock() - ventaCantidad);
        double total = prod.getPrecio() * ventaCantidad;
        pagos.add(0, new PaymentVista(ventaCliente, "Producto: " + prod.getNombre() + " x" + ventaCantidad,
            nvl(ventaMetodo, "Card"), total, fechaHoy(), "Paid"));
        ventaCliente = ventaProductoId = null; ventaCantidad = 1; ventaMetodo = "Card";
        msg("Venta registrada. Stock actualizado.", false);
        return "pagos?faces-redirect=true";
    }

    private String nvl(String s) { return s == null ? "" : s; }
    private String nvl(String s, String d) { return (s == null || s.isBlank()) ? d : s; }

    // getters listas
    public List<PlanVista> getPlanes() { return planes; }
    public List<MiembroVista> getMiembros() { return miembros; }
    public List<InstructorVista> getInstructores() { return instructores; }
    public List<CheckInVista> getCheckIns() { return checkIns; }
    public List<PaymentVista> getPagos() { return pagos; }
    public List<ProductoVista> getProductos() { return productos; }

    public double getTotalRevenue() {
        return pagos.stream().filter(p -> "Paid".equals(p.getEstado())).mapToDouble(PaymentVista::getAmount).sum();
    }
    public double getPendingBalance() {
        return pagos.stream().filter(p -> "Pending".equals(p.getEstado())).mapToDouble(PaymentVista::getAmount).sum();
    }
    public long getPendingCount() { return pagos.stream().filter(p -> "Pending".equals(p.getEstado())).count(); }
    public int getCardShare() {
        long c = pagos.stream().filter(p -> "Card".equals(p.getMetodo())).count();
        return pagos.isEmpty() ? 0 : (int) ((c * 100) / pagos.size());
    }
    public int getTransferShare() {
        long t = pagos.stream().filter(p -> "Transfer".equals(p.getMetodo())).count();
        return pagos.isEmpty() ? 0 : (int) ((t * 100) / pagos.size());
    }
    public long getProductosBajoStock() { return productos.stream().filter(p -> p.getStock() < 10).count(); }

    // form getters/setters
    public String getNuevoNombre() { return nuevoNombre; } public void setNuevoNombre(String v) { nuevoNombre = v; }
    public String getNuevoTelefono() { return nuevoTelefono; } public void setNuevoTelefono(String v) { nuevoTelefono = v; }
    public String getNuevoEmail() { return nuevoEmail; } public void setNuevoEmail(String v) { nuevoEmail = v; }
    public String getNuevoEstado() { return nuevoEstado; } public void setNuevoEstado(String v) { nuevoEstado = v; }
    public String getNuevoPlanNombre() { return nuevoPlanNombre; } public void setNuevoPlanNombre(String v) { nuevoPlanNombre = v; }
    public String getNuevoPlanTipo() { return nuevoPlanTipo; } public void setNuevoPlanTipo(String v) { nuevoPlanTipo = v; }
    public double getNuevoPlanPrecio() { return nuevoPlanPrecio; } public void setNuevoPlanPrecio(double v) { nuevoPlanPrecio = v; }
    public String getNuevoPlanDescripcion() { return nuevoPlanDescripcion; } public void setNuevoPlanDescripcion(String v) { nuevoPlanDescripcion = v; }
    public String getNuevoInstNombre() { return nuevoInstNombre; } public void setNuevoInstNombre(String v) { nuevoInstNombre = v; }
    public String getNuevoInstEspecialidad() { return nuevoInstEspecialidad; } public void setNuevoInstEspecialidad(String v) { nuevoInstEspecialidad = v; }
    public String getNuevoInstHorario() { return nuevoInstHorario; } public void setNuevoInstHorario(String v) { nuevoInstHorario = v; }
    public String getNuevoPagoNombre() { return nuevoPagoNombre; } public void setNuevoPagoNombre(String v) { nuevoPagoNombre = v; }
    public String getNuevoPagoPlan() { return nuevoPagoPlan; } public void setNuevoPagoPlan(String v) { nuevoPagoPlan = v; }
    public String getNuevoPagoMetodo() { return nuevoPagoMetodo; } public void setNuevoPagoMetodo(String v) { nuevoPagoMetodo = v; }
    public double getNuevoPagoMonto() { return nuevoPagoMonto; } public void setNuevoPagoMonto(double v) { nuevoPagoMonto = v; }
    public String getNuevoPagoEstado() { return nuevoPagoEstado; } public void setNuevoPagoEstado(String v) { nuevoPagoEstado = v; }
    public String getNuevoProdNombre() { return nuevoProdNombre; } public void setNuevoProdNombre(String v) { nuevoProdNombre = v; }
    public String getNuevoProdCategoria() { return nuevoProdCategoria; } public void setNuevoProdCategoria(String v) { nuevoProdCategoria = v; }
    public double getNuevoProdPrecio() { return nuevoProdPrecio; } public void setNuevoProdPrecio(double v) { nuevoProdPrecio = v; }
    public int getNuevoProdStock() { return nuevoProdStock; } public void setNuevoProdStock(int v) { nuevoProdStock = v; }
    public String getVentaCliente() { return ventaCliente; } public void setVentaCliente(String v) { ventaCliente = v; }
    public String getVentaProductoId() { return ventaProductoId; } public void setVentaProductoId(String v) { ventaProductoId = v; }
    public int getVentaCantidad() { return ventaCantidad; } public void setVentaCantidad(int v) { ventaCantidad = v; }
    public String getVentaMetodo() { return ventaMetodo; } public void setVentaMetodo(String v) { ventaMetodo = v; }

    // inner classes
    public static class PlanVista {
        private final String nombre, tipo, descripcion; private final double precio; private final List<String> beneficios; private final boolean activo;
        public PlanVista(String n, String t, double p, String d, List<String> b, boolean a) { nombre=n; tipo=t; precio=p; descripcion=d; beneficios=b; activo=a; }
        public String getNombre(){return nombre;} public String getTipo(){return tipo;} public double getPrecio(){return precio;}
        public String getDescripcion(){return descripcion;} public List<String> getBeneficios(){return beneficios;} public boolean isActivo(){return activo;}
    }
    public static class MiembroVista {
        private final String id, nombre, telefono, email, estado;
        public MiembroVista(String i, String n, String t, String e, String s){id=i;nombre=n;telefono=t;email=e;estado=s;}
        public String getId(){return id;} public String getNombre(){return nombre;} public String getTelefono(){return telefono;}
        public String getEmail(){return email;} public String getEstado(){return estado;}
    }
    public static class InstructorVista {
        private final String nombre, especialidad, horario, puntuacion, estatus, imageUrl; private final int clientes; private final boolean activo;
        public InstructorVista(String n,String e,String h,String p,int c,String s,String img,boolean a){nombre=n;especialidad=e;horario=h;puntuacion=p;clientes=c;estatus=s;imageUrl=img;activo=a;}
        public String getNombre(){return nombre;} public String getEspecialidad(){return especialidad;} public String getHorario(){return horario;}
        public String getPuntuacion(){return puntuacion;} public int getClientes(){return clientes;} public String getEstatus(){return estatus;}
        public String getImageUrl(){return imageUrl;} public boolean isActivo(){return activo;}
    }
    public static class CheckInVista {
        private final String nombre, detalle, hora, estado;
        public CheckInVista(String n,String d,String h,String e){nombre=n;detalle=d;hora=h;estado=e;}
        public String getNombre(){return nombre;} public String getDetalle(){return detalle;} public String getHora(){return hora;} public String getEstado(){return estado;}
    }
    public static class PaymentVista {
        private final String nombre, plan, metodo, fecha, estado; private final double amount;
        public PaymentVista(String n,String p,String m,double a,String f,String e){nombre=n;plan=p;metodo=m;amount=a;fecha=f;estado=e;}
        public String getNombre(){return nombre;} public String getPlan(){return plan;} public String getMetodo(){return metodo;}
        public double getAmount(){return amount;} public String getFecha(){return fecha;} public String getEstado(){return estado;}
        public String getFormattedAmount(){return String.format("$%.2f", amount);}
        public String getIniciales(){
            if(nombre==null||nombre.isBlank()) return "?";
            String[] p = nombre.trim().split("\\s+");
            if(p.length==1) return p[0].substring(0,1).toUpperCase();
            return (p[0].substring(0,1)+p[p.length-1].substring(0,1)).toUpperCase();
        }
    }
    public static class ProductoVista {
        private final String id, nombre, categoria; private final double precio; private int stock;
        public ProductoVista(String id, String n, String c, double p, int s){this.id=id;this.nombre=n;this.categoria=c;this.precio=p;this.stock=s;}
        public String getId(){return id;} public String getNombre(){return nombre;} public String getCategoria(){return categoria;}
        public double getPrecio(){return precio;} public int getStock(){return stock;} public void setStock(int s){this.stock=s;}
        public String getFormattedPrecio(){return String.format("$%.2f", precio);}
    }
}
