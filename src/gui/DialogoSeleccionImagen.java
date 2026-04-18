package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DialogoSeleccionImagen extends JFrame {
    private int anchoPermitido = 200;
    private int altoPermitido = 220;

    public DialogoSeleccionImagen() {
        setTitle("Gestor de Imágenes");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setLayout(new GridBagLayout()); // Centra los componentes visualmente

        JButton btnSeleccionar = new JButton("Seleccionar Imagen (Max "+anchoPermitido+"x"+altoPermitido+"px )");

        btnSeleccionar.addActionListener(e -> seleccionarYGuardarImagen());

        JButton btnVer = new JButton("Ver Imágenes Guardadas");
        btnVer.addActionListener(e -> {
            DialogoVerImagenes verDialogo = new DialogoVerImagenes(this);
            verDialogo.setVisible(true);
        });

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.add(new JLabel("Formatos aceptados: JPG, PNG", SwingConstants.CENTER));
        panel.add(btnSeleccionar);
        panel.add(btnVer);

        add(panel);
    }
    private void seleccionarYGuardarImagen() {
        JFileChooser selector = new JFileChooser();

        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imágenes (JPG, PNG)", "jpg", "jpeg", "png");
        selector.setFileFilter(filtro);

        int resultado = selector.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = selector.getSelectedFile();

            try {
                BufferedImage img = ImageIO.read(archivoSeleccionado);

                if (img != null) {
                    if (img.getWidth() <= anchoPermitido && img.getHeight() <= altoPermitido) {

                        // Crear carpeta del proyecto
                        File destinoCarpeta = new File("misimagenes");
                        if (!destinoCarpeta.exists()) {
                            destinoCarpeta.mkdirs();
                        }

                        File destinoArchivo = new File(destinoCarpeta, archivoSeleccionado.getName());
                        Files.copy(archivoSeleccionado.toPath(), destinoArchivo.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        JOptionPane.showMessageDialog(this, "¡Imagen guardada con éxito en /misimagenes!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Error: La imagen mide " + img.getWidth() + "x" + img.getHeight()
                                + "px.\nEl máximo permitido es "+anchoPermitido+"x"+altoPermitido+"px", "Tamaño excedido", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "El archivo seleccionado no es una imagen válida.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al procesar el archivo: " + ex.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            new DialogoSeleccionImagen().setVisible(true);
        });
    }
}
