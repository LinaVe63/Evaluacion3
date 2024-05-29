
import java.util.ArrayList;

public class Traje {

    private ArrayList<Componente> piezas;
    private String nombre;

    public Traje(String nombre) {
        this.nombre = nombre;
        this.piezas = new ArrayList<>();
    }

    public ArrayList<Componente> getPiezas() {
        return piezas;
    }

    public void setPiezas(ArrayList<Componente> piezas) {
        this.piezas = piezas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Traje{"
                + "piezas=" + piezas
                + ", nombre='" + nombre + '\''
                + '}';
    }
    
   
    public double valorAcumuladoTrajes() {
        double precioTotal = 0;
        for (Componente t : trajesEnAlmacen) {
            precioTotal += t.getPrecio();
        }
        return precioTotal;
    }
    
    public double calcularPrecioComponente() {
        double precioComponente = 0;
        for (Componente c : componentesEnAlmacen) {
            precioComponenteBlusa += c.calcularPrecioComponenteBlusa());
            precioComponenteFalda += c.calcularPrecioComponenteFald());
            precioComponentePantalon += c.calcularPrecioComponentePant());
            precioComponenteChaqueta += c.calcularPrecioComponenteChaq());
        }
        return precioComponente;
    }
}


