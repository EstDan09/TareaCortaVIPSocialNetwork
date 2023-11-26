/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Subastas;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andy2
 */

public abstract class AbstractObservable implements IObservable{
    private final List<IObserver> observers = new ArrayList<>();
          
    @Override       
    public void addObserver(IObserver observer) {           
        this.observers.add(observer);
    }                 
    
    @Override       
    public void removeObserver(IObserver observer) {           
        this.observers.remove(observer);
    }          
    
    @Override       
    public void notifyAllObservers(String command, Object source, int precio) {           
        for (IObserver observer : observers) {               
            observer.notifyObserver(command, source, precio);
        }
               
    }
}
