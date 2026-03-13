/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reproductor;

import interfaces.Reproducible;
import model.Cancion;


public abstract class Reproductor implements Reproducible {
    protected Cancion actual;
    
    public void setCancion(Cancion c) {
        this.actual = c;
    }
    
    public Cancion getActual() {
        return actual;
    }
}