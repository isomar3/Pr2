
package practica1.ep2;

import java.util.ArrayList;
import java.util.Iterator;

public class Usuario {
    private int ID;
    private String nombre;
    private String correo;
    private ArrayList<Objeto> listaObjetos;
    private ArrayList<Objeto> listaObjetosBorrados;
    boolean alquilado;
    
    public Usuario(){}
    
    public Usuario(int id, String nomb, String corr){
        ID = id;
        listaObjetos = new ArrayList<Objeto>();
        listaObjetosBorrados = new ArrayList<Objeto>();
        nombre = nomb;
        correo = corr;
        alquilado = false;
    }
    
    public void anyadirObjeto(Objeto obj)
    {
        listaObjetos.add(obj);
    }
    
    public void anyadirObjetoBorrado(Objeto obj)
    {
        listaObjetosBorrados.add(obj);
    }
    
    public int GetID() {
        return ID;
    }

    public String GetNombre() {
        return nombre;
    }

    public String GetCorreo() {
        return correo;
    }
    public ArrayList<Objeto> getObjetos(){
        return listaObjetos;
    }
    
    public ArrayList<Objeto> getObjetosBorrados(){
        return listaObjetosBorrados;
    }
    
    public String toString() {
        String Cadena_idcliente = String.format("%03d", ID);
        
        return (Cadena_idcliente + "   " + nombre);
    }

    void borrarObjeto(Objeto obj) {
        
        try {
            listaObjetos.remove(obj);
            System.out.println("Borrado con exito");

        } catch (Exception e) {
            System.out.println("Error durante el borrado");
        }
       
    }
}