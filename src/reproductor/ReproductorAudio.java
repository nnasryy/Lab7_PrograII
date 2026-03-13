/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reproductor;

import interfaces.Reproducible;
import model.Cancion;
import java.io.File;
import javax.swing.JOptionPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.embed.swing.JFXPanel;

public class ReproductorAudio extends Reproductor {
    private MediaPlayer mediaPlayer;

    private static boolean javaFXIniciado = false;

    public ReproductorAudio() {
        iniciarJavaFX();
    }

    private synchronized void iniciarJavaFX() {
        if (!javaFXIniciado) {
            try {
                new JFXPanel(); 
                javaFXIniciado = true;
            } catch (Exception e) {
                System.err.println("Error iniciando JavaFX: " + e.getMessage());
            }
        }
    }

    @Override
    public void play() {
        if (actual == null) {
            System.out.println("No hay canción seleccionada.");
            return;
        }
        
        try {
     
            if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                mediaPlayer.play();
                return;
            }


            stop();

            File file = new File(actual.getRutaCancion());
            if (!file.exists()) {
                JOptionPane.showMessageDialog(null, "Archivo no encontrado: " + actual.getRutaCancion());
                return;
            }

            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al reproducir MP3.\n¿Instalaste JavaFX?\nError: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
    }
}