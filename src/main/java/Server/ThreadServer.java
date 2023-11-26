/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import Clientes.MiembroVIP;
import Clientes.Status;
import Clientes.ThreadCliente;
import Observer.Follower;
import Observer.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Esteban
 */
public class ThreadServer extends Thread implements Member, Follower {

    Socket cliente = null;
    DataInputStream entrada = null;
    DataOutputStream salida = null;
    Server server;
    String userName;
    Status status;
    ArrayList<ThreadServer> usuariosEnLinea;
    ArrayList<ThreadServer> miembrosEnLinea;
    ArrayList<ThreadServer> regularesEnLinea;
    ArrayList<ThreadServer> usuariosQueSigo;
    ArrayList<ThreadServer> usuariosQueMeSiguen;
    ArrayList<Post> postsCreados;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Socket getCliente() {
        return cliente;
    }

    public void setCliente(Socket cliente) {
        this.cliente = cliente;
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

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<ThreadServer> getUsuariosEnLinea() {
        return usuariosEnLinea;
    }

    public void setUsuariosEnLinea(ArrayList<ThreadServer> usuariosEnLinea) {
        this.usuariosEnLinea = usuariosEnLinea;
    }

    public ArrayList<ThreadServer> getUsuariosQueSigo() {
        return usuariosQueSigo;
    }

    public void setUsuariosQueSigo(ArrayList<ThreadServer> usuariosQueSigo) {
        this.usuariosQueSigo = usuariosQueSigo;
    }

    public ArrayList<ThreadServer> getUsuariosQueMeSiguen() {
        return usuariosQueMeSiguen;
    }

    public void setUsuariosQueMeSiguen(ArrayList<ThreadServer> usuariosQueMeSiguen) {
        this.usuariosQueMeSiguen = usuariosQueMeSiguen;
    }

    public ThreadServer(Socket clienteOwner, Server servidor, int num) {
        this.cliente = clienteOwner;
        this.server = servidor;
        this.usuariosEnLinea = new ArrayList<>();
        this.miembrosEnLinea = new ArrayList<>();
        this.regularesEnLinea = new ArrayList<>();
        this.usuariosQueSigo = new ArrayList<>();
        this.usuariosQueMeSiguen = new ArrayList<>();
        this.postsCreados = new ArrayList<>();
        this.userName = "Waiting for name ajua";
    }

    @Override
    public void run() {
        try {
            this.entrada = new DataInputStream(this.cliente.getInputStream());
            this.salida = new DataOutputStream(this.cliente.getOutputStream());
            setUserName(this.entrada.readUTF());
            String statusStr = this.entrada.readUTF();
            System.out.println("Seha conectado el usuario " + getUserName());
            if (Status.Regular.toString().equals(statusStr)) {
                this.status = Status.Regular;
            }
            if (Status.VIP.toString().equals(statusStr)) {
                this.status = Status.VIP;
            }
            sendUser();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int caso = 0;
        while (true) {
            try {
                caso = this.entrada.readInt();
                switch (caso) {
                    case 1: //Regular: Wants to follow
                        String memberToFollow = this.entrada.readUTF();
                        boolean validate = false;
                        for (ThreadServer usuario : usuariosEnLinea) {
                            if (memberToFollow.equals(usuario.getUserName())) {
                                if (usuario.getStatus() == Status.VIP && this.getStatus() == Status.Regular) {
                                    usuario.addObserver(this);
                                    if (usuario.usuariosQueMeSiguen.size() % 10 == 0) {
                                        usuario.notifyObservers(usuario.getUserName() + " ha llegado a " + usuario.usuariosQueMeSiguen.size() + "seguidores");
                                    }
                                    this.usuariosQueSigo.add(usuario);
                                    validate = true;
                                    break;
                                }
                            }
                        }
                        if (validate) {
                            this.seeFollowing();
                        }
                        break;
                    case 2: //VIP: Make a Post
                        String post = this.entrada.readUTF();
                        Post tempo = new Post(post, this);
                        postsCreados.add(tempo);
                        for (ThreadServer usuarioSuscrito : usuariosQueMeSiguen) {
                            System.out.println("mandando a " + usuarioSuscrito.getUserName());
                            usuarioSuscrito.salida.writeInt(3);
                            usuarioSuscrito.salida.writeUTF(post);
                            usuarioSuscrito.salida.writeUTF(this.getUserName());
                        }
                        this.notifyObservers("Nuevo Post de: " + this.getUserName());
                        break;
                    case 3: //Regular: wants to like
                        String postToLike = this.entrada.readUTF();
                        String ownerToLike = this.entrada.readUTF();
                        String likeDislike = this.entrada.readUTF();
                        if ("like".equals(likeDislike)) {
                            for (ThreadServer usuarioQueSigo : usuariosQueSigo) {
                                for (Post postSearch : usuarioQueSigo.postsCreados) {
                                    if (postSearch.getMensaje().equals(postToLike)) {
                                        postSearch.likeIt();
                                        if (postSearch.getLikes() % 10 == 0) {
                                            usuarioQueSigo.notifyObservers("El post < " + postSearch.getMensaje() + " > llego a 10 likes!");
                                        }
                                    }
                                }
                            }
                        } else {
                            for (ThreadServer usuarioQueSigo : usuariosQueSigo) {
                                for (Post postSearch : usuarioQueSigo.postsCreados) {
                                    if (postSearch.getMensaje().equals(postToLike)) {
                                        postSearch.dislikeIt();
                                        usuarioQueSigo.notifyObservers("El post < " + postSearch.getMensaje() + " tuvo un dislike por parte de: " + this.getUserName());
                                    }
                                }
                            }
                            
                        }
                        break;
                    case 4://VIP: Se dio de baja
                        baja();
                        break;
                }
            } catch (IOException e) {
                System.out.println("El cliente termino la conexion");
                break;
            }

        }
    }

    public void sendUser() throws JsonProcessingException {

//        if (this.status == Status.VIP) {
//            System.out.println("Soy " + this.userName + " (entreVIP) " + this.status);
//            
//            for (ThreadServer usuario : this.usuariosEnLinea) {
//                ArrayList<String> listaParaEnviar = new ArrayList<>();
//                for (ThreadServer usuarioU: this.usuariosEnLinea) {
//                    if (usuarioU.getStatus() == Status.Regular) {
//                        if (!usuarioU.miembrosEnLinea.contains(this)) {
//                            usuarioU.miembrosEnLinea.add(this);
//                        }
//                        else {
//                            listaParaEnviar.add(usuarioU.getUserName());
//                        }
//                    }
//                }
//                
//                ObjectMapper objectMapper = new ObjectMapper();
//                String json = objectMapper.writeValueAsString(listaParaEnviar);
//                
//                if (usuario.getStatus() == Status.Regular) {
//                    try {
//                        usuario.salida.writeInt(1);
//                        usuario.salida.writeUTF(json);
//                    } catch (IOException ex) {
//                        Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
//                    }                  
//                }
//            }
//        }
//        
//        if (this.status == Status.Regular) {
//            System.out.println("Soy " + this.userName + " (entreRegular) " + this.status);
//            for (ThreadServer usuario : this.usuariosEnLinea) {
//                ArrayList<String> listaParaEnviar = new ArrayList<>();
//                for (ThreadServer usuarioU: this.usuariosEnLinea) {
//                    if (usuarioU.getStatus() == Status.VIP) {
//                        if (!usuarioU.regularesEnLinea.contains(this)) {
//                            usuarioU.regularesEnLinea.add(this);
//                        }
//                        else {
//                            listaParaEnviar.add(usuarioU.getUserName());
//                        }
//                    }
//                }
//                
//                ObjectMapper objectMapper = new ObjectMapper();
//                String json = objectMapper.writeValueAsString(listaParaEnviar);
//                
//                if (usuario.getStatus() == Status.VIP) {
//                    try {
//                        usuario.salida.writeInt(1);
//                        usuario.salida.writeUTF(json);
//                    } catch (IOException ex) {
//                        Logger.getLogger(ThreadServer.class.getName()).log(Level.SEVERE, null, ex);
//                    }                  
//                }
//            }
//        }
        for (ThreadServer usuario : this.usuariosEnLinea) {
            ArrayList<String> listaParaEnviar = new ArrayList<>();
            for (ThreadServer usuarioU : this.usuariosEnLinea) {
                listaParaEnviar.add(usuarioU.getUserName() + " - " + usuarioU.getStatus().toString());
            }
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(listaParaEnviar);

            try {
                usuario.salida.writeInt(1);
                usuario.salida.writeUTF(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void seeFollowing() throws JsonProcessingException {
        ArrayList<String> listaParaEnviar = new ArrayList<>();
        for (ThreadServer usuarioQueSigo : usuariosQueSigo) {
            listaParaEnviar.add(usuarioQueSigo.getUserName());
            usuarioQueSigo.seeFollowers();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(listaParaEnviar);

        try {
            this.salida.writeInt(2);
            this.salida.writeUTF(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void seeFollowers() throws JsonProcessingException {
        ArrayList<String> listaParaEnviar = new ArrayList<>();
        for (ThreadServer usuarioQueMeSiguen : usuariosQueMeSiguen) {
            listaParaEnviar.add(usuarioQueMeSiguen.getUserName());
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(listaParaEnviar);

        try {
            this.salida.writeInt(2);
            this.salida.writeUTF(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addObserver(Follower observer) {
        usuariosQueMeSiguen.add((ThreadServer) observer);
    }

    @Override
    public void removeObserver(Follower observer) {
        usuariosQueMeSiguen.remove((ThreadServer) observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (ThreadServer observer : usuariosQueMeSiguen) {
            observer.update(message);
        }
    }

    @Override
    public void update(String message) {
        try {
            this.salida.writeInt(4);
            this.salida.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void baja() throws JsonProcessingException {
        for (ThreadServer observer : usuariosQueMeSiguen) {
            removeObserver(observer);
            observer.usuariosQueSigo.remove(this);
            observer.seeFollowing();
        }

    }

}