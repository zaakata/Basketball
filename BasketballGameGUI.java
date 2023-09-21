package basket;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author gugul
 */
public class BasketballGameGUI {
    private JTextArea gameLog;
    private JLabel team1Label;
    private JLabel team2Label;
    private JLabel timeLabel;
    private JLabel scoreLabel;
    private JLabel twoPointsLabelTeam1;
    private JLabel twoPointsLabelTeam2;
    private JLabel threePointsLabelTeam1;
    private JLabel threePointsLabelTeam2;
    private JLabel lostLabelTeam1;
    private JLabel lostLabelTeam2;
    private CourtPanel courtPanel;

    /**
     *
     */
    public int musicVolume = 100;
    private Clip musicClip;
    private Team team1;
    private Team team2;
    private Map<Player, JLabel> playerLabelMap = new HashMap<>();
    private JFrame currentFrame = null;

    /**
     *
     */
    public BasketballGameGUI() {

        
        
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            BasketballGameGUI gui = new BasketballGameGUI();
            gui.showMenu();
        });
    }

    /**
     *
     */
    public void showMenu() {
        JFrame frame = new JFrame("Basketball Game Simulator Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        try {
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("C:\\Users\\gugul\\Documents\\NetBeansProjects\\Basket\\src\\basket\\menu.jpg")))));
            File musicFile = new File("C:\\Users\\gugul\\Documents\\NetBeansProjects\\Basket\\src\\basket\\music.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(musicFile);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioIn);
            musicClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        
        


         JButton startGameButton = new JButton("Start Game");
        startGameButton.setMaximumSize(new Dimension(200, 200));
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGameButton.addActionListener(e -> {
    frame.dispose();
    CreateSelectionTeamPanel();
});
        /* startGameButton.addActionListener(e -> {
            frame.dispose();
            createAndShowGUI();
        }); */

        JButton settingsButton = new JButton("Settings");
        settingsButton.setMaximumSize(new Dimension(200, 200));
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.addActionListener(e -> {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, musicVolume);
            volumeSlider.setMajorTickSpacing(20);
            volumeSlider.setPaintTicks(true);
            volumeSlider.setPaintLabels(true);
            panel.add(new JLabel("Głośność muzyki:"));
            panel.add(volumeSlider);

            int result = JOptionPane.showConfirmDialog(frame, panel, "Ustawienia", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                musicVolume = volumeSlider.getValue();
                FloatControl volumeControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
                float range = volumeControl.getMaximum() - volumeControl.getMinimum();
                float gain = (range * musicVolume) / 100 + volumeControl.getMinimum();
                volumeControl.setValue(gain);
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setMaximumSize(new Dimension(200, 200));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));

        frame.add(Box.createVerticalGlue());
        frame.add(startGameButton);
        frame.add(Box.createRigidArea(new Dimension(0, 50)));
        frame.add(settingsButton);
        frame.add(Box.createRigidArea(new Dimension(0, 50)));
        frame.add(exitButton);
        frame.add(Box.createVerticalGlue());

        frame.pack();

        // Full screen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
    
    /**
     *
     */
    public void CreateSelectionTeamPanel() {
    TeamSelectionPanel teamSelectionPanel = new TeamSelectionPanel();
    JFrame selectionFrame = new JFrame("Select Teams");
    selectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    JButton confirmButton = new JButton("Confirm");
    
    confirmButton.addActionListener(e -> {
        List<String> selectedTeams = teamSelectionPanel.getSelectedTeams();
        if (selectedTeams.size() == 2) {
            selectionFrame.dispose();
            createAndShowGUI(selectedTeams.get(0), selectedTeams.get(1));  // Przekazujemy wybrane zespoły do metody createAndShowGUI
        } else {
            JOptionPane.showMessageDialog(selectionFrame, "Please select two teams.");
        }
    });

    selectionFrame.setLayout(new BoxLayout(selectionFrame.getContentPane(), BoxLayout.Y_AXIS));
    selectionFrame.add(teamSelectionPanel);
    selectionFrame.add(confirmButton);

    selectionFrame.pack();
    selectionFrame.setLocationRelativeTo(null); // Wyśrodkowuje okno na ekranie
    selectionFrame.setVisible(true);
}

    /**
     *
     * @param team1Name
     * @param team2Name
     */
    public void createAndShowGUI(String team1Name, String team2Name) {
    this.team1 = new Team(team1Name);
    this.team2 = new Team(team2Name);

    BasketballGame game = new BasketballGame(team1, team2);
    game.setGui(this);
    

    switch (team1Name) {
        case "Mighty Eagles":
            team1.addPlayer(new Player("Smith", "PG", team1Name, 150, 85, 90, 80));
            team1.addPlayer(new Player("Johnson", "SG", team1Name, 150, 87, 85, 80));
            team1.addPlayer(new Player("Williams", "SF", team1Name, 150, 80, 87, 78));
            team1.addPlayer(new Player("Jones", "PF", team1Name, 150, 82, 88, 76));
            team1.addPlayer(new Player("Brown", "C", team1Name, 150, 84, 86, 79));
            break;
        case "Roaring Lions":
            team1.addPlayer(new Player("Davis", "PG", team2Name, 150, 86, 87, 81));
            team1.addPlayer(new Player("Miller", "SG", team2Name, 150, 89, 86, 78));
            team1.addPlayer(new Player("Wilson", "SF", team2Name, 150, 81, 89, 77));
            team1.addPlayer(new Player("Moore", "PF", team2Name, 150, 83, 85, 76));
            team1.addPlayer(new Player("Taylor", "C", team2Name, 150, 85, 88, 75));
            break;
        case "Valiant Vipers":
            team1.addPlayer(new Player("Papastatopulos", "PG", team2Name, 150, 81, 94, 81));
            team1.addPlayer(new Player("Anthony", "SG", team2Name, 150, 89, 86, 78));
            team1.addPlayer(new Player("Decker", "SF", team2Name, 150, 81, 89, 77));
            team1.addPlayer(new Player("Styles", "PF", team2Name, 150,83, 85, 76));
            team1.addPlayer(new Player("Eastbrook", "C", team2Name, 150, 85, 88, 75));
            break;
        case "Brave Bears":
            team1.addPlayer(new Player("Kim", "PG", team2Name, 150, 86, 87, 81));
            team1.addPlayer(new Player("Ngonbele", "SG", team2Name, 150, 89, 86, 78));
            team1.addPlayer(new Player("Mathos", "SF", team2Name, 150, 81, 89, 77));
            team1.addPlayer(new Player("Vukic", "PF", team2Name, 150, 83, 85, 76));
            team1.addPlayer(new Player("Maitland", "C", team2Name, 150, 85, 88, 75));
            break;
    }

    switch (team2Name) {
        case "Mighty Eagles":
            team2.addPlayer(new Player("Smith", "PG", team1Name, 150, 85, 90, 80));
            team2.addPlayer(new Player("Johnson", "SG", team1Name, 150, 87, 85, 80));
            team2.addPlayer(new Player("Williams", "SF", team1Name, 150, 80, 87, 78));
            team2.addPlayer(new Player("Jones", "PF", team1Name, 150, 82, 88, 76));
            team2.addPlayer(new Player("Brown", "C", team1Name, 150, 84, 86, 79));
            break;
        case "Roaring Lions":
            team2.addPlayer(new Player("Davis", "PG", team2Name, 150,86, 87, 81));
            team2.addPlayer(new Player("Miller", "SG", team2Name, 150, 89, 86, 78));
            team2.addPlayer(new Player("Wilson", "SF", team2Name, 150, 81, 89, 77));
            team2.addPlayer(new Player("Moore", "PF", team2Name, 150, 83, 85, 76));
            team2.addPlayer(new Player("Taylor", "C", team2Name, 150, 85, 88, 75));
            break;
        case "Valiant Vipers":
            team2.addPlayer(new Player("Papastatopulos", "PG", team2Name, 150, 81, 94, 81));
            team2.addPlayer(new Player("Anthony", "SG", team2Name, 150, 89, 86, 78));
            team2.addPlayer(new Player("Decker", "SF", team2Name, 150, 81, 89, 77));
            team2.addPlayer(new Player("Styles", "PF", team2Name, 150, 83, 85, 76));
            team2.addPlayer(new Player("Eastbrook", "C", team2Name, 150, 85, 88, 75));
            break;
        case "Brave Bears":
            team2.addPlayer(new Player("Kim", "PG", team2Name, 150, 86, 87, 81));
            team2.addPlayer(new Player("Ngonbele", "SG", team2Name, 150, 89, 86, 78));
            team2.addPlayer(new Player("Mathos", "SF", team2Name, 150, 81, 89, 77));
            team2.addPlayer(new Player("Vukic", "PF", team2Name, 150, 83, 85, 76));
            team2.addPlayer(new Player("Maitland", "C", team2Name, 150, 85, 88, 75));
            break;
    
    }


    JFrame frame = new JFrame("Basketball Game Simulator");
    currentFrame = frame;
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    

    // General Font
    Font generalFont = new Font("Arial", Font.PLAIN, 20);

    // Top Panel
    JPanel topPanel = new JPanel(new BorderLayout());
    JPanel scorePanel = new JPanel(new GridLayout(1, 4));

    team1Label = new JLabel(team1Name);
    team2Label = new JLabel(team2Name);
    timeLabel = new JLabel("0m 0s");
    scoreLabel = new JLabel("0 - 0");
    JLabel[] labels = {team1Label, scoreLabel, team2Label, timeLabel};

    for (JLabel label : labels) {
        label.setFont(generalFont);
        scorePanel.add(label);
    }
    
    
    


    topPanel.add(scorePanel, BorderLayout.NORTH);
    topPanel.add(new JSeparator(), BorderLayout.CENTER);
    frame.add(topPanel, BorderLayout.NORTH);

    // Middle Panel
    JPanel middlePanel = new JPanel(new BorderLayout());
    courtPanel = new CourtPanel(1600, 650); 
    courtPanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
    
    
JPanel centerPanel = new JPanel();
centerPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering
centerPanel.add(courtPanel);

    middlePanel.add(courtPanel, BorderLayout.CENTER);
    frame.add(middlePanel, BorderLayout.CENTER);

    // Stats Panel
    JPanel statsPanel = createStatsPanel(generalFont);
    middlePanel.add(statsPanel, BorderLayout.EAST);

    // Teams Panel
    JPanel teamsPanel = new JPanel(new GridLayout(2, 1));
    teamsPanel.add(createTeamPanel(team1));
    teamsPanel.add(createTeamPanel(team2));

    middlePanel.add(teamsPanel, BorderLayout.WEST);

    // Bottom Panel
    gameLog = new JTextArea(20, 50);
    gameLog.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(gameLog);
    frame.add(scrollPane, BorderLayout.SOUTH);

    frame.pack();
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setVisible(true);

    
    game.startSimulation();
}




private JPanel createTeamPanel(Team team) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        JLabel teamLabel = new JLabel("Team: " + team.getName());
        teamLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(teamLabel);

        JLabel playersLabel = new JLabel("Players:");
        playersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(playersLabel);

        for (Player player : team.getPlayers()) {
            JLabel playerLabel = new JLabel(player.toString());
            playerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(playerLabel);
            playerLabelMap.put(player, playerLabel);  // dodajemy do mapy
        }

        panel.add(Box.createVerticalGlue());

        return panel;
    }

