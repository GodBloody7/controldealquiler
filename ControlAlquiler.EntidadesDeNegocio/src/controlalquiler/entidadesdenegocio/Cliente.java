
package controlalquiler.entidadesdenegocio;

public class Cliente {
    private int id;
    private String nombre;
    private String apellido;
    private String razonsocial;
    private String telefono;
    private String direccion;
    private byte estado;
    private int top_aux;

    public Cliente() {
    }

    public Cliente(int id, String nombre, String apellido, String razonsocial, String telefono, String direccion, byte estado, int top_aux) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.razonsocial = razonsocial;
        this.telefono = telefono;
        this.direccion = direccion;
        this.estado = estado;
        this.top_aux = top_aux;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public byte getEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }
   



}

