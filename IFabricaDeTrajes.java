
public interface IFabricaDeTrajes {
    
    void escribirMenu();
    void añadirComponenteAAlmacen();
    void listarComponentes();
    void añadirTrajeAAlmacen();
    void listarTrajes();
    void crearEnvio();
    void mostrarCantidadPantalonesFaldas(); 
    void activarDesactivarRebajas(String tipoComponente, double porcentaje);} 
