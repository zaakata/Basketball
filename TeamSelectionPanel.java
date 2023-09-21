/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basket;
import javax.swing.*;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;

/**
 *
 * @author gugul
 */
class TeamSelectionPanel extends JPanel {
    private JCheckBox team1CheckBox = new JCheckBox("Mighty Eagles");
    private JCheckBox team2CheckBox = new JCheckBox("Roaring Lions");
    private JCheckBox team3CheckBox = new JCheckBox("Valiant Vipers");
    private JCheckBox team4CheckBox = new JCheckBox("Brave Bears");
    private JButton startButton = new JButton("Start Game");

    public TeamSelectionPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        add(team1CheckBox);
        add(team2CheckBox);
        add(team3CheckBox);
        add(team4CheckBox);
        
        startButton.setEnabled(false);
        startButton.addActionListener(e -> {
            // Handle the start game logic here
            // You can get selected teams by checking which checkboxes are selected
        });
        
        ItemListener itemListener = e -> {
            int selectedCount = 0;
            for (JCheckBox checkBox : new JCheckBox[] {team1CheckBox, team2CheckBox, team3CheckBox, team4CheckBox}) {
                if (checkBox.isSelected()) selectedCount++;
            }
            
            startButton.setEnabled(selectedCount == 2);
        };

        team1CheckBox.addItemListener(itemListener);
        team2CheckBox.addItemListener(itemListener);
        team3CheckBox.addItemListener(itemListener);
        team4CheckBox.addItemListener(itemListener);

        add(startButton);
    }
    
    public List<String> getSelectedTeams() {
        List<String> selectedTeams = new ArrayList<>();
        if (team1CheckBox.isSelected()) selectedTeams.add(team1CheckBox.getText());
        if (team2CheckBox.isSelected()) selectedTeams.add(team2CheckBox.getText());
        if (team3CheckBox.isSelected()) selectedTeams.add(team3CheckBox.getText());
        if (team4CheckBox.isSelected()) selectedTeams.add(team4CheckBox.getText());
        return selectedTeams;
    }
    
    
}

