
package controlalquiler.entidadesdenegocio;

import java.time.LocalDate;

public class Alquiler {
       private int id;
    private int idCliente;
    private int idUsuario;
    private LocalDate fechaEntrega;
    private LocalDate fechaDevolucion;
    private LocalDate fechaRegistrado;
    private int top_aux;
    private Usuario usuario;
    private Cliente cliente;
    
    public Alquiler() {
    }

    public Alquiler(int id, int idCliente, int idUsuario, LocalDate fechaEntrega, LocalDate fechaDevolucion, LocalDate fechaRegistrado, int top_aux, Usuario usuario, Cliente cliente) {
        this.id = id;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.fechaEntrega = fechaEntrega;
        this.fechaDevolucion = fechaDevolucion;
        this.fechaRegistrado = fechaRegistrado;
        this.top_aux = top_aux;
        this.usuario = usuario;
        this.cliente = cliente;
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
    
    public Cliente getCliente(){
        return cliente;
    }
    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
}

