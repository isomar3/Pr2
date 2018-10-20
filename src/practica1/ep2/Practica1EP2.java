package practica1.ep2;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Isaac-Alejandro
 */
public class Practica1EP2 {

    public static void main(String[] args) throws InterruptedException, ParseException, IOException {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        int sig_usuario = 1,
            sig_objeto = 1;
        Usuario us;
        Objeto obj;
        boolean ok;
        int opcion = 0;
        Scanner scanner = new Scanner(System.in);
        
        while(opcion != 9) {
            
            //Menu ccon las opciones
            System.out.println("\nElige una opción:" + "\n1-Alta de usuario" + "\n2-Alta objeto" + "\n3-Alquiler de objeto"
                + "\n4-Listar todos los objetos" + "\n5-Baja de objeto" + "\n6-Mostrar saldo" 
                + "\n7-Cambiar importe" + "\n8-Guardar prestamos" + "\n9-Salir\n");
             
            System.out.println("Opcion: ");
            
            //Mientras nos den una opcion no valida, preguntarla
            ok = false;
            while (!ok){
                try {
                    opcion = scanner.nextInt();
                    ok = true;
                } catch (Exception e) {
                    System.out.println("Elige una opcion valida");
                    scanner.nextLine();
                }
            }
            
            //Cada una de las acciones que debe realizar el menu
            switch (opcion) {
                case 1:
                    System.out.println("Se va a introducir un nuevo usuario:\n");
                    AltaUsuario(scanner, sig_usuario, usuarios);
                    sig_usuario++;
                    break;
                case 2:
                    if(!(sig_usuario == 1)) {
                        System.out.println("Se va a introducir un nuevo objeto:\n");
                        us = ElegirUsuario(usuarios, scanner);
                        AltObjeto(scanner, sig_objeto, us);
                        sig_objeto++;
                    }
                    else
                        System.out.println("No hay usuarios registrados");
                    break;
                case 3:
                    if (sig_usuario > 2 && sig_objeto != 1) {
                        System.out.println("Se va a alquilar un objeto, indica el usario que va a realizar el alquiler:\n");
                        us = ElegirUsuario(usuarios, scanner);
                        obj = ElegirObjeto(usuarios, us, scanner);
                        if(obj != null)
                            AltaPrestamo(us, obj, scanner);
                    }
                    else
                        System.out.println("No hay usuarios suficientes u objetos suficientes");
                    break;
                case 4:
                    if(sig_usuario != 1) {
                        System.out.println("Lista de usuarios, con sus objetos y los prestamos de estos:\n");
                        MostrartBD(usuarios);
                    }
                    else
                        System.out.println("No hay usuario registrados\n");
                    break;
                case 5:
                    if (sig_usuario > 1 && sig_objeto != 1) {
                        System.out.println("Borrado de un objeto:\n");
                        us = ElegirUsuario(usuarios, scanner);
                        obj = ElegirObjetoDelUsuario(us, scanner);
                        if(obj != null)
                            BorrarObjeto(us, obj);
                    }
                    else
                        System.out.println("No hay usuarios u objetos registrados");
                    break;
                case 6:
                    if(sig_usuario != 1) {
                        System.out.println("Lista de usuarios con prestamos en detalle:\n");
                        MostrartBDDetalle(usuarios);
                    }
                    else
                        System.out.println("No hay usuario registrados\n");
                    break;
                case 7:
                    if(sig_usuario != 1 && sig_objeto != 1) {
                        System.out.println("Cambio de importe:\n");
                        us = ElegirUsuario(usuarios, scanner);
                        obj = ElegirObjetoDelUsuario(us, scanner);
                        if(obj != null)
                            CambiarImporte(obj, scanner);
                    }
                    else
                        System.out.println("No hay usuarios u objetos suficientes registrados");
                    break;
                case 8:
                    if(sig_usuario != 1) {
                        System.out.println("Se van a guardar los usuarios y sus prestamos en un archivo:\n");
                        GuardarDBDetalle(usuarios);
                    }
                    else
                        System.out.println("No hay usuario registrados\n");
                    break;
                case 9:
                    System.out.println("Hasta otra");
                    break;
                default:
                    System.out.println("Elige una opcion valida");
                    break;
            }
            
            Thread.sleep(1000);
        }    
    }
    /**
     * Funcion para da de alta un nuevo usuario en el sistema
     * 
     * @param scanner para la lectura de datos
     * @param id siguiente id del usuario
     * @param usuarios lista de usuarios donde se metera el nuevo
     */
    private static void AltaUsuario(Scanner scanner, int id, ArrayList<Usuario> usuarios){
        String nombre, correo;
        Usuario us;
        
        System.out.println("Introduce el nombre: ");
        scanner.nextLine();
        nombre = scanner.nextLine();
        
        do {
            System.out.println("Introduce el correo: ");
            correo = scanner.nextLine();
        } while (!comprobarCorreo(correo));
      
        us = new Usuario(id, nombre, correo);
        usuarios.add(us);
        
    }
    
