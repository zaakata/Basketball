/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package basket;

/**
 *
 * @author gugul
 */

import java.util.List;
import java.util.Random;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class BasketballGame {
    private Team team1;
    private Team team2;
    private BasketballGameGUI gui;
    private static final int courtWidth = 1600;
    private static final int courtHeight = 650;
     private int simulationSpeed = 1000; // dodane
    private Clip musicClip; // dodane
    private List<Team> teams;
    int mod;

    /**
     *
     * @param simulationSpeed
     */
    public void setSimulationSpeed(int simulationSpeed) {
    this.simulationSpeed = simulationSpeed;
}

    /**
     *
     * @param team1
     * @param team2
     */
    public BasketballGame(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
        
    }
    
    /**
     *
     */
    public void startSimulation() {
        new Thread(this::simulateGame).start();
    }

    /**
     *
     * @param gui
     */
    public void setGui(BasketballGameGUI gui) {
        this.gui = gui;
    }
    
    /**
     *
     */
    public void simulateGame() {
        for (int quarter = 1; quarter <= 4; quarter++) {
            gui.updateGameLog("Kwarta " + quarter + ":");
            simulateQuarter();
            gui.updateGameLog("Podsumowanie po " + quarter + " kwarcie:");
            gui.updateScoreLabel("Wynik: " + team1.getName() + " " + team1.getScore() + " - " + team2.getScore() + " " + team2.getName());
            gui.updateGameLog("");

            if (quarter < 4) {
                gui.updateGameLog("Przerwa minutowa.");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gui.updateGameLog("");
            }
        }
        gui.showEndGameResult();
    }

    private void simulateQuarter() {
    Random random = new Random();
    int quarterDuration = 600; // 10 minut * 60 sekundy
    int time = 0;
    boolean team1Turn = true;

    while (time < quarterDuration) {
        int actionDuration = random.nextInt(15) + 10;
        time += actionDuration;
        int x = random.nextInt(courtWidth);
        int y = random.nextInt(courtHeight);
        gui.updateEventCoordinates(x, y);

        if (time > quarterDuration) {
            time = quarterDuration;
        }

        int minutes = time / 60;
        int seconds = time % 60;
        gui.updateTimeLabel("Czas: " + minutes + "m " + seconds + "s");

        
        Team currentTeam = team1Turn ? team1 : team2;
        Team opponentTeam = (currentTeam == team1) ? team2 : team1;
        Player attacker = currentTeam.getRandomPlayer();
        Player deffender = opponentTeam.getRandomPlayer();
        mod = random.nextInt(100) + ((attacker.getAttack()/2)+(attacker.getRating()/2) - deffender.getDefense());
        gui.updateGameLog(currentTeam.getName() + " ma piłkę. (" + attacker.getLastName() + ")");
            
        if (mod < 30) {
            gui.updateGameLog(currentTeam.getName() + " zdobywa 2 punkty! (" + attacker.getLastName() + ")");
            currentTeam.addTwoPointShot();
            if (team1Turn) {
                gui.updateLabelTwoTeam1("Rzuty za 2: " + currentTeam.getTwoPointShots() + "");
            } else {
                gui.updateLabelTwoTeam2("Rzuty za 2: " + currentTeam.getTwoPointShots() + "");
            }
        } else if (mod < 60) {
            gui.updateGameLog(currentTeam.getName() + " zdobywa 3 punkty! (" + attacker.getLastName() + ")");
            currentTeam.addThreePointShot();
            if (team1Turn) {
                gui.updateLabelThreeTeam1("Rzuty za 3: " + currentTeam.getThreePointShots() + "");
            } else {
                gui.updateLabelThreeTeam2("Rzuty za 3: " + currentTeam.getThreePointShots() + "");
            }
        } else {
            gui.updateGameLog(currentTeam.getName() + " traci piłkę! (" + attacker.getLastName() + ")");
            currentTeam.addTurnover();
            if (team1Turn) {
                gui.updateLabelLostTeam1("Straty: " + currentTeam.getTurnovers() + "");
            } else {
                gui.updateLabelLostTeam2("Straty: " + currentTeam.getTurnovers() + "");
            }
        }
        gui.updateScoreLabel(team1.getScore() + " - " + team2.getScore());
        applyEnduranceToAllPlayers();
        for (Player player : team1.getPlayers()) {
    gui.updatePlayerInfo(player);
}
for (Player player : team2.getPlayers()) {
    gui.updatePlayerInfo(player);
}
                
        try {
            Thread.sleep(simulationSpeed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gui.updateGameLog("");
        team1Turn = !team1Turn;
    }
}
    
    /**
     *
     * @param volume
     */
    public void adjustMusicVolume(int volume) {
        FloatControl gainControl = 
            (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) / 100.0f;
        gainControl.setValue(gainControl.getMinimum() + gain);
    }
    
    private void applyEnduranceToAllPlayers() {
    Random random = new Random();
    for (Player player : team1.getPlayers()) {
        int staminaLoss = random.nextInt(2);
        player.simulateEndurance(staminaLoss);
    }
    for (Player player : team2.getPlayers()) {
        int staminaLoss = random.nextInt(2);
        player.simulateEndurance(staminaLoss);
    }
}

}
    









