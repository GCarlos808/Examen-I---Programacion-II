package exameni_labprogii;
import javax.swing.ImageIcon;
public abstract class RentItem {
    protected String codigo, nombre;
    protected double precioBase;
    protected int copiasDisp;
    protected ImageIcon imagenItem = new ImageIcon();

    public RentItem(String codigo, String nombre, double precioBase) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.copiasDisp=0;
    }
    
    @Override
    public String toString(){
        return "Codigo: "+codigo+"\nNombre del item: "+nombre+"\nPrecio base: "+precioBase+"\nCantidad de copias: "+copiasDisp;
    }
    
    abstract double pagoRenta(int dias);

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioBase() {
        return precioBase;
    }
    
    
}
