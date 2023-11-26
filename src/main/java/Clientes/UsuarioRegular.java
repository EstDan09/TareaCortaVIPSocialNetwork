/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clientes;

import Observer.Follower;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Esteban
 */
public class UsuarioRegular extends ThreadCliente  {

    public UsuarioRegular(DataInputStream entrada, DataOutputStream salida, VentanaCliente vUser) {
        super(entrada, salida, vUser);
        System.out.println("SOY USUARIO REGULAR");
    }
    
    @Override
    public void run() {
        int opcion = 0;
        while (true) {
            try {
                opcion = this.entrada.readInt();
                switch (opcion) {
                    case 1: //Usuarios Conectados
                        String jsonRec = entrada.readUTF();
                        ObjectMapper objectMapper = new ObjectMapper();
                        ArrayList<String> listaParaEnviar = objectMapper.readValue(jsonRec, ArrayList.class);
                        ventadaDeCliente.mostrarUsuariosTotal(listaParaEnviar);
                        break;
                    case 2: // ver following
                        String jsonRec2 = entrada.readUTF();
                        
                        
                        
                        ObjectMapper objectMapper2 = new ObjectMapper();
                        ArrayList<String> listaParaEnviar2 = objectMapper2.readValue(jsonRec2, ArrayList.class);
                        
                        ventadaDeCliente.mostrarFollowersFollowing(listaParaEnviar2);
                        break;
                    case 3:
                        String postRecibido = this.entrada.readUTF();
                        String autor = this.entrada.readUTF();
                        ventadaDeCliente.showPost(postRecibido, autor);
                        break;
                    case 4:
                        String messageNoti = this.entrada.readUTF();
                        ventadaDeCliente.updateNoti(messageNoti);
                        break;
                }
            } catch (IOException e) {
                System.out.println("ERROR Regular");
                break;
            }
        }
    }
}
