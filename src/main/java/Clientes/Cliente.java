/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clientes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Esteban
 */
public class Cliente {
    
    public static String IP_SERVER = "localhost";
    public DataInputStream entrada = null;
    public DataOutputStream salida = null;
    public Socket cliente = null;
    public VentanaCliente ventanaUsuario;
    public String userName;
    public Status status;

    public static String getIP_SERVER() {
        return IP_SERVER;
    }

    public static void setIP_SERVER(String IP_SERVER) {
        Cliente.IP_SERVER = IP_SERVER;
    }

    public DataInputStream getEntrada() {
        return entrada;
    }

    public void setEntrada(DataInputStream entrada) {
        this.entrada = entrada;
    }

    public DataOutputStream getSalida() {
        return salida;
    }

    public void setSalida(DataOutputStream salida) {
        this.salida = salida;
    }

    public Socket getCliente() {
        return cliente;
    }

    public void setCliente(Socket cliente) {
        this.cliente = cliente;
    }

    public VentanaCliente getVentanaUsuario() {
        return ventanaUsuario;
    }

    public void setVentanaUsuario(VentanaCliente ventanaUsuario) {
        this.ventanaUsuario = ventanaUsuario;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public Cliente (VentanaCliente ventana) throws IOException {
        this.ventanaUsuario = ventana;
    }
    
    public void conexion () throws IOException {
        try {
            this.cliente = new Socket(Cliente.IP_SERVER, 8082);
            
            this.entrada = new DataInputStream(this.cliente.getInputStream());
            this.salida = new DataOutputStream(this.cliente.getOutputStream());
            
            
            this.userName = JOptionPane.showInputDialog("Introducir Nombre de Usuario:");
            String temp = JOptionPane.showInputDialog("Introducir tu status:");
            if(Status.Regular.toString().equals(temp)){
                this.status = Status.Regular;
            }
            if(Status.VIP.toString().equals(temp)){
                this.status = Status.VIP;
            }
            
            this.ventanaUsuario.setTitle(this.userName + " - " + this.status );
            
            this.salida.writeUTF(this.userName);
            
            this.salida.writeUTF(this.status.toString());
            
            
            
  
        } catch (IOException e) {
            System.out.println("\tEl servidor no esta levantado");
            System.out.println("\t=============================");
        }
        
        if (this.status == Status.Regular) {
            new UsuarioRegular(this.entrada, this.salida, this.ventanaUsuario).start();
        }
        if (this.status == Status.VIP) {
            new MiembroVIP(this.entrada, this.salida, this.ventanaUsuario).start();
        }
    }
}
