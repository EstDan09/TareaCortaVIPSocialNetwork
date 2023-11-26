/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subastas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author andy2
 */

public class ThreadOferenteySubastador extends Thread{
    String ultimoOfertador = "";
    VentanaOferente ventanaOferente;
    DataInputStream entrada;
    DataOutputStream salida;
//    ObjectOutputStream salidaObjetos;
//    ObjectInputStream entradaObjetos;
    InputStream entradaRaw;
    public static List<String> nombresDeProductos = new ArrayList<>();
    public static ArrayList<Producto> productosDisponibles = new ArrayList<>();
    //public Set<Producto> productosDisponibles = new HashSet<>(); 
    //private List<Oferente> oferentesObservadores = new ArrayList<>();
    
    public ThreadOferenteySubastador(DataInputStream entrada, DataOutputStream salida, VentanaOferente vo) throws IOException {
        this.entrada = entrada;
        this.ventanaOferente = vo;
        this.salida = salida; 
//        this.entradaObjetos = new ObjectInputStream(entradaObj);
//        this.salidaObjetos = new ObjectOutputStream(saldaObj);
    }
    
    public static List<String> getNombresDeProductos() {
        return nombresDeProductos;
    }
    
//    public void registrarOferenteObserver(Oferente oferente) {
//        oferentesObservadores.add(oferente); 
//    }
    
    public void run() {
        //VARIABLES
        int opcion = 0;
        while (true) {
            try {
                System.out.println("atento a instrucciones");
                // esta leyendo siempre la instruccion, un int
                opcion = this.entrada.readInt();
                switch (opcion) {
                    case 1:
                        synchronized (nombresDeProductos){
                            while (entrada.available() > 0) {
                                String nombreProducto = entrada.readUTF();
                                String descripcionProducto = entrada.readUTF();
                                int precioInicialProducto = entrada.readInt();
                                int precioFinalProducto = entrada.readInt();
                                if (!nombresDeProductos.contains(nombreProducto)) {
                                    nombresDeProductos.add(nombreProducto);
                                    Producto productoSubasta = new Producto(precioInicialProducto, precioFinalProducto, nombreProducto, descripcionProducto);
                                    synchronized (productosDisponibles) {
                                        productosDisponibles.add(productoSubasta);
                                    }
                                }
                                //--------------------------------
//                                nombresDeProductos.add(nombreProducto);
//                                String descripcionProducto = entrada.readUTF();
//                                int precioInicialProducto = entrada.readInt();
//                                int precioFinalProducto = entrada.readInt();
//                                Producto productoSubasta = new Producto(precioInicialProducto, precioFinalProducto, nombreProducto, descripcionProducto);
//                                //abc
//                                if (!productosDisponibles.contains(productoSubasta)) {
//                                    nombresDeProductos.add(nombreProducto);
//                                    productosDisponibles.add(productoSubasta);
//                                }
                            }
                            ListaProductos listaProductosInstancia = ListaProductos.getInstance();
                            listaProductosInstancia.productos = productosDisponibles;
                            System.out.println("Size: " + productosDisponibles.size());
                        }
                        break;

                    case 2:
                        String mensaje = entrada.readUTF();
                        ventanaOferente.actualizarMessageBox(mensaje); 
                        break;
                    
                    case 3:
                        String nombreEncontrado = this.entrada.readUTF();
                        int precioActualProducto = this.entrada.readInt();
                        String UltimoOferente = this.entrada.readUTF();
                        ultimoOfertador = UltimoOferente;
                        for (Producto producto : productosDisponibles) {
                            if (producto.getNombre().equals(nombreEncontrado)) {
                                producto.setPrecioActualSubasta(precioActualProducto);
                                break;
                            }
                        }
                        ListaProductos listaProductosInstancia = ListaProductos.getInstance();
                        listaProductosInstancia.notifyAllObservers("CambioPrecioActual", null, 0);
                        break;
                        
                    case 4:
                        String mensaje2 = entrada.readUTF();
                        mensaje2 += ultimoOfertador;
                        System.out.println(ultimoOfertador);
                        System.out.println(ventanaOferente.getTitle());
                        if(ventanaOferente.getTitle().equals(ultimoOfertador)){
                            String mensajeFelicitacion = "¡Felicidades, usted a ganado!";
                            ventanaOferente.actualizarMessageBox(mensajeFelicitacion);
                        }
                        else{
                            ventanaOferente.actualizarMessageBox(mensaje2); 
                        }
                        listaProductosInstancia = ListaProductos.getInstance();
                        listaProductosInstancia.notifyAllObservers("HayGanador", null, 0);
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error en la comunicaci�n " + "Informaci�n para el usuario");
                break;
            }
        }
        System.out.println("se desconecto el servidor");
    }
}
