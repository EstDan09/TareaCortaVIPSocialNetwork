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
public class Producto extends AbstractObservable implements Serializable{
    private static final long serialVersionUID = 1L;
    private int PrecioInicial;
    private int PrecioFinal;
    private String nombre;
    private String descripcion;
    private int PrecioActualSubasta;

    public Producto(int PrecioInicial, int PrecioFinal, String nombre, String descripcion) {
        this.PrecioInicial = PrecioInicial;
        this.PrecioFinal = PrecioFinal;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.PrecioActualSubasta = PrecioInicial;
    }

    public int getPrecioActualSubasta() {
        return PrecioActualSubasta;
    }

    public void setPrecioActualSubasta(int PrecioActualSubasta) {
        this.PrecioActualSubasta = PrecioActualSubasta;
    }
    
    public void actualizarPrecioSubasta(int nuevoPrecio) {
        this.PrecioActualSubasta = nuevoPrecio;
        notifyAllObservers("ACTUALIZAR_SUBASTA", this, nuevoPrecio);
    }

    public int getPrecioInicial() {
        return PrecioInicial;
    }

    public void setPrecioInicial(int PrecioInicial) {
        this.PrecioInicial = PrecioInicial;
    }

    public int getPrecioFinal() {
        return PrecioFinal;
    }

    public void setPrecioFinal(int PrecioFinal) {
        this.PrecioFinal = PrecioFinal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
