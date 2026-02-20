package exameni_labprogii;
import java.util.Calendar;
import java.util.ArrayList;
import javax.swing.ImageIcon;
public class Game extends RentItem implements MenuActions{
    
    private Calendar fechaPublicacion;
    private ArrayList<String> especificaciones;
    private final double precioRenta=20;
    public ImageIcon imagen;
    
    public Game(String codigo, String nombre, double precioBase) {
        super(codigo, nombre, precioBase);
        this.fechaPublicacion=Calendar.getInstance();
        this.especificaciones=new ArrayList<>();
    }
    
    @Override
    public double pagoRenta(int dias){
        return dias*precioRenta;
    }
    
    @Override
    public String toString(){
        return super.toString()+"\nFecha de publicacion: "+fechaPublicacion.toString()+"\n-PS3 Game";
    }
    
    public void setFechaPublicacion(int year, int mes, int dia){
        fechaPublicacion.set(year, mes-1, dia);
    }
    
    public void listEspecificaciones(){
        listEspecificaciones(0);
    }
    
    private void listEspecificaciones(int contador){
        if(especificaciones.isEmpty())
            return;
        if(especificaciones.size()<=contador)
            return;
        System.out.println(especificaciones.get(contador));
        listEspecificaciones(contador+1);
    }
    
    @Override
    public void submenu() {
        
    }
    
    @Override
    public void ejecutarOpcion(int opcion) {

        switch (opcion) {
            case 1:
                //setFechaPublicacion()
                break;
            case 2:
                //agregarEspecificacion()
                break;
            case 3:
                //getEspecificacionesComoTexto()
                break;
        }
    }
    
    public ImageIcon getImagen() {
        return imagen;
    }   
    
}
