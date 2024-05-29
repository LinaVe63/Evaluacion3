
public class Chaqueta extends Componente {
    private int numBotones;

    public Chaqueta(int id, String nombre, String talla, String color, boolean esComunitario, double precio, int numBotones) {
        super(id, nombre, talla, color, esComunitario, precio);
        this.numBotones = numBotones;
    }

    public int getNumBotones() {
        return numBotones;
    }

    public void setNumBotones(int numBotones) {
        this.numBotones = numBotones;
    }

    @Override
    public String toString() {
        return "Chaqueta [numBotones=" + numBotones + ", " + super.toString() + "]";
    }
}
