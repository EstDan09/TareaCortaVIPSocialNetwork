/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Subastas;

/**
 *
 * @author andy2
 */
public interface IObservable {
    public void addObserver(IObserver observer);       
    public void removeObserver(IObserver observer);       
    public void notifyAllObservers(String command, Object source, int precio); 
}
