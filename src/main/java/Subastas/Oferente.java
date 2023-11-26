/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subastas;

import java.io.*;
import java.net.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
/**
 *
 * @author andy2
 */
public class Oferente implements IObserver{
   public static String IP_SERVER = "localhost"; //IP del Servidor
   public DataInputStream entrada = null; 
   public DataOutputStream salida = null;
//   public ObjectOutputStream salidaObjetos = null;
//   public ObjectInputStream entradaObjetos = null;
   
   Socket cliente = null;//para la comunicacion
   VentanaOferente ventanaJugador;
   String nombreJugador;
   
   public Oferente(VentanaOferente vent) throws IOException
   {      
      this.ventanaJugador = vent;
   }
   
   @Override       
    public void notifyObserver(String command, Object source, int precio) {           
        if(command.equals("CambioPrecioActual")){                              
            System.out.println("Hubo un cambio en el precio actual del producto");           
        }
        if(command.equals("HayGanador")){                              
            System.out.println("El producto ha sido vendido");           
        }
    }
   
   public void conexion() throws IOException 
   {
      try {
          System.out.println("AAAAAAAAAAAAAAAAA");
          // se conecta con dos sockets al server, uno comunicacion otro msjes
         cliente = new Socket(Oferente.IP_SERVER, 8082);
         
         // inicializa las entradas-lectura y salidas-escritura
         entrada = new DataInputStream(cliente.getInputStream());
         salida = new DataOutputStream(cliente.getOutputStream());
//         entradaObjetos = new ObjectInputStream(cliente.getInputStream());
//         salidaObjetos = new ObjectOutputStream(cliente.getOutputStream());
         System.out.println("A2");
         
         // solicita el nombre del user
         nombreJugador = JOptionPane.showInputDialog("Introducir Nick :");
          System.out.println("A3");
         //Lo coloca en la ventana
         ventanaJugador.setTitle(nombreJugador);
         
         // es lo primero que envia al server
         // el thread servidor esta pendiente de leer el nombre antes de entrar
         // al while para leer opciones
         salida.writeUTF(nombreJugador);
         System.out.println("1. Envia el nombre del cliente: "+ nombreJugador);
      } catch (IOException e) {
         System.out.println("\tEl servidor no esta levantado");
         System.out.println("\t=============================");
      }
       System.out.println("Crea el Thread");
      ThreadOferenteySubastador threadOferente = new ThreadOferenteySubastador(entrada, salida, ventanaJugador);
       ListaProductos listaProductosInstancia = ListaProductos.getInstance();
       listaProductosInstancia.addObserver(this);
       threadOferente.start();
   }
   
   public String getNombreJugador()
   {
      return nombreJugador;
   }

    InputStream getInputStream() throws IOException {
        return cliente.getInputStream();
    }
    
    OutputStream getOutputStream() throws IOException {
        return cliente.getOutputStream();
    }

}
