package exameni_labprogii;

    import java.util.Calendar;
    import java.util.Date;
    import java.util.ArrayList;
    import javax.swing.JOptionPane;

    public class Game extends RentItem implements MenuActions {

        private Calendar fechaPublicacion;
        private ArrayList<String> especificaciones;
        private final double precioRenta = 20;

        public Game(String codigo, String nombre, double precioBase) {
            super(codigo, nombre, precioBase);
            this.fechaPublicacion = Calendar.getInstance();
            this.especificaciones = new ArrayList<>();
        }

        @Override
        public double pagoRenta(int dias) {
            return dias * precioRenta;
        }


        public void setFechaPublicacion(int year, int mes, int dia) {
            fechaPublicacion.set(year, mes - 1, dia);
        }

        public void setFechaPublicacionDesdeDate(Date fecha) {
            if (fecha != null) {
                this.fechaPublicacion.setTime(fecha);
            }
        }

        public Calendar getFechaPublicacion() {
            return fechaPublicacion;
        }

        public void agregarEspecificacion(String especificacion) {
            especificaciones.add(especificacion);
        }

        public void listEspecificaciones() {
            listEspecificaciones(0);
        }

        private void listEspecificaciones(int contador) {
            if (especificaciones.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay especificaciones");
                return;
            }
            if (especificaciones.size() <= contador) return;
            JOptionPane.showMessageDialog(null, especificaciones.get(contador));
            listEspecificaciones(contador + 1);
        }


        @Override
        public String toString() {
            return super.toString()
                    + "\nFecha de publicación: "
                    + fechaPublicacion.get(Calendar.DAY_OF_MONTH) + "/"
                    + (fechaPublicacion.get(Calendar.MONTH) + 1) + "/"
                    + fechaPublicacion.get(Calendar.YEAR)
                    + " - PS3 Game";
        }

        @Override
        public void submenu() { }

        @Override
        public void ejecutarOpcion(int opcion) {
            switch (opcion) {
                case 1:
                    break;
                case 2:
                    String especificacion = JOptionPane.showInputDialog("Ingrese especificación: ");
                    if (especificacion != null && !especificacion.trim().isEmpty()) {
                        agregarEspecificacion(especificacion);
                        JOptionPane.showMessageDialog(null, "La especificación se agregó de forma exitosa");
                    }
                    break;
                case 3:
                    listEspecificaciones();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida");
            }
        }
    }