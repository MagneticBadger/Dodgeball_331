import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameMenu {
    static JPanel centrePanel;
    static JFrame frame;
    JButton playButton, leaderboardButton, exitButton;

    public GameMenu() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 750);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        centrePanel = new MainMenu();
        centrePanel.repaint();
        playButton = new JButton("PLAY");
        playButton.addActionListener(new MyActionListener());
        leaderboardButton = new JButton("LEADERBOARD");
        exitButton = new JButton("EXIT");
        centrePanel.add(playButton);
        centrePanel.add(leaderboardButton);
        centrePanel.add(exitButton);
        frame.add("Center", centrePanel);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new GameMenu();
    }

    class MainMenu extends JPanel {
        private ImageIcon menuII = new ImageIcon("basketball-court2.jpeg");
        private Image menuBackground = menuII.getImage();

        public MainMenu() {
            menuBackground = menuBackground.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(menuBackground, 0, 0, this);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Impact", Font.BOLD, 72));
            g.drawString("Dodgeball", (getWidth() / 2) - 160, getHeight() / 2 - 100);
        }
    }

    class LeaderBoard extends JPanel {


        public LeaderBoard() {
            setBackground(Color.black);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.ORANGE);
            g.setFont(new Font("Impact", Font.BOLD, 72));
            g.drawString("GAME", (getWidth() / 2) - 70, getHeight() / 2);
        }
    }

    class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String label = e.getActionCommand();

            if (label == "PLAY") {
                frame.dispose();
                GameMain dogeball = new GameMain();
            }
        }
    }
}
