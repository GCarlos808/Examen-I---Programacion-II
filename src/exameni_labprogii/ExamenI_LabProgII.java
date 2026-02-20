package exameni_labprogii;
import javax.swing.*;
import java.util.ArrayList;

public class ExamenI_LabProgII {
    public void Rentar(){
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
            JOptionPane.showMessageDialog(null, "El valor ingresado es invalido");
            return;
        }
        
        JOptionPane.showMessageDialog(null, "Total a pagar: Lps. "+itemIngresado.pagoRenta(dias));
        
    }
    
    
    private static ArrayList<RentItem> items = new ArrayList<>(); 


    public static void main(String[] args) {

    }

}