    /**
     * Funcion para comprobar que un email
     * 
     * @param correo Email a comrobar
     * @return true si es correcto
     */
    private static boolean comprobarCorreo(String correo) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        boolean ok = false;
        
        Matcher mather = pattern.matcher(correo);
        
        if (mather.find() == true)
            ok = true;
        else {
            System.out.println("\nEl email ingresado es inválido.");
        }
        
        return ok;
    }
    
    /**
 * Elegi un usuario de una lista de usuarios
 * 
 * @param usuarios Usuarios que se van a mostrar
 * @param scanner para la lectura de datos
 * @return El usuario elegido de la lista
 */
    private static Usuario ElegirUsuario(ArrayList<Usuario> usuarios, Scanner scanner) {
       Usuario us = null;
       int indice;
       boolean ok = false;
        
       do {
            try {
                MostrarUsuarios(usuarios);
                System.out.println("\nElige el usuario: ");
                indice = scanner.nextInt();

                for(Usuario u: usuarios) {
                    if (u.GetID() == indice) {
                        us = u;
                    }
                }
                if (us == null)
                    System.out.println("\nElige un usuario existente\n");
                else {
                    System.out.println("\nUsuario escogido: " + us.toString());
                    ok = true;
                }

            } catch (Exception e) {
                System.out.println("Introduce un numero de 3 digitos por favor\n");
                scanner.next();
            }
       }  while (!ok);
       
       return us;
    }
    
    /**
     * Funcion para mostrar todos los usuarios
     * 
     * @param usuarios Lista de los usuarios a mostrar
     */
    private static void MostrarUsuarios(ArrayList<Usuario> usuarios) {
        
       for(Usuario us: usuarios) {
           System.out.println(us.toString());
       }
    }
    
    /**
     * Funcion que da de alta un nuevo objeto en el sistema
     * 
     * @param scanner para lectura de datos
     * @param sig_objeto Identificador del nuevo objeto
     * @param us Usuario al que pertenece el objeto
     * @throws ParseException 
     */
    private static void AltObjeto(Scanner scanner, int sig_objeto, Usuario us) throws ParseException {
        String descripcion; 
        String fecha;
        Date inicio, fin;
        Objeto obj;
        float precio = -1;
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        formato.setLenient(false);
        

        System.out.println("Introduce la descripcion del producto: ");
        scanner.nextLine();
        descripcion = scanner.nextLine();
        
        inicio = new Date();
        System.out.println("Fecha de inicio de alquiler: " + formato.format(inicio));

        do {
            System.out.println("Introduce la fecha final de alquiler(dd/mm/aaaa): ");
            fecha = scanner.nextLine();
        }
        while(!ValidarFormatoFecha(fecha, formato, inicio));
        fin = formato.parse(fecha);
        
        do {
            try {
                System.out.println("Introduce el precio (en euros) por dia de alquiler del producto (ejemplo: 25,35): ");
                precio = scanner.nextFloat();
            } catch (Exception e) {
                System.out.println("Introduce un valor correcto");
                scanner.next();
            }
        } while (precio <= 0);
        
        obj = new Objeto(sig_objeto, precio, us.GetID(), descripcion, inicio, fin);
        us.anyadirObjeto(obj);
    }
    
    /**
     * Funcion que valida una fecha de manera que cumpla un formato, sea correcta y se posterior a una fecha dada
     * 
     * @param fecha Fecha que se va a validar
     * @param formato Formato que debera cumplir esta fecha
     * @param inicio Fecha la cual debera ser anterior a la fecha dada
     * @return Devuelve true si se cumplen las tres condiciones
     */
    private static boolean ValidarFormatoFecha(String fecha, SimpleDateFormat formato, Date inicio) {
        Date fin;
        
        try {
            fin = formato.parse(fecha);
            if (fin.after(inicio)) {
                return true;
            }
            else {
                System.out.println("Introduce un fecha posterior");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Formato o fecha incorrecto");
            return false;
        }
    }

    /**
     * Funcion para elegir un objeto de una lista de objetos para su alquiler
     * 
     * @param usuarios Lista de usuarios que tienen los objetos a mostrar
     * @param usuario Usuario que va a elegir el objeto a alquilar, para evitar que se alquile a si mismo
     * @param scanner Para lectura de datos
     * @return El objeto elegido
     */
    private static Objeto ElegirObjeto(ArrayList<Usuario> usuarios, Usuario usuario, Scanner scanner) {
       Objeto obj = null;
       int indice;
       boolean ok = false;
        
       do {
            try {
                MostrarObjetos(usuarios);
                System.out.println("\nElige el objeto: ");
                indice = scanner.nextInt();
                
                for(Usuario us: usuarios) 
                    for(Objeto o: us.getObjetos())
                        if (o.GetID() == indice) {
                            obj = o;
                        }
                
                if (obj == null)
                    System.out.println("\nElige un objeto existente\n");
                else if (obj.GetPropietario() == usuario.GetID()) {
                    System.out.println("\nNo puedes realizar esta accion sobre ti mismo, vuelve a empezar\n");
                    return null;
                }
                else {
                    System.out.println("\nObjeto escogido: " + obj.toString());
                    ok = true;
                }

            } catch (Exception e) {
                System.out.println("Introduce un numero de 3 digitos por favor\n");
                System.out.println(e);
                scanner.next();
            }
       }  while (!ok);
       
       return obj;
    }

    /**
     * Funcion que da de alta un nuevo prestamos
     * 
     * @param us Usuario que va a realizar el prestamos
     * @param obj Objeto que se va a prestar
     * @param scanner Para lectura de datos
     * @throws ParseException 
     */
    private static void AltaPrestamo(Usuario us, Objeto obj, Scanner scanner) throws ParseException {
        Date inicio, fin;
        String fecha;
        int dias;
        float total;
        Prestamo pr;
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        formato.setLenient(false);
        scanner.nextLine();
        
           //Este bucle se raliza 2 veces, un vez dando error directamente y en la segunda ya te deja escribir, pero no se porque
        do {
            System.out.println("Introduce la fecha donde comenzara el alquiler(dd/mm/aaaa): ");
            fecha = scanner.nextLine();
        }
        while(!ValidarFechaRango(fecha, formato, obj.GetFechaInicio(), obj.GetFechaFin()));
        inicio = formato.parse(fecha);
        
        do {
            System.out.println("Introduce la fecha donde terminara el alquiler(dd/mm/aaaa): ");
            fecha = scanner.nextLine();
        }
        while(!ValidarFechaRango(fecha, formato, inicio, obj.GetFechaFin()));
        fin = formato.parse(fecha);
        
        total = CosteTotal(inicio, fin, obj.GetPrecio());
        
        System.out.println("Total a pagar: " + total);
        
        pr = new Prestamo(inicio, fin, total, us);
        
        obj.AñadirPrestamo(pr);
    }

    /**
     * Funcion que muestra todos los obejtos disponibles de todos los usuarios
     * 
     * @param usuarios Lista de los usuarios con sus objetos
     */
    private static void MostrarObjetos(ArrayList<Usuario> usuarios) {
        for(Usuario us: usuarios) {
            for(Objeto o: us.getObjetos())
                    System.out.println(o.toString());
       }
    }
    
    /**
     * Funcion que valida una fecha de manera que cumpla un formato, sea correcta y se encuentre entre 
     * un rango de fechas
     * 
     * @param fecha Fecha que se va a validar
     * @param formato Formato que debera cumplir esta fecha
     * @param inicio Fecha la cual debera ser anterior a la fecha dada
     * @param fin Fecha la cual debera ser posterior a la fecha dada
     * @return 
     */
    private static boolean ValidarFechaRango(String fecha, SimpleDateFormat formato, Date inicio, Date fin) {
        Date date;
        
        try {
            date = formato.parse(fecha);
            if (date.after(inicio) && date.before(fin)) {
                return true;
            }
            else {
                System.out.println("Introduce un fecha  entre los rangos");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Formato o fecha incorrecto");
            return false;
        }
    }

    /**
     * Funcion que cacula el coste total de un prestamo, incluyendo el 10% de la aplicacion
     * 
     * @param inicio Fecha de inicio del alquiler
     * @param fin Fecha final del alquiler
     * @param precio`Precio del alquiler por dia
     * @return Precio total del alquiler
     */
    private static float CosteTotal(Date inicio, Date fin, float precio) {
        
        long dif_ms = Math.abs(fin.getTime() - inicio.getTime());
        long diff_dias = TimeUnit.DAYS.convert(dif_ms, TimeUnit.MILLISECONDS);
        
        return (diff_dias * precio) * 1.1f;
    }

    /**
     * Funcion que muestra los usuarios, con sus objetos y los prestamos a estos
     * 
     * @param usuarios Lista de usuario de donde obtendremos la informacion
     */
    private static void MostrartBD(ArrayList<Usuario> usuarios) {
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        
        for(Usuario u: usuarios) {
            InformacionUsuario(u);
            if (u.getObjetos().size() > 0) {
                MostrarObjetos(u.getObjetos(), formato);   
            }
           
            if (u.getObjetosBorrados().size() > 0) {
                MostrarObjetos(u.getObjetosBorrados(), formato);   
            }
            
            if(u.getObjetosBorrados().size() <= 0 && u.getObjetos().size() <= 0)
                System.out.println("\tEl propietario " + u.GetID() + " no tiene objetos borrados asociados\n");
        }
    }

    /**
     * Funcion que muestra la informacion de un usuario de forma detallada
     * 
     * @param u Usuario del que se va a mostrar la informacion
     */
    private static void InformacionUsuario(Usuario u) {
        
        System.out.println("PROPIETARIO: " + u.GetID());
        System.out.println("Nombre del propietario: " + u.GetNombre());
        System.out.println("Correo electronico: " + u.GetCorreo() + "\n");
        System.out.println("\tOBJETOS DEL PROPIETARIO " + u.GetID() + "\n");
    }

    /**
     * Funcion que muestra la informacion de un objeto de forma detallada
     * 
     * @param o Objeto del que se va a mostrar la informacion
     * @param formato Formato que deberan cumplir las fechas mostradas
     */
    private static void InformacionObjeto(Objeto o, SimpleDateFormat formato) {
        try {
            System.out.println("\tCodigo del objeto: " + o.GetID());
            System.out.println("\tDescripcion: " + o.GetDescripcion());
            System.out.println("\tFecha disponibilidad: " + formato.format(o.GetFechaInicio()) + " - " + formato.format(o.GetFechaFin()));
            System.out.println("\tCoste del prestamo por dia: " + o.GetPrecio() + "\n");
            System.out.println("\t\tPRESTAMOS DEL OBJETO: " + o.GetID() + "\n");
        } catch (Exception e) {
            System.out.println("ERROR AL MOSTRAR UN OBJETO");
        }
    }

    /**
     * Funcion que muestra la informacion de un prestamo de forma detallada
     * 
     * @param pr Prestamo del que se va a mostrar la informacion
     * @param formato Formato que deberan cumplir las fechas mostradas
     */
    private static void InformacionPrestamos(Prestamo pr, SimpleDateFormat formato) {
        System.out.println("\t\tNombre del cliente: " + pr.GetNombreCliente());
        System.out.println("\t\tFechas del prestamos: " + formato.format(pr.GetInicio()) + " - " + formato.format(pr.GetFin()));
        System.out.println("\t\tImporte para el propietario: " + pr.GetImportePropietario());
        System.out.println("\t\tImporte para la startup: " + pr.GetImporteStartup() + "\n");
    }

    /**
     * Funcion para borrar un objeto y se deje de mostrar para alquilar
     * 
     * @param us Usuario al que pertenece el objeto
     * @param obj Objeto que se va a borrar
     */
    private static void BorrarObjeto(Usuario us, Objeto obj) {
           us.anyadirObjetoBorrado(obj);
           us.borrarObjeto(obj);
    }
    
    /**
     * Funcion para elegir un objeto de un usuario
     * 
     * @param usuario Usuario del que se quiere elegir el objeto
     * @param scanner Para lectura de datos
     * @return Objeto elegido
     */
    private static Objeto ElegirObjetoDelUsuario(Usuario usuario, Scanner scanner) {
        Objeto obj = null;
       int indice;
       boolean ok = false;
        
       do {
            try {
                MostrarObjetosDelUsuario(usuario);
                System.out.println("\nElige el objeto: ");
                indice = scanner.nextInt();
                
                for(Objeto o: usuario.getObjetos())
                    if (o.GetID() == indice) {
                        obj = o;
                    }
                
                if (obj == null)
                    System.out.println("\nElige un objeto existente\n");
                else if (obj.GetPropietario() == usuario.GetID()) {
                    System.out.println("\nObjeto escogido: " + obj.toString());
                    ok = true;
                }
                
            } catch (Exception e) {
                System.out.println("Introduce un numero de 3 digitos por favor\n");
                scanner.next();
            }
       }  while (!ok);
       
       return obj;
    }

    /**
     * Funcion que muestra todos los objetos disponibles de un usuario
     * 
     * @param usuario Usuario del que se va a mostrar la informacion
     */
    private static void MostrarObjetosDelUsuario(Usuario usuario) {
            for(Objeto o: usuario.getObjetos())
                if(o.GetPropietario() == usuario.GetID())
                    System.out.println(o.toString());
      
    }
    
    /**
     * Funcion que muestra los usuarios, con sus objetos y los prestamos a estos, junto con
     * las ganancias del sistemas
     * 
     * @param usuarios Lista de usuario de donde obtendremos la informacion
     */
    private static void MostrartBDDetalle(ArrayList<Usuario> usuarios) {
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        boolean ok;
        float total;
        
        for(Usuario u: usuarios) {
            total = 0;
            ok = false;
            if (u.getObjetos().size() > 0 || u.getObjetosBorrados().size() > 0) {
                for(Objeto o: u.getObjetos()) {
                    if(o.GetPrestamos().size() > 0) {
                            ok = true;
                    }
                }
                for(Objeto o: u.getObjetosBorrados()) {
                    if(o.GetPrestamos().size() > 0)
                        ok = true;
                }
            }
            
            if (ok) {
                InformacionUsuario(u);
                for(Objeto o: u.getObjetos()) {
                    if(o.GetPrestamos().size() > 0) {
                        InformacionObjeto(o, formato);
                        for(Prestamo pr: o.GetPrestamos()){
                            InformacionPrestamos(pr, formato);
                            total += pr.GetImporteStartup();
                        }   
                    }
                }
                for(Objeto o: u.getObjetosBorrados()) {
                    if(o.GetPrestamos().size() > 0) {
                        InformacionObjeto(o, formato);
                        for(Prestamo pr: o.GetPrestamos()){
                            InformacionPrestamos(pr, formato);
                            total += pr.GetImporteStartup();
                        }   
                    }
                }
                System.out.println("Importe total caluculado para la startup: " + total + "\n");
            }
        }
    }

    /**
     * Funcion que muestra los objetos de una lista de objetos
     * 
     * @param objetos Lista de objetos a mostrar
     * @param formato @param formato Formato que deberan cumplir las fechas mostradas
     */
    private static void MostrarObjetos(ArrayList<Objeto> objetos, SimpleDateFormat formato) {
        if(objetos.size() > 0) {
            for(Objeto o: objetos) {
                        InformacionObjeto(o, formato);
                        if(o.GetPrestamos().size() > 0) {
                            for(Prestamo pr: o.GetPrestamos()){
                                InformacionPrestamos(pr, formato);
                            }   
                        }
                        else
                            System.out.println("\t\tEl objeto " + o.GetID() + " no tiene prestamos asociados\n");
                    }
        }
    }
    
    /**
     * Funcion que oremite cambiar el precio de alquiler de un objeto
     * 
     * @param obj Objeto del que se va a cambiar el precio
     * @param scanner Para lectura de datos
     */
    private static void CambiarImporte(Objeto obj, Scanner scanner) {
        float importe = -1;
        
        do {
            try {
                System.out.println("Introduce el nuevo precio (en euros) por dia de alquiler del producto (ejemplo: 25,35): ");
                importe = scanner.nextFloat();
            } catch (Exception e) {
                System.out.println("Introduce un valor correcto");
                scanner.next();
            }
        } while (importe <= 0);
        
        obj.SetImporte(importe);
    }

    /**
     * Funcion que guarda las informacion detallada del los usuarios en un fichero
     * 
     * @param usuarios Informacion de los usuarios a guardar
     * @throws IOException 
     */
    private static void GuardarDBDetalle(ArrayList<Usuario> usuarios) throws IOException {
        try {
            FileWriter archivo = new FileWriter("src/saldos.txt");
            PrintWriter pw = new PrintWriter(archivo);
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            boolean ok;
            float total;

            for(Usuario u: usuarios) {
                total = 0;
                ok = false;
                if (u.getObjetos().size() > 0 || u.getObjetosBorrados().size() > 0) {
                    for(Objeto o: u.getObjetos()) {
                        if(o.GetPrestamos().size() > 0) {
                                ok = true;
                        }
                    }
                    for(Objeto o: u.getObjetosBorrados()) {
                        if(o.GetPrestamos().size() > 0)
                            ok = true;
                    }
                }

                if (ok) {
                    GuardarInformacionUsuario(u, pw);
                    for(Objeto o: u.getObjetos()) {
                        if(o.GetPrestamos().size() > 0) {
                            GuardarInformacionObjeto(o, formato, pw);
                            for(Prestamo pr: o.GetPrestamos()){
                                GuardarInformacionPrestamos(pr, formato, pw);
                                total += pr.GetImporteStartup();
                            }   
                        }
                    }
                    for(Objeto o: u.getObjetosBorrados()) {
                        if(o.GetPrestamos().size() > 0) {
                            GuardarInformacionObjeto(o, formato, pw);
                            for(Prestamo pr: o.GetPrestamos()){
                                GuardarInformacionPrestamos(pr, formato, pw);
                                total += pr.GetImporteStartup();
                            }   
                        }
                    }
                    pw.write("Importe total caluculado para la startup: " + total + "\n");
                }
            }
            pw.close();
        } catch (Exception e) {
            System.out.println("Error guardando el fichero");
        }
        
    }

    /**
     * Funcion que guarda la informacion de un usuario en un fichero
     * 
     * @param u Usuario a guardar
     * @param pw Para escribir en el fichero
     */
    private static void GuardarInformacionUsuario(Usuario u, PrintWriter pw) {
        try {
            pw.write("PROPIETARIO: " + u.GetID() + "\n");
            pw.write("Nombre del propietario: " + u.GetNombre() + "\n");
            pw.write("Correo electronico: " + u.GetCorreo() + "\n");
            pw.write("\tOBJETOS DEL PROPIETARIO " + u.GetID() + "\n");
        } catch (Exception e)  {
            System.out.println("Error el ecribir un usuario en el fichero");
        }
            
    }

    /**
     * Funcion que escribe un objeto en el fichero
     * 
     * @param o Obketo a escribir
     * @param formato Formato para las fechas
     * @param pw Para escribir en el fichero
     */
    private static void GuardarInformacionObjeto(Objeto o, SimpleDateFormat formato, PrintWriter pw) {
        try {
            pw.write("\n\tCodigo del objeto: " + o.GetID() + "\n");
            pw.write("\tDescripcion: " + o.GetDescripcion() + "\n");
            pw.write("\tFecha disponibilidad: " + formato.format(o.GetFechaInicio()) + " - " + formato.format(o.GetFechaFin()) + "\n");
            pw.write("\tCoste del prestamo por dia: " + o.GetPrecio() + "\n");
            pw.write("\t\tPRESTAMOS DEL OBJETO: " + o.GetID() + "\n");
        } catch (Exception e) {
            System.out.println("Error al escribir un objeto en el fichero");
        }
    }

    /**
     * Funcion que escribe un prestamo en el fichero
     * 
     * @param pr Prestamo  a escribir
     * @param formato Formato para las fechas
     * @param pw Para escribir en el fichero
     */
    private static void GuardarInformacionPrestamos(Prestamo pr, SimpleDateFormat formato, PrintWriter pw) {
        pw.write("\t\tNombre del cliente: " + pr.GetNombreCliente() + "\n");
        pw.write("\t\tFechas del prestamos: " + formato.format(pr.GetInicio()) + " - " + formato.format(pr.GetFin()) + "\n");
        pw.write("\t\tImporte para el propietario: " + pr.GetImportePropietario() + "\n");
        pw.write("\t\tImporte para la startup: " + pr.GetImporteStartup() + "\n");
    }
}