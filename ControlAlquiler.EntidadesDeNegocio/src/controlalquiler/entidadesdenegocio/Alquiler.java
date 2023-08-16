
package controlalquiler.entidadesdenegocio;

import java.time.LocalDate;

public class Alquiler {
       private int id;
    private int idCliente;
    private int idUsuario;
    private LocalDate fechaEntrega;
    private LocalDate fechaDevolucion;
    private LocalDate fechaRegistrado;
    private LocalDate fechaCerrado;
    private byte estatus;
    private int top_aux;
    private Usuario usuario;

    
    public Alquiler() {
    }

    public Alquiler(int id, int idCliente, int idUsuario, LocalDate fechaEntrega, LocalDate fechaDevolucion, LocalDate fechaRegistrado, LocalDate fechaCerrado, byte estatus, int top_aux, Usuario usuario) {
        this.id = id;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.fechaEntrega = fechaEntrega;
        this.fechaDevolucion = fechaDevolucion;
        this.fechaRegistrado = fechaRegistrado;
        this.fechaCerrado = fechaCerrado;
        this.estatus = estatus;
        this.top_aux = top_aux;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public LocalDate getFechaRegistrado() {
        return fechaRegistrado;
    }

    public void setFechaRegistrado(LocalDate fechaRegistrado) {
        this.fechaRegistrado = fechaRegistrado;
    }

    public LocalDate getFechaCerrado() {
        return fechaCerrado;
    }

    public void setFechaCerrado(LocalDate fechaCerrado) {
        this.fechaCerrado = fechaCerrado;
    }

    public byte getEstatus() {
        return estatus;
    }

    public void setEstatus(byte status) {
        this.estatus = estatus;
    }
    
        public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }
    public Usuario getUsuario(){
        return usuario;
    }
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }
}
