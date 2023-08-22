
package controlalquiler.entidadesdenegocio;

import java.util.ArrayList;

public class Categoria {
    private int id;
    private String nombre;
    private int top_aux;
    private ArrayList<Equipo> equipos;

    public Categoria() {
    }

    public Categoria(int id, String nombre, int top_aux, ArrayList<Equipo> equipos) {
        this.id = id;
        this.nombre = nombre;
        this.top_aux = top_aux;
        this.equipos = equipos;
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

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public ArrayList<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(ArrayList<Equipo> equipos) {
        this.equipos = equipos;
    } 
}
