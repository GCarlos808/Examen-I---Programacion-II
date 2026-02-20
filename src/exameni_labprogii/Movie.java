
    package exameni_labprogii;
    import java.util.Calendar;

    public class Movie extends RentItem {
        private Calendar fechaEstreno;
        
                public Movie(String codigo, String nombre, double precioBase) {
            super (codigo, nombre, precioBase);
            this.fechaEstreno=fechaEstreno;
        }
                
        public Calendar getFechaEstreno() {
            return fechaEstreno;
        };   
        
        public void setFechaEstreno(Calendar fechaEstreno) {
            this.fechaEstreno=fechaEstreno;
        }
        
        public String getEstado() {
            Calendar hoy = Calendar.getInstance();
            int diferenciaAnios = hoy.get(Calendar.YEAR) - fechaEstreno.get(Calendar.YEAR);
        int diferenciaMeses = (diferenciaAnios * 12) + hoy.get(Calendar.MONTH) - fechaEstreno.get(Calendar.MONTH);

        if (diferenciaMeses <= 3) {
            return "ESTRENO";
        } else {
            return "NORMAL";
        }
    } 
        
        @Override
        public double pagoRenta(int dias) {
            double total = precioBase;
            if (getEstado().equals("Estreno")) {
                if (dias > 2) {
                    total += (dias - 2) * 50;
                }
            } else {
                if (dias > 5 ) {
                    total += (dias - 5) *30;
                }
            }
            
            return total;
        }
        
        @Override 
        public String toString() {
            return super.toString() + " | Estado " + getEstado() + " - Movie";
        }
        
        }
 
    