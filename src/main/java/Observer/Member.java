/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Observer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Esteban
 */
public interface Member {
    void addObserver(Follower observer);
    void removeObserver(Follower observer);
    void notifyObservers(String message);
}
