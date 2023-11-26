/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subastas;

import java.io.Serializable;
/**
 *
 * @author andy2
 */
public class DatoSocket implements Serializable{
    int c;
   String d;

    public DatoSocket(int c, String d) {
        this.c = c;
        this.d = d;
    }  
}

class Punto implements Serializable{
    int x, y;
    DatoSocket x1;

    public Punto (int x, int y)
    {
        this.x = x;
        this.y = y;

    }
}
