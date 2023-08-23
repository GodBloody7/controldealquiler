
package controlalquiler.entidadesdenegocio;

import java.util.ArrayList;

public class Cliente {
    private int id;
    private String nombre;
    private String apellido;
    private String razonsocial;
    private String telefono;
    private String direccion;
    private int top_aux;
    private ArrayList<Alquiler>alquileres;

    public Cliente() {
    }

    public Cliente(int id, String nombre, String apellido, String razonsocial, String telefono, String direccion, int top_aux, ArrayList<Alquiler>alquileres) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.razonsocial = razonsocial;
        this.telefono = telefono;
        this.direccion = direccion;
        this.top_aux = top_aux;
        this.alquileres = alquileres;
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

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }
   
    public ArrayList<Alquiler>getAlquileres(){
        return alquileres;
    }

    public void setAlquileres(ArrayList<Alquiler> alquileres){
        this.alquileres = alquileres;
    }

}

