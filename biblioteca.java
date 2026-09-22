import java.util.ArrayList;

public class biblioteca {
    private ArrayList<libro> libros ;

    public biblioteca() {
        libros = new ArrayList<>();
    }

    public boolean agregarLibro(libro libro) {
        for (libro libroExistente : libros) {
            if (libroExistente.getIsbn().equals(libro.getIsbn())) {
                return false;
            }
        }
        libros.add(libro);
        return true;
    }

    public ArrayList<libro> obtenerlibros(){
        return libros;
    }

    public  ArrayList<libro> busquetaPorAutor(String autor){
        ArrayList<libro>  resultado = new ArrayList<>();
        for (libro libro : libros){
            if (libro.getAutor().equalsIgnoreCase(autor)){
                resultado.add(libro);
            }
        }
        return resultado;
    }
}
