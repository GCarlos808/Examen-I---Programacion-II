package exameni_labprogii;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public abstract class RentItem {

    protected String codigo, nombre;
    protected double precioBase;
    protected int copiasDisp;
    protected ImageIcon imagenItem = new ImageIcon();

    public RentItem(String codigo, String nombre, double precioBase) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.copiasDisp = 0;
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo
                + "\nNombre del item: " + nombre
                + "\nPrecio base: " + precioBase
                + "\nCantidad de copias: " + copiasDisp;
    }

    abstract double pagoRenta(int dias);

    public void setCopies(int copias) {
        this.copiasDisp = copias;
    }

    public void setImagePath(String path) {
        ImageIcon original = new ImageIcon(path);
        int maxSize = 500; 

        int w = original.getIconWidth();
        int h = original.getIconHeight();

        double scale = Math.min((double) maxSize / w, (double) maxSize / h);
        int newW = (int) (w * scale);
        int newH = (int) (h * scale);

        Image scaled = original.getImage().getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        this.imagenItem = new ImageIcon(scaled);
    }

    public String getCodigo()      { return codigo; }
    public String getNombre()      { return nombre; }
    public double getPrecioBase()  { return precioBase; }
    public ImageIcon getImagen()   { return imagenItem; }
}