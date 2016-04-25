import javax.swing.*;
import java.awt.*;

public class GameOver {
    static JPanel centrePanel;
    static JFrame frame;
    JButton restart;

    public GameOver() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 750);
        frame.setLayout(new BorderLayout());
        centrePanel = new MyCentre();
        centrePanel.repaint();
        restart = new JButton("Restart");
        frame.add("Center", centrePanel);
        JPanel restartPanel = new JPanel();
        restartPanel.add(restart);
        restartPanel.setBackground(Color.black);
        frame.add("South", restartPanel);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GameOver();
    }

    class MyCentre extends JPanel {


        public MyCentre() {
            setBackground(Color.black);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.ORANGE);
            g.setFont(new Font("Impact", Font.BOLD, 72));
            g.drawString("GAME OVER", (getWidth() / 2) - 70, getHeight() / 2);
        }
    }
}