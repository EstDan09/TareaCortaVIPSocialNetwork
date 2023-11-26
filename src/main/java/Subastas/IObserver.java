/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Subastas;

/**
 *
 * @author andy2
 */

public interface IObserver {
    public void notifyObserver(String command, Object source, int precio);
}
