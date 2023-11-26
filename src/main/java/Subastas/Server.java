/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subastas;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author andy2
 */
public class Server {
    VentanaServer ventanaServer;
    ArrayList<ThreadServer> threadsConectados;

    public Server(VentanaServer ventanaServer) {
        this.ventanaServer = ventanaServer;
        this.threadsConectados = new ArrayList<>();
    }
    
    public void runServer()
    {
        try {
            //crea el socket servidor para aceptar dos conexiones
            ServerSocket serv = new ServerSocket(8082);
            ventanaServer.mostrar(serv.toString());
            ventanaServer.mostrar("-Estamos Activos");
            ventanaServer.mostrar("-Bienvenidos a esta subasta");
            ventanaServer.mostrar("-Esperando participantes...");
            
            // espera primer cliente
            while (true)
            {
                Socket jugador = serv.accept();
                ventanaServer.mostrar("Se conecto un nuevo participante");
                ThreadServer threadServerSubasta = new ThreadServer (jugador, this);
                threadsConectados.add(threadServerSubasta);
                for (ThreadServer threadDisp: threadsConectados) {
                    ArrayList<ThreadServer> temporal = new ArrayList<>();
                    for (ThreadServer threadDispU: threadsConectados) {
                        temporal.add(threadDispU);
                    }
                    threadDisp.refresherToJugadoresEnLinea(temporal);
                }
                threadServerSubasta.start();            
            }
            
        } catch (IOException ex) {
            ventanaServer.mostrar("ERROR ... en el servidor");
        }
    }
}
