    package exameni_labprogii;

    import java.util.Calendar;
    import java.util.Date;
    import javax.swing.ImageIcon;
    import com.toedter.calendar.JDateChooser;

    public class Movie extends RentItem {

        private Calendar fechaEstreno;

        public Movie(String codigo, String nombre, double precioBase) {
            super(codigo, nombre, precioBase);
            this.fechaEstreno = Calendar.getInstance();
        }

        public Calendar getFechaEstreno() {
            return fechaEstreno;
        }

        public void setFechaEstreno(Calendar fechaEstreno) {
            this.fechaEstreno = fechaEstreno;
        }


        public void setFechaEstreno(int year, int mes, int dia) {
            this.fechaEstreno.set(year, mes - 1, dia);
        }


        public void setFechaEstrenoDesdeDate(Date fecha) {
            if (fecha != null) {
                this.fechaEstreno.setTime(fecha);
            }
        }

        public String getEstado() {
            Calendar hoy = Calendar.getInstance();
            int diferenciaAnios = hoy.get(Calendar.YEAR) - fechaEstreno.get(Calendar.YEAR);
            int diferenciaMeses = (diferenciaAnios * 12)
                    + hoy.get(Calendar.MONTH)
                    - fechaEstreno.get(Calendar.MONTH);
            return (diferenciaMeses <= 3) ? "ESTRENO" : "NORMAL";
        }

        @Override
        public double pagoRenta(int dias) {
            double total = precioBase * dias;
            if (getEstado().equals("ESTRENO")) {
                if (dias > 2) total += (dias - 2) * 50;
            } else {
                if (dias > 5)  total += (dias - 5) * 30;
            }
            return total;
        }

        @Override
        public String toString() {
            return super.toString()
                    + "\nFecha de estreno: "
                    + fechaEstreno.get(Calendar.DAY_OF_MONTH) + "/"
                    + (fechaEstreno.get(Calendar.MONTH) + 1) + "/"
                    + fechaEstreno.get(Calendar.YEAR)
                    + " | Estado: " + getEstado() + " - Movie";
        }
    }