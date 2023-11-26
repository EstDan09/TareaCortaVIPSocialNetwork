/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

/**
 *
 * @author Esteban
 */
public class Post {
    public String mensaje;
    public int likes;
    public ThreadServer owner;

    public ThreadServer getOwner() {
        return owner;
    }

    public void setOwner(ThreadServer owner) {
        this.owner = owner;
    }
    

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Post(String mensaje, ThreadServer owner) {
        this.mensaje = mensaje;
        this.owner = owner;
        this.likes = 0;
    }
    
    public void likeIt() {
        likes++;
        System.out.println(this.mensaje + " " + this.owner.getUserName() + " " + this.likes);
    }
    
}
