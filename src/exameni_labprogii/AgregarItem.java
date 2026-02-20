package exameni_labprogii;

import javax.swing.JOptionPane;


public class AgregarItem {

    private void accionAgregar(){
        String[] opciones = {"Movie", "Game"};
        
        String tipo = (String)JOptionPane.showInputDialog(frame, "Seleccione el tipo de Ítem: ", "Agregar Ítem", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
        
        if (tipo == null) {
            return;
        }
        
        String code;
        do{
            code = JOptionPane.showInputDialog(frame, "Ingrese el código del Ítem:");
            
            if (code == null) {
                return;
            }
            code = code.trim();
            
            if (code.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Código no puede estar vacío.");
                code = null;
                continue;
            }
            if (!isCodeUnique(code)){
                JOptionPane.showMessageDialog(frame, "El código ya existe. Intente otro.");
                code = null;
            } while (code == null);
            
            String name = JOptionPane.showInputDialog(frame, "Ingrese el nombre del Ítem:");
            
            if (name == null) {
                return
            }
            
            RentItem nuevo = null;
            
            if (tipo.equalsIgnoreCase("Movie")) {
                String precioStr = JOptionPane.showInputDialog(frame, "Ingrese el precio base de renta (L):", "30");
                
                if (precioStr == null) return;
                double precio;
                
                try {
                    precio = Double.parseDouble(precioStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Precio inválido. Operación cancelada. ");
                    return;
                }
                Movie m = new Movie(code, name, precio);
                int resp = JOptionPane.showConfirmDialog(frame, "¿Desea cambiar la fecha de Estreno? (Default: Hoy)", "Fecha estreno", JOptionPane.YES_NO_OPTION);
                if (resp == JOptionPane.YES_OPTION){
                    try {
                        String y = JOptionPane.showInputDialog(frame, "Mes (1-12): ");
                        if (mo == null) {
                            return;
                        }
                        String d =JOptionPane.showInputDialog(frame, "Día (1-31): ");
                        if (d == null) {
                            return;
                        }
                        int year = Integer.parseInt(y);
                        m.releaseDate(year, month, day);
                        
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Fecha inválida. Se mantiene la fecha por defecto");
                    }
                }
                nuevo = m;
        } else {
            Game g = new Game(code, name);
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
    }
}
