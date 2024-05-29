
import java.util.Objects;

public abstract class Componente implements Comparable<Componente> {

    protected int id;
    protected String nombre;
    protected String talla;
    protected String color;
    protected boolean esComunitario;
    protected double precio;

    public Componente(int id, String nombre, String talla, String color, boolean esComunitario, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.talla = talla;
        this.color = color;
        this.esComunitario = esComunitario;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isEsComunitario() {
        return esComunitario;
    }

    public void setEsComunitario(boolean esComunitario) {
        this.esComunitario = esComunitario;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Componente [id=" + id + ", nombre=" + nombre + ", talla=" + talla + ", color=" + color
                + ", esComunitario=" + esComunitario + ", precio=" + precio + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Componente other = (Componente) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Componente other) {
        return Integer.compare(this.id, other.id);
    }
}
