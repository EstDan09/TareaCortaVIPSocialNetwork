/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clientes;

import Observer.Follower;
import Observer.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Esteban
 */
public class MiembroVIP extends ThreadCliente  {
    
    public ArrayList<String> followers = new ArrayList<>();

    public MiembroVIP(DataInputStream entrada, DataOutputStream salida, VentanaCliente vUser) {
        super(entrada, salida, vUser);
        System.out.println("SOY VIP");
    }

    @Override
    public void run() {
        int opcion = 0;
        while (true) {
            try {
                opcion = this.entrada.readInt();
                switch (opcion) {
                    case 1: //He ganado un seguidor
                        String jsonRec = entrada.readUTF();
                        ObjectMapper objectMapper = new ObjectMapper();
                        ArrayList<String> listaParaEnviar = objectMapper.readValue(jsonRec, ArrayList.class);
                        ventadaDeCliente.mostrarUsuariosTotal(listaParaEnviar);
                        break;
                    case 2: //Ver following
                        System.out.println("aja");
                        String jsonRec2 = entrada.readUTF();
                        System.out.println("vip-"+ jsonRec2);
                        
                        ObjectMapper objectMapper2 = new ObjectMapper();
                        ArrayList<String> listaParaEnviar2 = objectMapper2.readValue(jsonRec2, ArrayList.class);
                        
                        followers.clear();
                        
                        for (String observer: listaParaEnviar2) {
                            followers.add(observer);
                        }
                        
                        ventadaDeCliente.mostrarFollowersFollowing(listaParaEnviar2);
                        break;
                    case 3: //Notificar seguidores
//                        String whatToNotify = entrada.readUTF();
//                        notifyObservers(whatToNotify);
                        break;
                }
            } catch (IOException e) {
                System.out.println("ERROR VIP");
                break;
            }
        }
    }
}    