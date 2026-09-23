import javax.swing.*;

class libro {
    private String isbn;
    private String titulo;
    private String autor;
    private int anio;
    private int copias;
    private int copiasMaximas;

    public libro(String isbn, String titulo, String autor, int anio, int copias) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.copias = copias;
        this.copiasMaximas = copias;
    }

    public String getIsbn() { return isbn; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnio() { return anio; }
    public int getCopias() { return copias; }
    public boolean isDisponible() { return copias > 0; }

    public void prestar() {
        if (copias > 0) {
            copias--;
            JOptionPane.showMessageDialog(null, "Préstamo realizado de: " + titulo);
        } else {
            JOptionPane.showMessageDialog(null, "No hay copias disponibles de: " + titulo);
        }
    }

    public void devolver() {
        if (copias < copiasMaximas) {
            copias++;
            JOptionPane.showMessageDialog(null, "Devolución realizada de: " + titulo);
        } else {
            JOptionPane.showMessageDialog(null, "No puedes devolver más copias de las registradas.");
        }
    }
}
