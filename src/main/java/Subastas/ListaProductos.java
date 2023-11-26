/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Subastas;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author andy2
 */
public class ListaProductos extends AbstractObservable implements Serializable{
    //private static final long serialVersionUID = 1L;
    public static ArrayList<Producto> productos = new ArrayList<>();
    private static ListaProductos listaProductos;
    
    private ListaProductos(){}
    
    public static ListaProductos getInstance() {           
        if (listaProductos == null) {               
            listaProductos = new ListaProductos();  
        }
                
            return listaProductos;       
    }   
    
    public static ArrayList<Producto> getProductos() {
        return productos;
    }

    public static void setProductos(ArrayList<Producto> productos) {
        ListaProductos.productos = productos;
    }
    
    public void actualizarPrecioProducto(String nombre, int nuevoPrecio) {
        for (Producto producto : productos) {
            if (producto.getNombre().equals(nombre)) {
                producto.setPrecioFinal(nuevoPrecio);
                notifyAllObservers("ACTUALIZAR_PRECIO", producto, nuevoPrecio);
                break;
            }
        }
    }
    
}

