
package controlalquiler.entidadesdenegocio;

public class DetalleAlquiler {
    private int id;
    private int idAquiler;
    private int idEquipo;
    private int cantidad;
    private double precio;
    private double subTotal;
    private int top_aux;
    private Alquiler alquiler;
    private Equipo equipo;

    public DetalleAlquiler() {
    }

    public DetalleAlquiler(int id, int idAquiler, int idEquipo, int cantidad, double precio, double subTotal, int top_aux, Alquiler alquiler, Equipo equipo) {
        this.id = id;
        this.idAquiler = idAquiler;
        this.idEquipo = idEquipo;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subTotal = subTotal;
        this.top_aux = top_aux;
        this.alquiler = alquiler;
        this.equipo = equipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAquiler() {
        return idAquiler;
    }

    public void setIdAquiler(int idAquiler) {
        this.idAquiler = idAquiler;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public Alquiler getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Alquiler alquiler) {
        this.alquiler = alquiler;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}
