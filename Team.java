/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basket;

/**
 *
 * @author gugul
 */
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author gugul
 */
public class Team {
    private String name;
    private ArrayList<Player> players;
    private int score;

    /**
     *
     * @param name
     */
    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();
        this.score = 0;
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }
    
    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param player
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     *
     * @param player
     */
    public void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     *
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     *
     * @param points
     */
    public void addScore(int points) {
        this.score += points;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Team: ").append(name).append("\n");
        sb.append("Players: \n");
        for (Player player : players) {
            sb.append(player.toString()).append("\n");
        }
        return sb.toString();
    }
    
    /**
     *
     * @return
     */
    public Player getRandomPlayer() {
    Random random = new Random();
    return players.get(random.nextInt(players.size()));
}
    
    private int twoPointShots = 0;
private int threePointShots = 0;
private int turnovers = 0;

    /**
     *
     */
    public void addTwoPointShot() {
    twoPointShots++;
    addScore(2);
}

    /**
     *
     */
    public void addThreePointShot() {
    threePointShots++;
    addScore(3);
}

    /**
     *
     */
    public void addTurnover() {
    turnovers++;
}

    /**
     *
     * @return
     */
    public int getTwoPointShots() {
    return twoPointShots;
}

    /**
     *
     * @return
     */
    public int getThreePointShots() {
    return threePointShots;
}

    /**
     *
     * @return
     */
    public int getTurnovers() {
    return turnovers;
}

}
