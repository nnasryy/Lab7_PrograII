package controlador;

import gui.VentanaPrincipal;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.Cancion;
import model.Genero;
import reproductor.ReproductorAudio;
import servicio.Playlist;

public class Controlador {
    private VentanaPrincipal vista;
    private Playlist playlist;
    private ReproductorAudio reproductor;

    public Controlador(VentanaPrincipal vista, Playlist playlist, ReproductorAudio reproductor) {
        this.vista = vista;
        this.playlist = playlist;
        this.reproductor = reproductor;
    }

    public void iniciar() {
        playlist.cargar();
        vista.actualizarLista(playlist.getLista());
        vista.setVisible(true);
    }

    public void accionSeleccionar(int index) {
        if (index < 0) {
            JOptionPane.showMessageDialog(vista, "Selecciona una canción de la lista.");
            return;
        }
        Cancion c = playlist.get(index);
        vista.mostrarInfoCancion(c); // Muestra la imagen y datos
        reproductor.setCancion(c);   // Prepara la canción
        // No reproduce automáticamente, solo la selecciona
    }

    public void accionPlay(int index) {
        if (index < 0) {
            JOptionPane.showMessageDialog(vista, "Selecciona una canción de la lista.");
            return;
        }
        
        // Si no está cargada o es distinta, la cargamos
        Cancion c = playlist.get(index);
        if (c != null) {
            if (reproductor.getActual() == null || !reproductor.getActual().equals(c)) {
                reproductor.stop();
                reproductor.setCancion(c);
            }
            reproductor.play();
            vista.mostrarInfoCancion(c);
        }
    }

    public void accionPause() {
        reproductor.pause();
    }

    public void accionStop() {
        reproductor.stop();
        vista.limpiarInfo();
    }

    public void accionAgregar() {
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Audio (*.mp3, *.wav)", "mp3", "wav"));
        
        if (fc.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            
            // SOLICITUD DE DATOS PASO A PASO
            
            // 1. Nombre de la canción
            String nombre = JOptionPane.showInputDialog(vista, "Nombre de la canción:", f.getName().replace(".mp3", ""));
            if (nombre == null || nombre.isEmpty()) return; // Canceló
            
            // 2. Artista
            String artista = JOptionPane.showInputDialog(vista, "Artista:", "Desconocido");
            if (artista == null) return;
            
            // 3. Duración
            String durStr = JOptionPane.showInputDialog(vista, "Duración (segundos):", "180");
            int dur = 180;
            try { 
                dur = Integer.parseInt(durStr); 
            } catch (Exception e) { 
                dur = 180; 
            }
            
            // 4. Género musical (Usando el Enum)
            Object[] generos = Genero.values();
            Object sel = JOptionPane.showInputDialog(vista, "Seleccione Género Musical:", "Género", JOptionPane.QUESTION_MESSAGE, null, generos, generos[0]);
            if (sel == null) return;
            Genero gen = (Genero) sel;
            
            // 5. Imagen asociada
            String rutaImg = "";
            int opt = JOptionPane.showConfirmDialog(vista, "¿Desea agregar una imagen de portada?", "Imagen", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                JFileChooser fcImg = new JFileChooser();
                fcImg.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes (*.jpg, *.png)", "jpg", "png", "jpeg"));
                if (fcImg.showOpenDialog(vista) == JFileChooser.APPROVE_OPTION) {
                    rutaImg = fcImg.getSelectedFile().getAbsolutePath();
                }
            }
            
            // Guardar
            Cancion nueva = new Cancion(nombre, artista, dur, gen, f.getAbsolutePath(), rutaImg);
            playlist.agregar(nueva);
            vista.actualizarLista(playlist.getLista());
        }
    }

    public void accionEliminar(int index) {
        if (index < 0) {
            JOptionPane.showMessageDialog(vista, "Selecciona una canción para eliminar.");
            return;
        }
        
        Cancion c = playlist.get(index);
        // Si la canción que se reproduce es eliminada, detener reproducción
        if (reproductor.getActual() != null && reproductor.getActual().equals(c)) {
            reproductor.stop();
        }
        
        playlist.eliminar(index);
        vista.actualizarLista(playlist.getLista());
        vista.limpiarInfo();
    }
}