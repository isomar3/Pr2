
package practica1.ep2;

import java.util.Date;

public class Prestamo {
    private Date inicio;
    private Date fin;
    private float precio;
    private Usuario cliente;
    
    
    public Prestamo(){}
    
    public Prestamo(Date ini, Date f, float prec, Usuario cli){
        inicio = ini;
        fin = f;
        precio = prec;
        cliente = cli;
    }
    
    public String GetNombreCliente() {
        return cliente.GetNombre();
    }
    
    public Date GetInicio () {
        return inicio;
    }
    
    public Date GetFin () {
        return fin;
    }
    
    public float GetImportePropietario() {
        return precio/1.1f;
    }
    
    public float GetImporteStartup() {
        return precio - precio/1.1f;
    }
}
