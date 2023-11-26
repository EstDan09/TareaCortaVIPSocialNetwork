/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subastas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author andy2
 */
public class ThreadServer extends Thread {
    Socket cliente = null;//referencia a socket de comunicacion de cliente
    DataInputStream entrada = null;//Para leer comunicacion
    DataOutputStream salida = null;//Para enviar comunicacion
//    ObjectOutputStream salidaObjetos = null;
//    ObjectInputStream entradaObjetos = null;
    String nameUser; //Para el nombre del usuario de esta conexion
    Server servidor;// referencia al servidor
    ArrayList<ThreadServer> participantes; 
    public static ArrayList<Producto> productos2 = new ArrayList<>();

    public ThreadServer(Socket clienteOwner, Server servidor) {
        this.cliente = clienteOwner;
        this.servidor = servidor;
        this.nameUser = "por mientras";
    }
    
    public void refresherToJugadoresEnLinea(ArrayList<ThreadServer> jugadoresActualizados) {
        this.participantes = jugadoresActualizados;
        System.out.println("Participante Agregado");
    }
    
    public void setNameUser(String name) {
        this.nameUser = name;
    }

    public String getNameUser() {
        return nameUser;
    }

    public static ArrayList<Producto> getProductos2() {
        return productos2;
    }
    
    public void run() {
        try {
            // inicializa las comunicaciones
            this.entrada = new DataInputStream(cliente.getInputStream());//comunic
            this.salida = new DataOutputStream(cliente.getOutputStream());//comunic
//            this.salidaObjetos = new ObjectOutputStream(cliente.getOutputStream());
//            this.entradaObjetos = new ObjectInputStream(cliente.getInputStream());
            this.setNameUser(entrada.readUTF());
            System.out.println("Hola soy una nueva conexión de nombre " + getNameUser());

        } catch (IOException e) {
            e.printStackTrace();
        }

        int caso = 0;
        while (true) {
            try {
                //Siempre espera leer un int que será la instruccion por hacer
                caso = this.entrada.readInt();
                switch (caso) {
                    case 1: 
                        String nuevoProducto = this.entrada.readUTF();
                        servidor.ventanaServer.mostrar(nuevoProducto);
                        break;
                    case 2: 
                        String nombre = entrada.readUTF();
                        String descripcion = entrada.readUTF();
                        int precioInicial = entrada.readInt();
                        int precioFinal = entrada.readInt();
                        Producto productoSubasta = new Producto(precioInicial, precioFinal, nombre, descripcion);
                        ListaProductos.getProductos().add(productoSubasta);
                        synchronized (productos2) {
                            productos2.add(productoSubasta);
                        }
                        System.out.println("Lista de productos: " + ListaProductos.getProductos());
                        System.out.println("Producto creado");
                        for (ThreadServer cliente : participantes) {
                            cliente.salida.writeInt(1);
                            for (Producto producto : ListaProductos.getProductos()) {
                                cliente.salida.writeUTF(producto.getNombre());
                                System.out.println(producto.getNombre());
                                cliente.salida.writeUTF(producto.getDescripcion());
                                cliente.salida.writeInt(producto.getPrecioInicial());
                                cliente.salida.writeInt(producto.getPrecioFinal());  
                            }
                        }
                        break;
                    case 3:   
                        String mensaje = entrada.readUTF();
                        for (ThreadServer cliente : participantes) {
                            cliente.salida.writeInt(2);
                            cliente.salida.writeUTF(mensaje);
                        }
                        break;
                    case 4:   
                        String nombreEncontrado = this.entrada.readUTF();
                        int precioActualProducto = this.entrada.readInt();
                        String UltimoOferente = this.entrada.readUTF();
                         for (ThreadServer cliente : participantes) {
                            cliente.salida.writeInt(3);
                            cliente.salida.writeUTF(nombreEncontrado);
                            cliente.salida.writeInt(precioActualProducto);
                            cliente.salida.writeUTF(UltimoOferente);
                         }
                        break;
                    
                    case 5: 
                        String mensaje2 = entrada.readUTF();
                        for (ThreadServer cliente : participantes) {
                            cliente.salida.writeInt(4);
                            cliente.salida.writeUTF(mensaje2);
                        }
                        break;
                }
            } catch (IOException e) {
                System.out.println("El cliente termino la conexion");
                break;
            }
        }
    }
}
