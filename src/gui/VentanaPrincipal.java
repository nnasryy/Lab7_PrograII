package gui;

import controlador.Controlador;
import model.Cancion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {
    private JList<String> lista;
    private DefaultListModel<String> modelo;
    private JLabel lblImagen;
    private JLabel lblInfo; 
    private Controlador controlador;

    // Paleta de Colores Y2K
    private Color fondoBase = new Color(15, 15, 25);
    private Color neonRosa = new Color(255, 0, 127);
    private Color neonAzul = new Color(0, 255, 255);
    private Color purpuraGris = new Color(40, 40, 60);

    public VentanaPrincipal() {
        setTitle("☆ TUNE PLAYER Y2K ☆");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(fondoBase);
        iniciarComponentes();
    }

    public void setControlador(Controlador c) {
        this.controlador = c;
    }

    private void iniciarComponentes() {
        setLayout(new BorderLayout(15, 15));
        ((JPanel)getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- LISTA DE REPRODUCCIÓN (IZQUIERDA) ---
        modelo = new DefaultListModel<>();
        lista = new JList<>(modelo);
        lista.setBackground(purpuraGris);
        lista.setForeground(neonAzul);
        lista.setSelectionBackground(neonRosa);
        lista.setSelectionForeground(Color.WHITE);
        lista.setFont(new Font("Monospaced", Font.BOLD, 13));
        
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(new LineBorder(neonAzul, 2));
        scroll.setPreferredSize(new Dimension(220, 0));
        add(scroll, BorderLayout.WEST);

        // --- PANEL CENTRAL (VISUALIZER / IMAGEN) ---
        JPanel panelCentro = new JPanel(new BorderLayout(10, 10));
        panelCentro.setOpaque(false);
        
        lblImagen = new JLabel("NO TRACK", SwingConstants.CENTER);
        lblImagen.setPreferredSize(new Dimension(250, 250));
        lblImagen.setBorder(new LineBorder(neonRosa, 3));
        lblImagen.setForeground(neonRosa);
        lblImagen.setFont(new Font("SansSerif", Font.ITALIC, 24));
        
        lblInfo = new JLabel("READY TO VIBE", SwingConstants.CENTER);
        lblInfo.setForeground(Color.WHITE);
        lblInfo.setFont(new Font("Dialog", Font.BOLD, 14));
        
        panelCentro.add(lblImagen, BorderLayout.CENTER);
        panelCentro.add(lblInfo, BorderLayout.SOUTH);
        
        add(panelCentro, BorderLayout.CENTER);

        // --- PANEL DE BOTONES (SUR) ---
        // Usamos un GridLayout de 2 filas para que Add/Select/Remove queden arriba de Play/Pause/Stop
        JPanel panelControlPadre = new JPanel(new GridLayout(2, 1, 10, 10));
        panelControlPadre.setOpaque(false);

        JPanel fila1 = new JPanel(new GridLayout(1, 3, 10, 10));
        fila1.setOpaque(false);
        JButton btnAdd = estiloBotonY2K("ADD", Color.GREEN);
        JButton btnSelect = estiloBotonY2K("SELECT", neonAzul);
        JButton btnRemove = estiloBotonY2K("REMOVE", Color.RED);
        fila1.add(btnAdd);
        fila1.add(btnSelect);
        fila1.add(btnRemove);

        JPanel fila2 = new JPanel(new GridLayout(1, 3, 10, 10));
        fila2.setOpaque(false);
        JButton btnPlay = estiloBotonY2K("PLAY", neonRosa);
        JButton btnPause = estiloBotonY2K("PAUSE", Color.YELLOW);
        JButton btnStop = estiloBotonY2K("STOP", Color.WHITE);
        fila2.add(btnPlay);
        fila2.add(btnPause);
        fila2.add(btnStop);

        panelControlPadre.add(fila1);
        panelControlPadre.add(fila2);
        add(panelControlPadre, BorderLayout.SOUTH);

        // --- ASIGNACIÓN DE EVENTOS ---
        btnPlay.addActionListener(e -> controlador.accionPlay(lista.getSelectedIndex()));
        btnPause.addActionListener(e -> controlador.accionPause());
        btnStop.addActionListener(e -> controlador.accionStop());
        btnAdd.addActionListener(e -> controlador.accionAgregar());
        btnSelect.addActionListener(e -> controlador.accionSeleccionar(lista.getSelectedIndex()));
        btnRemove.addActionListener(e -> controlador.accionEliminar(lista.getSelectedIndex()));
    }

    // Método para aplicar el estilo visual a los botones
    private JButton estiloBotonY2K(String texto, Color colorNeon) {
        JButton btn = new JButton(texto);
        btn.setBackground(fondoBase);
        btn.setForeground(colorNeon);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBorder(new LineBorder(colorNeon, 2));
        
        // Efecto Hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(colorNeon);
                btn.setForeground(Color.BLACK);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(fondoBase);
                btn.setForeground(colorNeon);
            }
        });
        return btn;
    }

    public void actualizarLista(ArrayList<Cancion> canciones) {
        modelo.clear();
        for (Cancion c : canciones) {
            modelo.addElement("♫ " + c.getNombre());
        }
    }

    public void mostrarInfoCancion(Cancion c) {
        lblInfo.setText(c.getNombre() + " - " + c.getArtista() + " [" + c.getGenero() + "]");
        
        if (c.getRutaImagen() != null && !c.getRutaImagen().isEmpty()) {
            ImageIcon icon = new ImageIcon(c.getRutaImagen());
            Image img = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
            lblImagen.setText("");
        } else {
            lblImagen.setIcon(null);
            lblImagen.setText("NO ART");
        }
    }

    public void limpiarInfo() {
        lblInfo.setText("STOPPED");
        lblImagen.setIcon(null);
        lblImagen.setText(" ☆");
    }
}