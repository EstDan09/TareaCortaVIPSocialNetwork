/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clientes;

import java.io.DataInputStream;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author Esteban
 */
public abstract class ThreadCliente extends Thread {
    VentanaCliente ventadaDeCliente;
    DataInputStream entrada;
    DataOutputStream salida;
    
    public ThreadCliente (DataInputStream entrada, DataOutputStream salida, VentanaCliente vUser) {
        this.entrada = entrada;
        this.ventadaDeCliente = vUser;
        this.salida = salida;
    }
    
    public abstract void run();
}
