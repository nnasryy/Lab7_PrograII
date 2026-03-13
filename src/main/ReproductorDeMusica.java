/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import controlador.Controlador;
import gui.VentanaPrincipal;
import reproductor.ReproductorAudio;
import servicio.Playlist;

public class ReproductorDeMusica {
    public static void main(String[] args) {

        VentanaPrincipal vista = new VentanaPrincipal();
        Playlist servicio = new Playlist();
        ReproductorAudio reproductor = new ReproductorAudio();
        Controlador ctrl = new Controlador(vista, servicio, reproductor);
        

        vista.setControlador(ctrl);
        ctrl.iniciar();
    }
}