package gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.io.File;

public class DialogoVerImagenes extends JDialog {
    private JList<String> listaArchivos;
    private JLabel visorImagen;
    private final String RUTA_CARPETA = "misimagenes";

    public DialogoVerImagenes(Frame padre) {
        super(padre, "Visor de Imágenes", true);
        setSize(600, 400); // Un poco más ancho
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());

        // 1. Cargar archivos
        DefaultListModel<String> modelo = new DefaultListModel<>();
        File carpeta = new File(RUTA_CARPETA);
        if (carpeta.exists()) {
            for (File f : carpeta.listFiles()) {
                if (f.isFile()) modelo.addElement(f.getName());
            }
        }

        // 2. Configurar Lista con ancho fijo y Scroll
        listaArchivos = new JList<>(modelo);
        listaArchivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaArchivos.setFixedCellWidth(180); // <--- ESTO EVITA QUE SE COMA EL ESPACIO
        listaArchivos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cambiarImagen();
        });

        JScrollPane scrollLista = new JScrollPane(listaArchivos);
        scrollLista.setPreferredSize(new Dimension(200, 0)); // Ancho inicial sugerido

        // 3. Configurar Visor de Imagen
        visorImagen = new JLabel("Seleccione una imagen", SwingConstants.CENTER);
        visorImagen.setBorder(BorderFactory.createTitledBorder("Vista Previa (150x150)"));
        // Forzamos un tamaño para que el layout no lo colapse
        visorImagen.setMinimumSize(new Dimension(200, 200));

        // 4. Panel contenedor para la imagen (para que no se estire feo)
        JPanel panelImagen = new JPanel(new GridBagLayout());
        panelImagen.add(visorImagen);

        // 5. Ensamblar usando un SplitPane (Divisor ajustable)
        JSplitPane divisor = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollLista, panelImagen);
        divisor.setDividerLocation(200); // Posición inicial de la barrita

        add(divisor, BorderLayout.CENTER);
    }

    private void cambiarImagen() {
        String nombre = listaArchivos.getSelectedValue();
        if (nombre != null) {
            File archivo = new File(RUTA_CARPETA, nombre);
            if (archivo.exists()) {
                ImageIcon iconoOriginal = new ImageIcon(archivo.getAbsolutePath());
                // Escalamos la imagen para que siempre quepa en el visor
                Image imgEscalada = iconoOriginal.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                visorImagen.setIcon(new ImageIcon(imgEscalada));
                visorImagen.setText("");
                // Tooltip para ver el nombre completo si es muy largo
                visorImagen.setToolTipText(nombre);
            }
        }
    }
}