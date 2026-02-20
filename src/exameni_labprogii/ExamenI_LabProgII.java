    package exameni_labprogii;

    import javax.swing.*;
    import java.util.ArrayList;
    import java.util.Date;
    import java.io.File;
    import com.toedter.calendar.JDateChooser;

    public class ExamenI_LabProgII {

        private static ArrayList<RentItem> items = new ArrayList<>();

        public static void main(String[] args) {
        
        }

        JFrame frame = new JFrame();

        private static Date pedirFechaConPlugin(String titulo) {
            JDateChooser dateChooser = new JDateChooser();
            dateChooser.setDateFormatString("dd/MM/yyyy");

            int result = JOptionPane.showConfirmDialog(
                    null,
                    dateChooser,
                    titulo,
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                return dateChooser.getDate(); 
            }
            return null;  
        }

        private boolean isCodeUnique(String code) {
            for (RentItem item : items) {
                if (item.getCodigo().equalsIgnoreCase(code)) return false;
            }
            return true;
        }

     
        private void accionAgregar() {
            String[] opciones = {"Movie", "Game"};
            String tipo = (String) JOptionPane.showInputDialog(frame,
                    "Seleccione el tipo de Ítem:", "Agregar Ítem",
                    JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            if (tipo == null) return;

            String code = null;
            do {
                code = JOptionPane.showInputDialog(frame, "Ingrese el código del Ítem:");
                if (code == null) return;
                code = code.trim();
                if (code.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Código no puede estar vacío.");
                    code = null;
                    continue;
                }
                if (!isCodeUnique(code)) {
                    JOptionPane.showMessageDialog(frame, "El código ya existe. Intente otro.");
                    code = null;
                }
            } while (code == null);

            String name = JOptionPane.showInputDialog(frame, "Ingrese el nombre del Ítem:");
            if (name == null) return;

            RentItem nuevo = null;

            if (tipo.equalsIgnoreCase("Movie")) {
                String precioStr = JOptionPane.showInputDialog(frame, "Ingrese el precio base de renta (L):", "30");
                if (precioStr == null) return;
                double precio;
                try {
                    precio = Double.parseDouble(precioStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Precio inválido. Operación cancelada.");
                    return;
                }

                Movie m = new Movie(code, name, precio);

                int resp = JOptionPane.showConfirmDialog(frame,
                        "¿Desea cambiar la fecha de Estreno? (Default: Hoy)",
                        "Fecha estreno", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    Date fecha = pedirFechaConPlugin("Seleccione la fecha de estreno");
                    if (fecha != null) {
                        m.setFechaEstrenoDesdeDate(fecha);
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "No se seleccionó fecha. Se mantiene la fecha por defecto.");
                    }
                }
                nuevo = m;

            } else { 
                String precioStr = JOptionPane.showInputDialog(frame, "Ingrese el precio base de renta (L):", "20");
                if (precioStr == null) return;
                double precio;
                try {
                    precio = Double.parseDouble(precioStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Precio inválido. Operación cancelada.");
                    return;
                }

                Game g = new Game(code, name, precio);

                int resp = JOptionPane.showConfirmDialog(frame,
                        "¿Desea cambiar la fecha de publicación? (por defecto hoy)",
                        "Fecha publicación", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION) {
                    Date fecha = pedirFechaConPlugin("Seleccione la fecha de publicación");
                    if (fecha != null) {
                        g.setFechaPublicacionDesdeDate(fecha);
                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "No se seleccionó fecha. Se mantiene la fecha por defecto.");
                    }
                }
                nuevo = g;
            }

            if (nuevo == null) return;

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Seleccione una imagen para el ítem (opcional)");
            int result = chooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                nuevo.setImagePath(chooser.getSelectedFile().getAbsolutePath());
            }

            String copiesStr = JOptionPane.showInputDialog(frame,
                    "Ingrese cantidad de copias disponibles (entero):", "0");
            if (copiesStr == null) return;
            try {
                int c = Integer.parseInt(copiesStr);
                nuevo.setCopies(Math.max(0, c));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Cantidad inválida. Se deja en 0.");
            }

            items.add(nuevo);
            JOptionPane.showMessageDialog(frame, tipo + " agregado exitosamente.");
        }


        public static void Rentar() {
            String codigo = JOptionPane.showInputDialog("Ingrese el código del ítem:");
            if (codigo == null || codigo.trim().isEmpty()) return;

            RentItem itemIngresado = buscarItem(codigo);
            if (itemIngresado == null) {
                JOptionPane.showMessageDialog(null, "Item no existe");
                return;
            }

            JOptionPane.showMessageDialog(null,
                    itemIngresado.toString(), "Información del Ítem",
                    JOptionPane.INFORMATION_MESSAGE, itemIngresado.getImagen());

            int dias;
            try {
                dias = Integer.parseInt(JOptionPane.showInputDialog("Ingrese cantidad de días:"));
                if (dias <= 0) {
                    JOptionPane.showMessageDialog(null, "Cantidad de días inválida");
                    return;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "El valor ingresado es inválido.");
                return;
            }

            JOptionPane.showMessageDialog(null,
                    "Total a pagar: Lps. " + itemIngresado.pagoRenta(dias));
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
                    case 2: verEspecificaciones(game);        break;
                    case 3:
                    case -1: continuar = false;               break;
                }
            }
        }

       
        private static void actualizarFechaPublicacion(Game game) {
            Date fecha = pedirFechaConPlugin("Actualizar Fecha de Publicación");
            if (fecha != null) {
                game.setFechaPublicacionDesdeDate(fecha);
                JOptionPane.showMessageDialog(null,
                        "Fecha actualizada exitosamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "No se seleccionó ninguna fecha. Operación cancelada.",
                        "Cancelado", JOptionPane.WARNING_MESSAGE);
            }
        }

        private static void agregarEspecificacion(Game game) {
            String especificacion = JOptionPane.showInputDialog(null,
                    "Ingrese la especificación técnica:",
                    "Agregar Especificación", JOptionPane.QUESTION_MESSAGE);
            if (especificacion != null && !especificacion.trim().isEmpty()) {
                game.agregarEspecificacion(especificacion);
                JOptionPane.showMessageDialog(null,
                        "Especificación agregada exitosamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

        private static void verEspecificaciones(Game game) {
            game.listEspecificaciones();
        }


        private static RentItem buscarItem(String codigo) {
            for (RentItem item : items) {
                if (item.getCodigo().equalsIgnoreCase(codigo)) return item;
            }
            return null;
        }

        private static void ejecutarSubmenu() {
            String codigo = JOptionPane.showInputDialog("Ingrese el código del ítem:");
            if (codigo == null || codigo.trim().isEmpty()) return;

            RentItem item = buscarItem(codigo);
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
                String info = item.toString();
                if (item instanceof Movie) {
                    info += "\nEstado: " + ((Movie) item).getEstado();
                }
                JOptionPane.showMessageDialog(null, info, "Ítem",
                        JOptionPane.INFORMATION_MESSAGE, item.getImagen());
            }
        }
    }