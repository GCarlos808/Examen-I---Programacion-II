package exameni_labprogii;
import javax.swing.*;
import java.util.ArrayList;

public class ExamenI_LabProgII {
    public static void Rentar(){
        RentItem itemIngresado=null;
        int dias;
        String codigo=JOptionPane.showInputDialog("Ingrese el codigo del item");
        for(RentItem item: items ){
            if(item.getCodigo().equalsIgnoreCase(codigo))
                itemIngresado=item;
            break;
        }
        if(itemIngresado==null){
            JOptionPane.showMessageDialog(null, "Item no existe");
            return;
        }
        
        JOptionPane.showMessageDialog(null, itemIngresado.toString(), "Información del Ítem", JOptionPane.INFORMATION_MESSAGE, itemIngresado.getImagen());
        
        try{
            dias=Integer.parseInt(JOptionPane.showInputDialog("Ingrese cantidad de dias"));
            
            if(dias<=0){
                JOptionPane.showMessageDialog(null, "Cantidad de dias invalido");
                return;
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "El valor ingresado es invalido ");
            return;
        }
        
        JOptionPane.showMessageDialog(null, "Total a pagar: Lps. "+itemIngresado.pagoRenta(dias));
        
    }
    
    
    private static ArrayList<RentItem> items = new ArrayList<>(); 


    public static void main(String[] args) {
        
    }
    
    private static void ejecutarSubMenu() {
        String codigo = JOptionPane.showInputDialog(null, "Ingrese el código del ítem:", " Ejecutar Submenú", 
                JOptionPane.QUESTION_MESSAGE);
        
        if (codigo == null || codigo.trim().isEmpty()) {
            return;
        }
        RentItem item = buscarItem(codigo);
        if (item == null) {
            JOptionPane.showMessageDialog(null, "Item No Existe", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!(item instanceof MenuActions)) {
            JOptionPane.showMessageDialog(null, "Este ítem no tiene submenú disponible.\n" +
                    "Solo los Games tienen submenú.", "Información", JOptionPane.INFORMATION_MESSAGE);
        
            return;
        }
        Game game = (Game) item;
        mostrarSubmenuGame(game);   
    
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
                case 0:  // Actualizar fecha
                    actualizarFechaPublicacion(game);
                    break;
                    
                case 1:  // Agregar especificación
                    agregarEspecificacion(game);
                    break;
                    
                case 2:  // Ver especificaciones
                    verEspecificaciones(game);
                    break;
                    
                case 3:  // Volver
                case -1: // X (cerrar)
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
        if (game.getEspecificaciones().isEmpty()) {
            JOptionPane.showMessageDialog(
                null,
                "No hay especificaciones registradas para este juego",
                "Sin Especificaciones",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════\n");
        sb.append("  ESPECIFICACIONES DE: ").append(game.getNombre()).append("\n");
        sb.append("═══════════════════════════════════\n\n");
        
        for (int i = 0; i < game.getEspecificaciones().size(); i++) {
            sb.append(String.format("%2d. %s\n", (i + 1), game.getEspecificaciones().get(i)));
        }
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(450, 300));
        
        JOptionPane.showMessageDialog(
            null,
            scrollPane,
            "Especificaciones",
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
