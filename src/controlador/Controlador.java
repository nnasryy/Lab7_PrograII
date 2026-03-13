/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import gui.VentanaPrincipal;
import model.Cancion;
import reproductor.ReproductorAudio;
import servicio.Playlist;
import javax.swing.*;

public class Controlador {
    private VentanaPrincipal vista;
    private Playlist playlist;
    private ReproductorAudio reproductor;

    public Controlador(VentanaPrincipal v, Playlist p, ReproductorAudio r) {
        this.vista = v;
        this.playlist = p;
        this.reproductor = r;
    }

    public void iniciar() {
        playlist.cargar();
        vista.actualizarLista(playlist.getLista());
        vista.setVisible(true);
    }

    public void play(int index) {
        Cancion c = playlist.get(index);
        if (c != null) {
            reproductor.stop();
            reproductor.setCancion(c);
            reproductor.play();
            vista.setImagen(c.getRutaImagen());
        }
    }

    public void pause() {
        reproductor.pause();
    }

    public void stop() {
        reproductor.stop();
    }
    
    public void add() {
        JFileChooser fc = new JFileChooser();
   fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos de Audio", "mp3", "wav"));
        if (fc.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION) {
            String ruta = fc.getSelectedFile().getAbsolutePath();
            // Datos simples para el ejemplo
            Cancion c = new Cancion("Nueva Cancion", "Artista", 0, model.Genero.POP, ruta, "");
            playlist.agregar(c);
            vista.actualizarLista(playlist.getLista());
        }
    }
    
    public void remove(int index) {
        playlist.eliminar(index);
        reproductor.stop();
        vista.actualizarLista(playlist.getLista());
    }
}