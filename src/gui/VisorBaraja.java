package gui;

import dato.Palo;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @author  Vinni
 * git config --global user.name "Vinni"
 * git config --global user.email "Vinni9gmail.com"
 */
public class VisorBaraja extends JFrame {

    private JTextField campoNumero;
    private JComboBox<Palo> comboPalos;
    private JLabel etiquetaImagen;

    private final String RUTA_REVERSO = "cartas/cardBack_blue.png";

    public VisorBaraja() {
        configurarApariencia();
        configurarVentana();
        mostrarReverso();
    }

    private void configurarApariencia() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurarVentana() {
        setTitle("Visor de Cartas Internacional");
        setSize(450, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        // Panel de Controles
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        panelControles.add(new JLabel("Carta (A, 2-10, J, Q, K):"));
        campoNumero = new JTextField(4);
        panelControles.add(campoNumero);

        panelControles.add(new JLabel("Palo:"));
        comboPalos = new JComboBox<>(Palo.values());
        panelControles.add(comboPalos);

        JButton botonMostrar = new JButton("Mostrar Carta");
        panelControles.add(botonMostrar);

        add(panelControles, BorderLayout.NORTH);

        // Área de Imagen con Scroll por si la imagen es muy grande
        etiquetaImagen = new JLabel("Introduce datos y pulsa Mostrar", SwingConstants.CENTER);
        etiquetaImagen.setFont(new Font("Arial", Font.ITALIC, 14));
        etiquetaImagen.setBorder(BorderFactory.createTitledBorder("Vista Previa"));
        add(new JScrollPane(etiquetaImagen), BorderLayout.CENTER);

        // Evento de acción
        botonMostrar.addActionListener(e -> cargarImagen());

        setLocationRelativeTo(null);
    }

    private void cargarImagen() {
        String valor = campoNumero.getText().trim().toUpperCase();
        Palo palo = (Palo) comboPalos.getSelectedItem();

        if (valor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, escribe un número o letra (A, J, Q, K).");
            return;
        }

        // Formato sugerido: "CORAZONES_A.jpg", "PICAS_10.jpg", etc.
        String nombreArchivo = "card" + palo.getPrefijo() + valor + ".png";
        String ruta = "cartas/" + nombreArchivo;

        File archivo = new File(ruta);
        if (archivo.exists()) {
            ImageIcon icono = new ImageIcon(ruta);
            Image imgEscalada = icono.getImage().getScaledInstance(320, 480, Image.SCALE_SMOOTH);
            etiquetaImagen.setIcon(new ImageIcon(imgEscalada));
            etiquetaImagen.setText("");
        } else {
            etiquetaImagen.setIcon(null);
            etiquetaImagen.setText("Imagen no encontrada");
            JOptionPane.showMessageDialog(this, "No existe el archivo: " + nombreArchivo,
                    "Error", JOptionPane.WARNING_MESSAGE);
        }

    }
    private void mostrarReverso() {
        File archivo = new File(RUTA_REVERSO);
        if (archivo.exists()) {
            ajustarImagen(RUTA_REVERSO);
        } else {
            etiquetaImagen.setText("Reverso no encontrado (" + RUTA_REVERSO + ")");
        }
    }
    private void ajustarImagen(String ruta) {
        ImageIcon icono = new ImageIcon(ruta);
        Image imgEscalada = icono.getImage().getScaledInstance(320, 480, Image.SCALE_SMOOTH);
        etiquetaImagen.setIcon(new ImageIcon(imgEscalada));
        etiquetaImagen.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VisorBaraja().setVisible(true));
    }
}