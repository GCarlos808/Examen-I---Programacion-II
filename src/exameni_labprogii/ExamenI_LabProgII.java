package exameni_labprogii;
import javax.swing.*;
import java.util.ArrayList;
import com.toedter.calendar.JDateChooser;
import java.io.File;

public class ExamenI_LabProgII {
    
    private static ArrayList<RentItem> items = new ArrayList<>(); 

    public static void main(String[] args) {
        
    }

    JFrame frame = new JFrame();
    
    private void accionAgregar() {
        String[] opciones = {"Movie", "Game"};
        String tipo = (String) JOptionPane.showInputDialog(frame, "Seleccione el tipo de Ítem:", "Agregar Ítem", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
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
            int resp = JOptionPane.showConfirmDialog(frame, "¿Desea cambiar la fecha de Estreno? (Default: Hoy)", "Fecha estreno", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                try {
                    String y = JOptionPane.showInputDialog(frame, "Año (YYYY):");
                    if (y == null) return;
                    String mo = JOptionPane.showInputDialog(frame, "Mes (1-12):");
                    if (mo == null) return;
                    String d = JOptionPane.showInputDialog(frame, "Día (1-31):");
                    if (d == null) return;
                    int year = Integer.parseInt(y);
                    int month = Integer.parseInt(mo);
                    int day = Integer.parseInt(d);
                    m.getFechaEstreno(year, month, day);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Fecha inválida. Se mantiene la fecha por defecto");
                }
            }
            nuevo = m;
        } else {
            Game g = new Game(code, name, precioBase);
            int resp = JOptionPane.showConfirmDialog(frame, "¿Desea cambiar la fecha de publicación? (por defecto hoy)", "Fecha publicación", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                try {
                    String y = JOptionPane.showInputDialog(frame, "Año (YYYY):");
                    if (y == null) return;
                    String mo = JOptionPane.showInputDialog(frame, "Mes (1-12):");
                    if (mo == null) return;
                    String d = JOptionPane.showInputDialog(frame, "Día (1-31):");
                    if (d == null) return;
                    int year = Integer.parseInt(y);
                    int month = Integer.parseInt(mo);
                    int day = Integer.parseInt(d);
                    g.setFechaPublicacion(year, month, day);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Fecha inválida. Se mantiene la fecha por defecto.");
                }
            }
            nuevo = g;
        }
        
        if (nuevo == null) return;
        
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccione una imagen para el ítem (opcional)");
        int result = chooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            nuevo.setImagePath(f.getAbsolutePath());
        }
        
        String copiesStr = JOptionPane.showInputDialog(frame, "Ingrese cantidad de copias disponibles (entero):", "0");
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
        RentItem itemIngresado = null;
        int dias;
        String codigo = JOptionPane.showInputDialog("Ingrese el codigo del item ");
        
        if (codigo == null || codigo.trim().isEmpty()) {
            return;
        }
        
        itemIngresado = buscarItem(codigo);
        
        if (itemIngresado == null) {
            JOptionPane.showMessageDialog(null, "Item no existe");
            return;
        }
        
        JOptionPane.showMessageDialog(null, itemIngresado.toString(), "Información del Ítem", 
            JOptionPane.INFORMATION_MESSAGE, itemIngresado.getImagen());
        
        try {
            dias = Integer.parseInt(JOptionPane.showInputDialog("Ingrese cantidad de dias"));
            
            if (dias <= 0) {
                JOptionPane.showMessageDialog(null, "Cantidad de dias invalido");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El valor ingresado es invalido ");
            return;
        }
        
        JOptionPane.showMessageDialog(null, "Total a pagar: Lps. " + itemIngresado.pagoRenta(dias));
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
            
            int opcion = JOptionPane.showOptionDialog(
                null,
                "SUBMENÚ DE: " + game.getNombre() + "\n\n" +
                "Seleccione una opción:",
                "Submenú Game",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
            );
            
            switch (opcion) {
                case 0:
                    actualizarFechaPublicacion(game);
                    break;
                case 1:
                    agregarEspecificacion(game);
                    break;
                case 2:
                    verEspecificaciones(game);
                    break;
                case 3:
                case -1:
                    continuar = false;
                    break;
            }
        }
    }
    
    private static void actualizarFechaPublicacion(Game game) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JTextField txtYear = new JTextField(10);
        JTextField txtMes = new JTextField(10);
        JTextField txtDia = new JTextField(10);
        
        panel.add(new JLabel("Año:"));
        panel.add(txtYear);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Mes (1-12):"));
        panel.add(txtMes);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Día:"));
        panel.add(txtDia);
        
        int result = JOptionPane.showConfirmDialog(
            null,
            panel,
            "Actualizar Fecha de Publicación",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                int year = Integer.parseInt(txtYear.getText().trim());
                int mes = Integer.parseInt(txtMes.getText().trim());
                int dia = Integer.parseInt(txtDia.getText().trim());
                
                if (mes < 1 || mes > 12) {
                    throw new IllegalArgumentException("Mes debe estar entre 1 y 12");
                }
                if (dia < 1 || dia > 31) {
                    throw new IllegalArgumentException("Día debe estar entre 1 y 31");
                }
                
                game.setFechaPublicacion(year, mes, dia);
                
                JOptionPane.showMessageDialog(
                    null,
                    "Fecha actualizada exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Error: Ingrese números válidos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    private static void agregarEspecificacion(Game game) {
        String especificacion = JOptionPane.showInputDialog(
            null,
            "Ingrese la especificación técnica:",
            "Agregar Especificación",
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (especificacion != null && !especificacion.trim().isEmpty()) {
            game.agregarEspecificacion(especificacion);
            
            JOptionPane.showMessageDialog(
                null,
                "Especificación agregada exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    
    private static void verEspecificaciones(Game game) {
        game.listEspecificaciones();
        
        JOptionPane.showMessageDialog(
            null,
            "Las especificaciones se han mostrado en la consola",
            "Ver Especificaciones",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
   
    
    private static RentItem buscarItem(String codigo) {
        for (RentItem item : items) {
            if (item.getCodigo().equalsIgnoreCase(codigo)) {
                return item;
            }
        }
        return null;
    }
}