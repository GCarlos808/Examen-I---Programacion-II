    package exameni_labprogii;

    import javax.swing.*;
    import java.util.ArrayList;
    import java.util.Date;
    import java.io.File;
    import com.toedter.calendar.JDateChooser;

    public class ExamenI_LabProgII {

        private static ArrayList<RentItem> items = new ArrayList<>();

        public static void main(String[] args) {
            boolean ejecutando = true;
            while (ejecutando) {
                String[] opciones = {
                    "Agregar Ítem",
                    "Rentar",
                    "Ejecutar Submenú",
                    "Imprimir Todo",
                    "Salir"
                };

                int opcion = JOptionPane.showOptionDialog(
                        null,
                        "SISTEMA DE RENTA MULTIMEDIA\n\nSeleccione una opción:",
                        "Menú Principal",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, opciones, opciones[0]);

                switch (opcion) {
                    case 0: accionAgregar();   break;
                    case 1: accionRentar();    break;
                    case 2: ejecutarSubmenu(); break;
                    case 3: imprimirTodo();    break;
                    case 4:
                    case -1:
                        ejecutando = false;
                        JOptionPane.showMessageDialog(null, "¡Hasta luego!");
                        break;
                }
            }
        }


        private static void accionAgregar() {
            String[] tipos = {"Movie", "Game"};
            String tipo = (String) JOptionPane.showInputDialog(
                    null, "Seleccione el tipo de Ítem:", "Agregar Ítem",
                    JOptionPane.QUESTION_MESSAGE, null, tipos, tipos[0]);
            if (tipo == null) return;

            String code = null;
            do {
                code = JOptionPane.showInputDialog(null, "Ingrese el código del Ítem:");
                if (code == null) return;
                code = code.trim();
                if (code.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El código no puede estar vacío.");
                    code = null;
                    continue;
                }
                if (!isCodeUnique(code)) {
                    JOptionPane.showMessageDialog(null, "El código ya existe. Intente otro.");
                    code = null;
                }
            } while (code == null);

            String name = JOptionPane.showInputDialog(null, "Ingrese el nombre del Ítem:");
            if (name == null || name.trim().isEmpty()) return;

            RentItem nuevo = null;

            if (tipo.equalsIgnoreCase("Movie")) {
                String precioStr = JOptionPane.showInputDialog(null,
                        "Ingrese el precio base de renta (Lps):", "30");
                if (precioStr == null) return;
                double precio;
                try {
                    precio = Double.parseDouble(precioStr.trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Precio inválido. Operación cancelada.");
                    return;
                }

                Movie m = new Movie(code, name.trim(), precio);

                int resp = JOptionPane.showConfirmDialog(null,
                        "¿Desea establecer una fecha de estreno diferente a hoy?",
                        "Fecha de Estreno", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    Date fecha = pedirFechaConPlugin("Seleccione la fecha de estreno");
                    if (fecha != null) {
                        m.setFechaEstrenoDesdeDate(fecha);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "No se seleccionó fecha. Se usará la fecha de hoy.");
                    }
                }
                nuevo = m;

            } else {
                String precioStr = JOptionPane.showInputDialog(null,
                        "Ingrese el precio base de renta (Lps):", "20");
                if (precioStr == null) return;
                double precio;
                try {
                    precio = Double.parseDouble(precioStr.trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Precio inválido. Operación cancelada.");
                    return;
                }

                Game g = new Game(code, name.trim(), precio);

                int resp = JOptionPane.showConfirmDialog(null,
                        "¿Desea establecer una fecha de publicación diferente a hoy?",
                        "Fecha de Publicación", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    Date fecha = pedirFechaConPlugin("Seleccione la fecha de publicación");
                    if (fecha != null) {
                        g.setFechaPublicacionDesdeDate(fecha);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "No se seleccionó fecha. Se usará la fecha de hoy.");
                    }
                }
                nuevo = g;
            }

            int respImg = JOptionPane.showConfirmDialog(null,
                    "¿Desea asociar una imagen al ítem?",
                    "Imagen del Ítem", JOptionPane.YES_NO_OPTION);
            if (respImg == JOptionPane.YES_OPTION) {
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Seleccione una imagen para el ítem");
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    nuevo.setImagePath(chooser.getSelectedFile().getAbsolutePath());
                }
            }

            String copiesStr = JOptionPane.showInputDialog(null,
                    "Ingrese cantidad de copias disponibles:", "1");
            if (copiesStr != null) {
                try {
                    nuevo.setCopies(Math.max(0, Integer.parseInt(copiesStr.trim())));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Cantidad inválida. Se establece en 0.");
                }
            }

            items.add(nuevo);
            JOptionPane.showMessageDialog(null,
                    tipo + " \"" + nuevo.getNombre() + "\" agregado exitosamente.");
        }

        private static void accionRentar() {
            String codigo = JOptionPane.showInputDialog(null, "Ingrese el código del ítem a rentar:");
            if (codigo == null || codigo.trim().isEmpty()) return;

            RentItem item = buscarItem(codigo.trim());
            if (item == null) {
                JOptionPane.showMessageDialog(null, "Item No Existe");
                return;
            }

            JOptionPane.showMessageDialog(null,
                    item.toString(), "Información del Ítem",
                    JOptionPane.INFORMATION_MESSAGE, item.getImagen());

            String diasStr = JOptionPane.showInputDialog(null, "Ingrese la cantidad de días a rentar:");
            if (diasStr == null) return;
            int dias;
            try {
                dias = Integer.parseInt(diasStr.trim());
                if (dias <= 0) {
                    JOptionPane.showMessageDialog(null, "La cantidad de días debe ser mayor a 0.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Valor inválido para días.");
                return;
            }

            JOptionPane.showMessageDialog(null,
                    "Ítem: " + item.getNombre()
                    + "\nDías: " + dias
                    + "\nTotal a pagar: Lps. " + String.format("%.2f", item.pagoRenta(dias)),
                    "Resumen de Renta", JOptionPane.INFORMATION_MESSAGE);
        }


        private static void ejecutarSubmenu() {
            String codigo = JOptionPane.showInputDialog(null, "Ingrese el código del ítem:");
            if (codigo == null || codigo.trim().isEmpty()) return;

            RentItem item = buscarItem(codigo.trim());
            if (item == null) {
                JOptionPane.showMessageDialog(null, "Item No Existe");
                return;
            }
            if (item instanceof MenuActions) {
                mostrarSubmenuGame((Game) item);
            } else {
                JOptionPane.showMessageDialog(null, "Este ítem no tiene submenú disponible.");
            }
        }


        private static void imprimirTodo() {
            if (items.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay ítems registrados.");
                return;
            }
            for (RentItem item : items) {
                JOptionPane.showMessageDialog(null,
                        item.toString(),
                        "Ítem — " + item.getNombre(),
                        JOptionPane.INFORMATION_MESSAGE,
                        item.getImagen());
            }
        }


        private static void mostrarSubmenuGame(Game game) {
            boolean continuar = true;
            while (continuar) {
                String[] opciones = {
                    "1. Actualizar fecha de publicación",
                    "2. Agregar especificación",
                    "3. Ver especificaciones",
                    "4. Volver al menú principal"
                };

                int opcion = JOptionPane.showOptionDialog(null,
                        "SUBMENÚ DE: " + game.getNombre() + "\n\nSeleccione una opción:",
                        "Submenú Game",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, opciones, opciones[0]);

                switch (opcion) {
                    case 0: actualizarFechaPublicacion(game); break;
                    case 1: agregarEspecificacion(game);      break;
                    case 2: game.listEspecificaciones();      break;
                    case 3:
                    case -1: continuar = false;               break;
                }
            }
        }

        private static void actualizarFechaPublicacion(Game game) {
            Date fecha = pedirFechaConPlugin("Seleccione la nueva fecha de publicación");
            if (fecha != null) {
                game.setFechaPublicacionDesdeDate(fecha);
                JOptionPane.showMessageDialog(null, "Fecha actualizada exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se seleccionó ninguna fecha. Operación cancelada.");
            }
        }

        private static void agregarEspecificacion(Game game) {
            String espec = JOptionPane.showInputDialog(null,
                    "Ingrese la especificación técnica:",
                    "Agregar Especificación", JOptionPane.QUESTION_MESSAGE);
            if (espec != null && !espec.trim().isEmpty()) {
                game.agregarEspecificacion(espec.trim());
                JOptionPane.showMessageDialog(null, "Especificación agregada exitosamente.");
            }
        }


        private static Date pedirFechaConPlugin(String titulo) {
            JDateChooser dateChooser = new JDateChooser();
            dateChooser.setDateFormatString("dd/MM/yyyy");

            int result = JOptionPane.showConfirmDialog(
                    null, dateChooser, titulo,
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                return dateChooser.getDate();
            }
            return null;
        }

        private static boolean isCodeUnique(String code) {
            for (RentItem item : items) {
                if (item.getCodigo().equalsIgnoreCase(code)) return false;
            }
            return true;
        }

        private static RentItem buscarItem(String codigo) {
            for (RentItem item : items) {
                if (item.getCodigo().equalsIgnoreCase(codigo)) return item;
            }
            return null;
        }
    }