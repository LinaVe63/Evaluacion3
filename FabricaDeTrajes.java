
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class FabricaDeTrajes implements IFabricaDeTrajes {

    private ArrayList<Componente> componentesEnAlmacen;
    private TreeSet<Traje> trajesEnAlmacen;
    private boolean sonRebajas;

    public FabricaDeTrajes() {
        this.componentesEnAlmacen = new ArrayList<>();
        this.trajesEnAlmacen = new TreeSet<>((t1, t2) -> t1.getNombre().compareTo(t2.getNombre()));
        this.sonRebajas = false;
    }

    @Override
    public void escribirMenu() {
        System.out.println("Menú de la Fábrica de Trajes:");
        System.out.println("1. Añadir Componente a Almacén");
        System.out.println("2. Listar Componentes");
        System.out.println("3. Añadir Traje a Almacén");
        System.out.println("4. Listar Trajes");
        System.out.println("5. Crear Envío");
        System.out.println("6. Ver cantidad de Faldas y Pantalones");
        System.out.println("7. Activar/Desactivar Rebajas");
        System.out.println("8. Salir");
    }

    @Override
    public void añadirComponenteAAlmacen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite el NUMERO del componente a añadir:");
        System.out.println("1. Falda");
        System.out.println("2. Blusa");
        System.out.println("3. Pantalón");
        System.out.println("4. Chaqueta");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        try {
            System.out.print("Ingrese el numero del ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            // Validar si el ID ya existe
            for (Componente c : componentesEnAlmacen) {
                if (c.getId() == id) {
                    throw new IdException("El id del componente ya existe");
                }
            }

            System.out.print("Ingrese el nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese la talla: ");
            String talla = scanner.nextLine();
            System.out.print("Ingrese el color: ");
            String color = scanner.nextLine();
            System.out.print("¿Es comunitario? (si/no): ");
            boolean esComunitario = leerBoolean(scanner);
            System.out.print("Ingrese el precio base: ");
            double precio = scanner.nextDouble();

            // Validar porcentaje de componentes extracomunitarios
            int countExtracomunitarios = 0;
            for (Componente c : componentesEnAlmacen) {
                if (!c.isEsComunitario()) {
                    countExtracomunitarios++;
                }
            }
            if (!esComunitario && countExtracomunitarios >= componentesEnAlmacen.size() / 2) {
                throw new MuchoExtracomunitarioException("ya hay más del 50% de componentes extracomunitarios en el almacén");
            }

            switch (opcion) {

                //Falda
                case 1 -> {
                    System.out.print("¿Tiene cremallera? (si/no): ");
                    boolean conCremalleraFalda = leerBoolean(scanner);
                    if (conCremalleraFalda) {
                        precio += 1;
                    }
                    componentesEnAlmacen.add(new Falda(id, nombre, talla, color, esComunitario, precio, conCremalleraFalda));
                }

                //Blusa
                case 2 -> {
                    System.out.print("¿Tiene mangas largas? (si/no): ");
                    boolean mangaLarga = leerBoolean(scanner);
                    for (Componente c : componentesEnAlmacen) {
                        if (c.getClass() == Blusa.class && c.getColor().equalsIgnoreCase(color) && ((Blusa) c).isMangaLarga() != mangaLarga) {
                            throw new MangaException("ya existe en el almacén una blusa de ese color con otro tipo de manga.");
                        }
                    }
                    componentesEnAlmacen.add(new Blusa(id, nombre, talla, color, esComunitario, precio, mangaLarga));
                }

                //Pantalon
                case 3 -> {
                    System.out.print("¿Tiene cremallera? (si/no): ");
                    boolean conCremalleraPantalon = leerBoolean(scanner);
                    if (conCremalleraPantalon) {
                        precio += 1;
                    }
                    componentesEnAlmacen.add(new Pantalon(id, nombre, talla, color, esComunitario, precio, conCremalleraPantalon));
                }

                //Chaqueta
                case 4 -> {
                    System.out.print("Ingrese el número de botones: ");
                    int numBotones = scanner.nextInt();
                    precio += numBotones * 2;
                    componentesEnAlmacen.add(new Chaqueta(id, nombre, talla, color, esComunitario, precio, numBotones));
                }

                default -> {
                    System.out.println("Opción no válida.");
                    return;
                }
            }

            System.out.println("Componente añadido exitosamente.");

        } catch (IdException | MuchoExtracomunitarioException | MangaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void listarComponentes() {
        if (componentesEnAlmacen.isEmpty()) {
            System.out.println("No hay componentes en el almacén.");
        } else {
            for (Componente c : componentesEnAlmacen) {
                System.out.println(c);
            }
        }
    }

    @Override
    public void añadirTrajeAAlmacen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Creación de un nuevo traje:");

        // Listar blusas existentes
        System.out.println("Lista de Blusas existentes:");
        ArrayList<Blusa> blusasDisponibles = new ArrayList<>();
        for (Componente c : componentesEnAlmacen) {
            if (c.getClass() == Blusa.class) {
                blusasDisponibles.add((Blusa) c);
                System.out.println(c);
            }
        }
        System.out.print("Elige una blusa por su ID: ");
        int idBlusaElegida = scanner.nextInt();
        Blusa blusaElegida = blusasDisponibles.stream().filter(b -> b.getId() == idBlusaElegida).findFirst().orElse(null);
        if (blusaElegida == null) {
            System.out.println("ID de blusa no válido.");
            return;
        }
        componentesEnAlmacen.remove(blusaElegida);

        // Listar chaquetas existentes
        System.out.println("Lista de Chaquetas existentes:");
        ArrayList<Chaqueta> chaquetasDisponibles = new ArrayList<>();
        for (Componente c : componentesEnAlmacen) {
            if (c.getClass() == Chaqueta.class) {
                chaquetasDisponibles.add((Chaqueta) c);
                System.out.println(c);
            }
        }
        System.out.print("Elige una chaqueta por su ID: ");
        int idChaquetaElegida = scanner.nextInt();
        Chaqueta chaquetaElegida = chaquetasDisponibles.stream().filter(c -> c.getId() == idChaquetaElegida).findFirst().orElse(null);
        if (chaquetaElegida == null) {
            System.out.println("ID de chaqueta no válido.");
            componentesEnAlmacen.add(blusaElegida);
            return;
        }
        componentesEnAlmacen.remove(chaquetaElegida);

        // Listar faldas y pantalones existentes
        System.out.println("Lista de Faldas y Pantalones existentes con su ID respectivo:");
        ArrayList<Componente> faldasPantalonesDisponibles = new ArrayList<>();
        for (Componente c : componentesEnAlmacen) {
            if (c instanceof Falda || c instanceof Pantalon) {
                faldasPantalonesDisponibles.add(c);
                System.out.println(c);
            }
        }
        System.out.print("Elige una falda o pantalón por su ID: ");
        int idFaldaPantalonElegido = scanner.nextInt();
        Componente faldaPantalonElegido = faldasPantalonesDisponibles.stream().filter(c -> c.getId() == idFaldaPantalonElegido).findFirst().orElse(null);
        if (faldaPantalonElegido == null) {
            System.out.println("ID de falda/pantalón no válido.");
            componentesEnAlmacen.add(blusaElegida);
            componentesEnAlmacen.add(chaquetaElegida);
            return;
        }
        componentesEnAlmacen.remove(faldaPantalonElegido);

        // Verificar reglas del traje
        try {
            if (!blusaElegida.getColor().substring(0, 1).equalsIgnoreCase(faldaPantalonElegido.getColor().substring(0, 1))) {
                throw new ColoresException("Las piezas de un traje deben ser del mismo color o colores amigos.");
            }
            if (!blusaElegida.getTalla().equalsIgnoreCase(faldaPantalonElegido.getTalla())) {
                throw new TallaException("Todas las piezas de un traje deben ser de la misma talla, excepto la falda.");
            }

            // Verificar si el nombre del traje ya existe
            String nombreTraje = "Traje" + (trajesEnAlmacen.size() + 1);
            for (Traje t : trajesEnAlmacen) {
                if (t.getNombre().equals(nombreTraje)) {
                    throw new TrajeYaExisteException("El nombre del traje ya existe en el almacén.");
                }
            }

            // Crear el nuevo traje con las piezas seleccionadas
            Traje nuevoTraje = new Traje(nombreTraje);
            nuevoTraje.getPiezas().add(blusaElegida);
            nuevoTraje.getPiezas().add(chaquetaElegida);
            nuevoTraje.getPiezas().add(faldaPantalonElegido);

            // Añadir el nuevo traje al almacén
            trajesEnAlmacen.add(nuevoTraje);
            System.out.println("Traje añadido correctamente al almacén.");
        } catch (ColoresException | TallaException | TrajeYaExisteException e) {
            System.out.println("Error al crear traje: " + e.getMessage());
            // Revertir los cambios añadiendo nuevamente las piezas al almacén de componentes
            componentesEnAlmacen.add(blusaElegida);
            componentesEnAlmacen.add(chaquetaElegida);
            componentesEnAlmacen.add(faldaPantalonElegido);
        }
    }

    public void precioPorComponente() {

        System.out.println("De que componente quiere conocer el precio:");
        ArrayList<Componente> calcularPrecioComponente = new ArrayList<>();
        for (Componente c : componentesEnAlmacen) {
            if (c instanceof Falda || c instanceof Pantalon || c instanceof Chaqueta || c instanceof Blusa) {
                System.out.println("Pantalones: " + c.calcularPrecioComponentePant());
                System.out.println("Falda: " + c.calcularPrecioComponenteFald());
                System.out.println("Chaqueta: " + c.calcularPrecioComponenteChaq());
                System.out.println("Blusa: " + c.calcularPrecioComponenteBlusa());
            }
        }

    }

    public void valorAcumuladoTrajes() {
        if (trajesEnAlmacen.isEmpty()) {
            System.out.println("No hay trajes en el almacén.");
        } else {
            for (Traje t : trajesEnAlmacen) {
                System.out.println(t);
                System.out.println("Precio total de los trajes: $" + t.calcularPrecioTotal());
            }
        }
    }

    @Override
    public void listarTrajes() {
        if (trajesEnAlmacen.isEmpty()) {
            System.out.println("No hay trajes en el almacén.");
        } else {
            for (Traje t : trajesEnAlmacen) {
                System.out.println(t);
}            }
        }
    }

    @Override
    public void activarDesactivarRebajas(String tipoComponente, double porcentaje) {
        if (sonRebajas) {
            for (Componente c : componentesEnAlmacen) {
                if (tipoComponente.equalsIgnoreCase("Falda") && c instanceof Falda) {
                    c.setPrecio(c.getPrecio() / (1 - porcentaje / 100));
                } else if (tipoComponente.equalsIgnoreCase("Blusa") && c instanceof Blusa) {
                    c.setPrecio(c.getPrecio() / (1 - porcentaje / 100));
                } else if (tipoComponente.equalsIgnoreCase("Pantalon") && c instanceof Pantalon) {
                    c.setPrecio(c.getPrecio() / (1 - porcentaje / 100));
                } else if (tipoComponente.equalsIgnoreCase("Chaqueta") && c instanceof Chaqueta) {
                    c.setPrecio(c.getPrecio() / (1 - porcentaje / 100));
                }
            }
            System.out.println("Rebajas desactivadas para " + tipoComponente + ".");
        } else {
            for (Componente c : componentesEnAlmacen) {
                if (tipoComponente.equalsIgnoreCase("Falda") && c instanceof Falda) {
                    c.setPrecio(c.getPrecio() * (1 - porcentaje / 100));
                } else if (tipoComponente.equalsIgnoreCase("Blusa") && c instanceof Blusa) {
                    c.setPrecio(c.getPrecio() * (1 - porcentaje / 100));
                } else if (tipoComponente.equalsIgnoreCase("Pantalon") && c instanceof Pantalon) {
                    c.setPrecio(c.getPrecio() * (1 - porcentaje / 100));
                } else if (tipoComponente.equalsIgnoreCase("Chaqueta") && c instanceof Chaqueta) {
                    c.setPrecio(c.getPrecio() * (1 - porcentaje / 100));
                }
            }
            System.out.println("Rebajas activadas para " + tipoComponente + ".");
        }
        sonRebajas = !sonRebajas;
    }


    @Override
    public void crearEnvio() {
        System.out.println("Creación de un nuevo envío:");
        ArrayList<Traje> crearEnvio = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Listado de trajes existentes:");
            for (Traje t : trajesEnAlmacen) {
                System.out.println(t.getNombre());
            }

            System.out.print("Elige un traje por su nombre (o escribe 'fin' para salir): ");
            String nombreTraje = scanner.nextLine();
            if (nombreTraje.equalsIgnoreCase("fin")) {
                break;
            }

            Traje trajeSeleccionado = trajesEnAlmacen.stream().filter(t -> t.getNombre().equalsIgnoreCase(nombreTraje)).findFirst().orElse(null);
            if (trajeSeleccionado != null) {
                crearEnvio.add(trajeSeleccionado);
                trajesEnAlmacen.remove(trajeSeleccionado);
                System.out.println("Traje añadido al envío.");
            } else {
                System.out.println("Nombre de traje no válido.");
            }
        }
    }

    private boolean leerBoolean(Scanner scanner) {
        String input = scanner.nextLine().trim().toLowerCase();
        switch (input) {
            case "si" -> {
                return true;
            }
            case "no" -> {
                return false;
            }
            default -> {
                System.out.println("Por favor, ingrese 'si' o 'no'.");
                return leerBoolean(scanner);
            }
        }
    }

    @Override
    public void mostrarCantidadPantalonesFaldas() {
        int cantidadPantalones = 0;
        int cantidadFaldas = 0;

        System.out.println("Cantidad de Faldas y Pantalones existentes:");
        ArrayList<Componente> faldasPantalonesExistentes = new ArrayList<>();
        for (Componente c : componentesEnAlmacen) {
            if (c instanceof Falda || c instanceof Pantalon) {
            }

            System.out.println("Pantalones: " + cantidadPantalones);
            System.out.println("Faldas: " + cantidadFaldas);
        }

    }
}
