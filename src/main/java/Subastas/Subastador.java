/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subastas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author andy2
 */
public class Subastador {
   public static String IP_SERVER = "localhost"; //IP del Servidor
   public DataInputStream entrada = null; 
   public DataOutputStream salida = null;
   
   Socket cliente = null;//para la comunicacion
   VentanaSubastador ventanaJugador;
   String nombreJugador;
   
   public Subastador(VentanaSubastador vent) throws IOException
   {      
      this.ventanaJugador = vent;
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
      //new threadCliente(entrada, salida, ventanaJugador).start();
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
