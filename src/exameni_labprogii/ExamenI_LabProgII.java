package exameni_labprogii;
import javax.swing.*;
import java.util.ArrayList;

public class ExamenI_LabProgII {
    public void Rentar(){
        RentItem itemIngresado=null;
        String codigo=JOptionPane.showInputDialog("Ingrese el codigo del item");
        for(RentItem item: items ){
            if(item.getCodigo().equalsIgnoreCase(codigo))
                itemIngresado=item;
            break;
        }
        if(itemIngresado==null){
            JOptionPane.showMessageDialog(null, "El codigo ingresado no existe");
            return;
        }
        
        JOptionPane.showMessageDialog(null, "", JOptionPane.INFORMATION_MESSAGE, itemIngresad);
    }
    
    
    private static ArrayList<RentItem> items = new ArrayList<>(); 


    public static void main(String[] args) {

    }

}
