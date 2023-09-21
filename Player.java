/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basket;


import java.awt.List;
import java.util.ArrayList;

/**
 *
 * @author gugul
 */
public class Player {
    private int stamina;
    private int rating;
    private int attack;
    private int defense;
    private String lastName;
    private String position;
    private String team;



    // Metoda do zmiany oceny gracza:

    /**
     *
     * @param newRating
     */
    public void changeRating(int newRating) {
        this.rating = newRating;
        
    }


    // Metoda do zmiany ilości staminy

    /**
     *
     * @param newStamina
     */
    public void changeStamina(int newStamina) {
        this.stamina = newStamina;
        
    }

    // Dodatkowo, możesz chcieć dodać metody pobierające do odczytywania wartości tych pól



    // Teraz, za każdym razem, gdy statystyki gracza zmieniają się, musimy wywołać notifyObservers()
    // Na przykład, tutaj jest metoda do zmiany oceny gracza:

    /**
     *
     * @param lastName
     * @param position
     * @param team
     * @param stamina
     * @param rating
     * @param attack
     * @param defense
     */



    public Player(String lastName, String position, String team,int stamina, int rating, int attack, int defense ) {
        this.stamina = stamina;
        this.rating = rating;
        this.lastName = lastName;
        this.position = position;
        this.team = team;
        this.attack = attack;
        this.defense = defense;
    }

    /**
     *
     * @return
     */
    public int getRating() {
        return rating;
    }

    /**
     *
     * @return
     */
    public int getStamina() {
        return stamina;
    }
    
    /**
     *
     * @return
     */
    public int getAttack() {
        return attack;
    }

    /**
     *
     * @return
     */
    public int getDefense() {
        return defense;
    }
    
    /**
     *
     * @param stamina
     */
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    /**
     *
     * @param rating
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     */
    public String getPosition() {
        return position;
    }

    /**
     *
     * @param position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     *
     * @return
     */
    public String getTeam() {
        return team;
    }

    /**
     *
     * @param team
     */
    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return 
             " " + stamina + " " +
                 lastName + " " +
                 rating;

    }
    
    /**
     *
     * @param staminaLoss
     */
    public void simulateEndurance(int staminaLoss) {
    this.stamina -= staminaLoss;

    if (this.stamina < 0) {
        this.stamina = 0;
    }

    int statsReduction = 0;
    if (this.stamina == 140) statsReduction += 3;
    if (this.stamina == 130) statsReduction += 3;
    if (this.stamina == 120) statsReduction += 3;
    if (this.stamina == 110) statsReduction += 3;
    if (this.stamina == 100) statsReduction += 3;
    if (this.stamina == 90) statsReduction += 3;
    if (this.stamina == 80) statsReduction += 3;
    if (this.stamina == 70) statsReduction += 3;
    if (this.stamina == 60) statsReduction += 3;
    if (this.stamina == 50) statsReduction += 3;
    if (this.stamina == 40) statsReduction += 3;
    if (this.stamina == 30) statsReduction += 3;
    if (this.stamina == 20) statsReduction += 3;
    if (this.stamina == 10) statsReduction += 3;
    if (this.stamina == 5) statsReduction += 3;
    

    if (this.stamina % 10 == 0 && this.stamina <= 140 && this.stamina >= 10) {
    this.stamina -= 1;
    }

    this.rating -= statsReduction;
    if (this.rating < 0) {
        this.rating = 0;
    }

    this.attack -= statsReduction;
    if (this.attack < 0) {
        this.attack = 0;
    }

    this.defense -= statsReduction;
    if (this.defense < 0) {
        this.defense = 0;
    }
}
    
    
}
