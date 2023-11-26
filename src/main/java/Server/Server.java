/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.net.Socket;
import java.util.ArrayList;
import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Esteban
 */
public class Server {
    
    VentanaServer ventanaServer;
    ArrayList<Socket> usuariosConectados;
    ArrayList<ThreadServer> threadsConectados;
    int contadorUsuarios = 1;
    
    public Server (VentanaServer main) {
        this.ventanaServer = main;
        usuariosConectados = new ArrayList<>();
        threadsConectados = new ArrayList<>();
    }
    
    public void runServer() {
        try {
            System.out.println("Entro a server-----------------");
            ServerSocket server = new ServerSocket(8082);
            ventanaServer.mostrar(server.toString());
            ventanaServer.mostrar("Server de nuestra red social: ON");
            ventanaServer.mostrar("Esperando usuarios...");
            
            while(true) {
                Socket usuario = server.accept();
                usuariosConectados.add(usuario);
                ventanaServer.mostrar("Usuario Conectado");
                ventanaServer.mostrar(usuariosConectados.size() + ": Es el total"
                        + " de usuarios conectados");
                ThreadServer threadServerUsuario = new ThreadServer(usuario, this, contadorUsuarios);
                threadsConectados.add(threadServerUsuario);
                for (ThreadServer threadDisponible : threadsConectados) {
                    ArrayList<ThreadServer> temporal = new ArrayList<>();
                    for (ThreadServer threadDisponibleU : threadsConectados) {
                        temporal.add(threadDisponibleU);
                    }
                    threadDisponible.setUsuariosEnLinea(temporal);
                }
                threadServerUsuario.start();
            }
            
            
            
        } catch (IOException e) {
            
        }
    }
    
}
