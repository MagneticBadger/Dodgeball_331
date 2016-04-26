import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameMenu extends JFrame {
    static JPanel centrePanel;
    //    static JFrame frame;
    public int width, height;

    public GameMenu() {
//        frame = new JFrame();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1000, 750);
        width = getWidth();
        height = getHeight();
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        centrePanel = new MainMenu();
        centrePanel.repaint();
        add("Center", centrePanel);
        setVisible(true);

    }

    public static void main(String[] args) {
        new GameMenu();
    }

    class MainMenu extends JPanel {
        private ImageIcon menuII = new ImageIcon("basketball-court2.jpeg");
        private Image menuBackground = menuII.getImage();
        JButton playButton, leaderboardButton, exitButton;

        public MainMenu() {
            menuBackground = menuBackground.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            playButton = new JButton("PLAY");
            playButton.addActionListener(new MyActionListener());
            leaderboardButton = new JButton("LEADERBOARD");
            exitButton = new JButton("EXIT");
            add(playButton);
            add(leaderboardButton);
            add(exitButton);
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
//                frame.dispose();
                String[] args = new String[]{"123"};
                GameMain.main(args);
            }
        }
    }
}