private JPanel createStatsPanel(Font font) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    panel.add(Box.createVerticalGlue());

    JLabel team1NameLabel = new JLabel("Drużyna: " + team1.getName());
    team1NameLabel.setFont(font);
    team1NameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(team1NameLabel);

    // Dla drużyny 1
    twoPointsLabelTeam1 = new JLabel("Rzuty za 2: " + team1.getTwoPointShots());
    twoPointsLabelTeam1.setFont(font);
    twoPointsLabelTeam1.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(twoPointsLabelTeam1);

    threePointsLabelTeam1 = new JLabel("Rzuty za 3: " + team1.getThreePointShots());
    threePointsLabelTeam1.setFont(font);
    threePointsLabelTeam1.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(threePointsLabelTeam1);

    lostLabelTeam1 = new JLabel("Straty: " + team1.getTurnovers());
    lostLabelTeam1.setFont(font);
    lostLabelTeam1.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(lostLabelTeam1);

    panel.add(Box.createVerticalGlue());

    JLabel team2NameLabel = new JLabel("Drużyna: " + team2.getName());
    team2NameLabel.setFont(font);
    team2NameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(team2NameLabel);

    // Dla drużyny 2
    twoPointsLabelTeam2 = new JLabel("Rzuty za 2: " + team2.getTwoPointShots());
    twoPointsLabelTeam2.setFont(font);
    twoPointsLabelTeam2.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(twoPointsLabelTeam2);

    threePointsLabelTeam2 = new JLabel("Rzuty za 3: " + team2.getThreePointShots());
    threePointsLabelTeam2.setFont(font);
    threePointsLabelTeam2.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(threePointsLabelTeam2);

    lostLabelTeam2 = new JLabel("Straty: " + team2.getTurnovers());
    lostLabelTeam2.setFont(font);
    lostLabelTeam2.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(lostLabelTeam2);

    panel.add(Box.createVerticalGlue());

    return panel;
}

    /**
     *
     * @param text
     */
    public void updateGameLog(String text) {
        gameLog.insert(text + "\n", 0);
    }

    /**
     *
     * @param text
     */
    public void updateTimeLabel(String text) {
        timeLabel.setText(text);
    }
    
    /**
     *
     * @param text
     */
    public void updateScoreLabel(String text) {
        scoreLabel.setText(text);
    }

    /**
     *
     * @param text
     */
    public void updateLabelTwoTeam1(String text) {
    twoPointsLabelTeam1.setText(text);
}

    /**
     *
     * @param text
     */
    public void updateLabelThreeTeam1(String text) {
    threePointsLabelTeam1.setText(text);
}

    /**
     *
     * @param text
     */
    public void updateLabelLostTeam1(String text) {
    lostLabelTeam1.setText(text);
}

    /**
     *
     * @param text
     */
    public void updateLabelTwoTeam2(String text) {
    twoPointsLabelTeam2.setText(text);
}

    /**
     *
     * @param text
     */
    public void updateLabelThreeTeam2(String text) {
    threePointsLabelTeam2.setText(text);
}

    /**
     *
     * @param text
     */
    public void updateLabelLostTeam2(String text) {
    lostLabelTeam2.setText(text);
}

    /**
     *
     * @param x
     * @param y
     */
    public void updateEventCoordinates(int x, int y) {
        courtPanel.setEventCoordinates(x, y);
        courtPanel.repaint();
    }

    /**
     *
     * @param player
     */
    public void updatePlayerInfo(Player player) {
        JLabel playerLabel = playerLabelMap.get(player);
        if (playerLabel != null) {
            playerLabel.setText(player.toString());
            if (player.getStamina() < 50) {
                playerLabel.setForeground(Color.RED);
            } else {
                playerLabel.setForeground(Color.BLACK);
            }
        }
    }

    /**
     *
     */
    public void showEndGameResult() {
    JDialog endGameDialog = new JDialog();
    endGameDialog.setTitle("Game Over");
    endGameDialog.setLayout(new BorderLayout());
    endGameDialog.setModal(true);  // make the dialog modal, blocking other inputs

    JLabel resultLabel = new JLabel("Final Score: " + team1.getName() + " " + team1.getScore() + " - " + team2.getScore() + " " + team2.getName());
    resultLabel.setHorizontalAlignment(JLabel.CENTER);
    resultLabel.setFont(new Font("Arial", Font.BOLD, 30));
    endGameDialog.add(resultLabel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();

    JButton okButton = new JButton("OK");
    okButton.addActionListener(e -> endGameDialog.dispose());
    buttonPanel.add(okButton);

    JButton returnToMenuButton = new JButton("Return to Menu");
returnToMenuButton.addActionListener(e -> {
    if (currentFrame != null) {
        currentFrame.dispose();
        currentFrame = null;
    }
    endGameDialog.dispose(); 
    showMenu();
});
    buttonPanel.add(returnToMenuButton);

    endGameDialog.add(buttonPanel, BorderLayout.SOUTH);
    endGameDialog.pack();
    endGameDialog.setLocationRelativeTo(null);  // center the dialog on the screen
    endGameDialog.setVisible(true);
}

}

