package servicio;

import model.Cancion;
import model.Genero;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Playlist {
    private ArrayList<Cancion> lista = new ArrayList<>();
    private String archivo = "playlist.dat";

    public void agregar(Cancion c) {
        lista.add(c);
        guardar(c);
    }

    public void eliminar(int index) {
        lista.remove(index);
        reescribirArchivo();
    }

    public Cancion get(int index) {
        return lista.get(index);
    }

    public ArrayList<Cancion> getLista() {
        return lista;
    }

    // RECURSIVIDAD: Buscar canción por nombre
    public int buscarPorNombre(String nombre, int index) {
        if (index >= lista.size()) return -1; // Caso base: no encontrado
        if (lista.get(index).getNombre().equalsIgnoreCase(nombre)) return index; // Caso base: encontrado
        return buscarPorNombre(nombre, index + 1); // Llamada recursiva
    }

    // RANDOMACCESSFILE: Guardar una canción al final
    private void guardar(Cancion c) {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            raf.seek(raf.length()); // Ir al final
            raf.writeUTF(c.getNombre());
            raf.writeUTF(c.getArtista());
            raf.writeInt(c.getDuracion());
            raf.writeUTF(c.getGenero().name());
            raf.writeUTF(c.getRutaCancion());
            raf.writeUTF(c.getRutaImagen());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // RANDOMACCESSFILE: Cargar datos al iniciar
    public void cargar() {
        File f = new File(archivo);
        if (!f.exists()) return;
        
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                String nom = raf.readUTF();
                String art = raf.readUTF();
                int dur = raf.readInt();
                Genero gen = Genero.valueOf(raf.readUTF());
                String rut = raf.readUTF();
                String img = raf.readUTF();
                lista.add(new Cancion(nom, art, dur, gen, rut, img));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Si borramos, hay que reescribir todo el archivo
    private void reescribirArchivo() {
        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {
            raf.setLength(0); // Limpiar archivo
            for (Cancion c : lista) {
                raf.writeUTF(c.getNombre());
                raf.writeUTF(c.getArtista());
                raf.writeInt(c.getDuracion());
                raf.writeUTF(c.getGenero().name());
                raf.writeUTF(c.getRutaCancion());
                raf.writeUTF(c.getRutaImagen());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}