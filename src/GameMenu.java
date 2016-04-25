import javax.swing.*;
import java.awt.*;


public class GameMenu extends JPanel {

    public void GameMenu(){
        setLayout(new BorderLayout());
        ImageIcon icon = new ImageIcon("StartButton.png");
        JButton start = new JButton(icon);
        JButton leaderboard = new JButton(icon);
        JButton exit = new JButton(icon);
        this.add(start);
        this.add(leaderboard);
        this.add(exit);
    }




}
