
package practica1.ep2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Objeto {
    private String descripcion;
    private float precio;
    private int propietario;
    private int ID;
    private Date fecha_inicio;
    private Date fecha_fin;
    private ArrayList<Prestamo> prestamos;
    
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    
    public Objeto(){}
    
    public Objeto(int id, float prec, int prop, String des, Date ini, Date fin){
        propietario = prop;
        ID = id;
        descripcion = des;
        fecha_inicio = ini;
        fecha_fin = fin;
        precio = prec;
        prestamos = new ArrayList<Prestamo>();
    }
    
    public Objeto(int id, float prec, int prop, String des, Date ini, Date fin, ArrayList<Prestamo> pre){
        propietario = prop;
        ID = id;
        descripcion = des;
        fecha_inicio = ini;
        fecha_fin = fin;
        precio = prec;
        prestamos = pre;
    }

    public int GetID() {
        return ID;
    }
    
    public int GetPropietario () {
        return propietario;
    }
    
    public Date GetFechaInicio () {
        return fecha_inicio;
    }
    
    public Date GetFechaFin () {
        return fecha_fin;
    }
    
    public float GetPrecio() {
        return precio;
    }
    
    public String GetDescripcion() {
        return descripcion;
    }
    
    public ArrayList<Prestamo> GetPrestamos() {
        return prestamos;
    }
    
    public String toString() {
        String Cadena_idobjeto = String.format("%03d", ID);
        
        return (Cadena_idobjeto + "   " + descripcion + "  " + precio + 
                "  " + formato.format(fecha_inicio) + "  " + formato.format(fecha_fin));
    }

    public void AñadirPrestamo(Prestamo pr) {
       prestamos.add(pr);
    }

    void SetImporte(float importe) {
        precio = importe;
    }
}