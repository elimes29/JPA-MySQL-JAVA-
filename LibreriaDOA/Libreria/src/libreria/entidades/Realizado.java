/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.entidades;

/**
 *
 * @author usuario
 */
public class Realizado {
    private Libro libro;
    private Boolean reaizado;

    public Realizado() {
    }

    public Realizado(Libro libro, Boolean reaizado) {
        this.libro = libro;
        this.reaizado = reaizado;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Boolean getReaizado() {
        return reaizado;
    }

    public void setReaizado(Boolean reaizado) {
        this.reaizado = reaizado;
    }

    
}
