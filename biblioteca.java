import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class biblioteca extends JFrame {
    private ArrayList<libro> libros = new ArrayList();
    private HashMap<String, libro> indiceISBN = new HashMap();
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;
    private JTextField campoISBN;
    private JTextField campoTitulo;
    private JTextField campoAutor;
    private JTextField campoAnio;
    private JTextField campoCopias;

    public biblioteca() {
        this.setTitle("Sistema Biblioteca");
        this.setSize(700, 500);
        this.setDefaultCloseOperation(3);
        this.setLayout(new BorderLayout());
        JPanel panelEntrada = new JPanel(new GridLayout(6, 2));
        this.campoISBN = new JTextField();
        this.campoTitulo = new JTextField();
        this.campoAutor = new JTextField();
        this.campoAnio = new JTextField();
        this.campoCopias = new JTextField();
        panelEntrada.add(new JLabel("ISBN:"));
        panelEntrada.add(this.campoISBN);
        panelEntrada.add(new JLabel("Título:"));
        panelEntrada.add(this.campoTitulo);
        panelEntrada.add(new JLabel("Autor:"));
        panelEntrada.add(this.campoAutor);
        panelEntrada.add(new JLabel("Año:"));
        panelEntrada.add(this.campoAnio);
        panelEntrada.add(new JLabel("Copias:"));
        panelEntrada.add(this.campoCopias);
        JButton btnAgregar = new JButton("Agregar Libro");
        panelEntrada.add(btnAgregar);
        this.add(panelEntrada, "North");
        String[] columnas = new String[]{"ISBN", "Título", "Autor", "Año", "Copias", "Disponible"};
        this.modeloTabla = new DefaultTableModel(columnas, 0);
        this.tablaLibros = new JTable(this.modeloTabla);
        this.add(new JScrollPane(this.tablaLibros), "Center");
        JPanel panelAcciones = new JPanel();
        JButton btnPrestar = new JButton("Prestar");
        JButton btnDevolver = new JButton("Devolver");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnFiltrar = new JButton("Filtrar por Autor");
        JButton btnMostrarTodos = new JButton("Mostrar Todos");
        panelAcciones.add(btnPrestar);
        panelAcciones.add(btnDevolver);
        panelAcciones.add(btnEliminar);
        panelAcciones.add(btnFiltrar);
        panelAcciones.add(btnMostrarTodos);
        this.add(panelAcciones, "South");
        btnAgregar.addActionListener((e) -> this.agregarLibro());
        btnPrestar.addActionListener((e) -> this.prestarLibro());
        btnDevolver.addActionListener((e) -> this.devolverLibro());
        btnEliminar.addActionListener((e) -> this.eliminarLibro());
        btnFiltrar.addActionListener((e) -> this.filtrarPorAutor());
        btnMostrarTodos.addActionListener((e) -> this.actualizarTabla(this.libros));
    }

    private void agregarLibro() {
        String isbn = this.campoISBN.getText().trim();
        String titulo = this.campoTitulo.getText().trim();
        String autor = this.campoAutor.getText().trim();
        String anioStr = this.campoAnio.getText().trim();
        String copiasStr = this.campoCopias.getText().trim();
        if (!isbn.isEmpty() && !titulo.isEmpty() && !autor.isEmpty() && !anioStr.isEmpty() && !copiasStr.isEmpty()) {
            if (this.indiceISBN.containsKey(isbn)) {
                JOptionPane.showMessageDialog(this, "Ya existe un libro con ese ISBN.");
            } else {
                try {
                    int anio = Integer.parseInt(anioStr);
                    int copias = Integer.parseInt(copiasStr);
                    libro libro = new libro(isbn, titulo, autor, anio, copias);
                    this.libros.add(libro);
                    this.indiceISBN.put(isbn, libro);
                    this.actualizarTabla(this.libros);
                    this.campoISBN.setText("");
                    this.campoTitulo.setText("");
                    this.campoAutor.setText("");
                    this.campoAnio.setText("");
                    this.campoCopias.setText("");
                } catch (NumberFormatException var9) {
                    JOptionPane.showMessageDialog(this, "Año y copias deben ser números.");
                }

            }
        } else {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
        }
    }

    private void prestarLibro() {
        int fila = this.tablaLibros.getSelectedRow();
        if (fila >= 0) {
            String isbn = (String)this.modeloTabla.getValueAt(fila, 0);
            libro libro = (libro)this.indiceISBN.get(isbn);
            if (libro != null) {
                libro.prestar();
                this.actualizarTabla(this.libros);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un libro en la tabla.");
        }

    }

    private void devolverLibro() {
        int fila = this.tablaLibros.getSelectedRow();
        if (fila >= 0) {
            String isbn = (String)this.modeloTabla.getValueAt(fila, 0);
            libro libro = (libro)this.indiceISBN.get(isbn);
            if (libro != null) {
                libro.devolver();
                this.actualizarTabla(this.libros);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un libro en la tabla.");
        }

    }

    private void eliminarLibro() {
        int fila = this.tablaLibros.getSelectedRow();
        if (fila >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas eliminar este libro?", "Confirmar", 0);
            if (confirm == 0) {
                String isbn = (String)this.modeloTabla.getValueAt(fila, 0);
                libro libro = (libro)this.indiceISBN.remove(isbn);
                this.libros.remove(libro);
                this.actualizarTabla(this.libros);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un libro en la tabla.");
        }

    }

    private void filtrarPorAutor() {
        String autor = JOptionPane.showInputDialog("Ingrese el autor a filtrar:");
        if (autor != null && !autor.trim().isEmpty()) {
            ArrayList<libro> filtrados = new ArrayList();

            for(int i = 0; i < this.libros.size(); ++i) {
                if (((libro)this.libros.get(i)).getAutor().equalsIgnoreCase(autor)) {
                    filtrados.add((libro)this.libros.get(i));
                }
            }

            this.actualizarTabla(filtrados);
        }

    }

    private void actualizarTabla(ArrayList<libro> lista) {
        this.modeloTabla.setRowCount(0);

        for(int i = 0; i < lista.size(); ++i) {
            libro l = (libro)lista.get(i);
            this.modeloTabla.addRow(new Object[]{l.getIsbn(), l.getTitulo(), l.getAutor(), l.getAnio(), l.getCopias(), l.isDisponible()});
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> (new biblioteca()).setVisible(true));
    }
}
