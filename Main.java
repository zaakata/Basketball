/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basket;

import javax.swing.SwingUtilities;

/**
 *
 * @author gugul
 */
public class Main {
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BasketballGameGUI gui = new BasketballGameGUI();
            gui.showMenu();
            gui.showEndGameResult();
        });
    }
}
