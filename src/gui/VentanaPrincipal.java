/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import controlador.Controlador;
import model.Cancion;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {
    private JList<String> lista;
    private DefaultListModel<String> modelo;
    private JLabel lblImagen;
    private Controlador controlador;

    public VentanaPrincipal() {
        setTitle("Reproductor Novato");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        iniciarComponentes();
    }

    public void setControlador(Controlador c) {
        this.controlador = c;
    }

    private void iniciarComponentes() {
  
        modelo = new DefaultListModel<>();
        lista = new JList<>(modelo);
        JScrollPane scroll = new JScrollPane(lista);
        add(scroll, BorderLayout.WEST);

        lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(200, 200));
        lblImagen.setBorder(new LineBorder(Color.BLACK));
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblImagen, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        JButton btnPlay = new JButton("Play");
        JButton btnPause = new JButton("Pause");
        JButton btnStop = new JButton("Stop");
        JButton btnAdd = new JButton("Add");
        JButton btnRemove = new JButton("Remove");

        panelBotones.add(btnPlay);
        panelBotones.add(btnPause);
        panelBotones.add(btnStop);
        panelBotones.add(btnAdd);
        panelBotones.add(btnRemove);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos (LLaman al controlador)
        btnPlay.addActionListener(e -> controlador.play(lista.getSelectedIndex()));
        btnPause.addActionListener(e -> controlador.pause());
        btnStop.addActionListener(e -> controlador.stop());
        btnAdd.addActionListener(e -> controlador.add());
        btnRemove.addActionListener(e -> controlador.remove(lista.getSelectedIndex()));
    }

    // Métodos para que el controlador actualice la vista
    public void actualizarLista(ArrayList<Cancion> canciones) {
        modelo.clear();
        for (Cancion c : canciones) {
            modelo.addElement(c.toString());
        }
    }

    public void setImagen(String ruta) {
        if (ruta == null || ruta.isEmpty()) {
            lblImagen.setIcon(null);
            lblImagen.setText("Sin Imagen");
        } else {
            ImageIcon icon = new ImageIcon(ruta);
            if (icon.getIconWidth() > 0) {
                lblImagen.setIcon(icon);
            } else {
                lblImagen.setIcon(null);
                lblImagen.setText("No Image");
            }
        }
    }
}