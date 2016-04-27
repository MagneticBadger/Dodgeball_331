import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.nanoTime;


/**
 * Created by suggs on 20/04/2016.
 * This class was worked on between the both of us.
 */
public class GameMain extends JFrame implements KeyListener {
    private final long framePeriod = 1000000000 / 20;
    public int difficulty = 0, fps = 30, width, height, hulkCounter = 0;
    private double startTime, currentTime;
    private long gameEndTime, runTime;                // done in seconds for checking, minutes:seconds for leaderboard menu
    private boolean isEnded, isWon;
    private final int OPP_RADIUS = 17;
    private static Player player;
    private ImageIcon backgroundII = new ImageIcon("basketball-court.jpeg");
    private Image backgroundImage = backgroundII.getImage();
    private BufferedImage screenBuffer;
    ImageIcon hulkIcon = new ImageIcon("hulk.gif");
    Image hulk = hulkIcon.getImage();
    Image nope;
    private Graphics g;
    private Graphics2D g2;
    Rectangle restartBox;
    ArrayList<Ball> balls = new ArrayList<Ball>();
    AudioClip quack, winner, loser;




    /**
     * Initialize all variables for instance of the game.
     * Worked together on this
     */
    public GameMain() {
        setSize(1000, 750);
        width = getWidth();
        height = getHeight();
        setLocation(250, 250);
        setTitle("Dodgeball");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JFrame difficultyFrame = new JFrame();
        difficultyFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Object[] options = {"Easy", "Medium", "Hard", "Insane"};
        int n = JOptionPane.showOptionDialog(difficultyFrame,
                "Please select your difficulty: ",
                "Difficulty Selection",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        difficulty = n;
        player = new Player(getWidth()/2, getHeight()/2);
        addMouseListener(new MyMouseAdapter());
        addMouseMotionListener(new MyMouseAdapter());
        addKeyListener(this);
        backgroundImage = backgroundImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);


        // generates opponent balls based on difficulty level
        if(difficulty == 0) {
            generateOpponents(4);
        }
        else if(difficulty == 1){
            generateOpponents(4);
        }
        else if(difficulty == 2){
            generateOpponents(6);
        }
        else if(difficulty == 3){
            generateOpponents(6);
        }

        quack = getAudioClip("duck.wav");
        winner = getAudioClip("Party.wav");
        loser = getAudioClip("sad.wav");
        setVisible(true);
        revalidate();
        repaint();
        run();
    }



    /**
     * Calculates the current running time of the program by negating the start time
     * from the current time.
     * Written by Todd Shaughnessy
     * @return
     */
    public void calculateTime() {
        double currentTime = (currentTimeMillis() - startTime)/1000;
        this.currentTime = currentTime;

    }



    /**
     * Instantiates an opponent block to an initial starting location that is passed in via
     * the oppX and oppY fields.
     * Written by Todd Shaughnessy
     * @param numberToAdd
     */
    private void generateOpponents(int numberToAdd) {
        int minVelocity;
        int maxVelocity;
        if(difficulty == 0 | difficulty == 2){
            minVelocity = 15;
            maxVelocity = 25;
        } else {
            minVelocity = 20;
            maxVelocity = 30;
        }

        if (numberToAdd == 4) {
            // Top left ball
            balls.add(new Ball(50 + OPP_RADIUS, 50 + OPP_RADIUS, OPP_RADIUS,
                    minVelocity + Math.random() * (maxVelocity - minVelocity), minVelocity + Math.random() * (maxVelocity - minVelocity)));
            // top right ball
            balls.add(new Ball(getWidth() - 50 - OPP_RADIUS, 50 + OPP_RADIUS, OPP_RADIUS,
                    -(minVelocity + Math.random() * (maxVelocity - minVelocity)), minVelocity + Math.random() * (maxVelocity - minVelocity)));
            // bottom ball
            balls.add(new Ball(50 + OPP_RADIUS, getHeight() - 50 - OPP_RADIUS, OPP_RADIUS + 50,
                    minVelocity + Math.random() * (maxVelocity - minVelocity), -(minVelocity + Math.random() * (maxVelocity - minVelocity))));
            // bottom ball
            balls.add(new Ball(getWidth() - OPP_RADIUS - 50, getHeight() - OPP_RADIUS - 50, OPP_RADIUS,
                    -(minVelocity + Math.random() * (maxVelocity - minVelocity)), -(minVelocity + Math.random() * (maxVelocity - minVelocity))));

        } else if(numberToAdd == 6) {

            // Top left ball
            balls.add(new Ball(20 + OPP_RADIUS, 20 + OPP_RADIUS, OPP_RADIUS,
                    minVelocity + Math.random() * (maxVelocity - minVelocity), minVelocity + Math.random() * (maxVelocity - minVelocity)));

            // top right ball
            balls.add(new Ball(getWidth() - OPP_RADIUS, 20 + OPP_RADIUS, OPP_RADIUS,
                    -(minVelocity + Math.random() * (maxVelocity - minVelocity)), minVelocity + Math.random() * (maxVelocity - minVelocity)));

            // bottom ball
            balls.add(new Ball(20, getHeight() - OPP_RADIUS, OPP_RADIUS + 20,
                    minVelocity + Math.random() * (maxVelocity - minVelocity), -(minVelocity + Math.random() * (maxVelocity - minVelocity))));

            // bottom ball
            balls.add(new Ball(getWidth() - OPP_RADIUS - 20, getHeight() - OPP_RADIUS - 20, OPP_RADIUS,
                    -(minVelocity + Math.random() * (maxVelocity - minVelocity)), -(minVelocity + Math.random() * (maxVelocity - minVelocity))));

            // Top Middle ball
            balls.add(new Ball(getWidth() / 2, OPP_RADIUS, OPP_RADIUS,
                    minVelocity + Math.random() * (maxVelocity - minVelocity), minVelocity + Math.random() * (maxVelocity - minVelocity)));

            // bottom middle ball
            balls.add(new Ball(getWidth() / 2, getHeight() - OPP_RADIUS, OPP_RADIUS,
                    -(minVelocity + Math.random() * (maxVelocity - minVelocity)), (minVelocity + Math.random() * (maxVelocity - minVelocity))));
        }
    }

    /**
     * Written by Michael Suggs
     */
    public void checkCollision() {
        for (Ball b : balls) {
            if (b.collisionBox == null || player.collisionBox == null) {
                continue;
            } else if (player.collisionBox.intersects(b.collisionBox)) {
                if (player.mode.equalsIgnoreCase("HOCKEY") && currentTime >= 15) {
                    player.playerImage = hulk;
                    player.hulkMode();
                    player.hulkStart = (currentTimeMillis() - startTime) / 1000;
                    revalidate();
                    repaint();
                } else if (player.mode.equalsIgnoreCase("HULK") && ((currentTime - player.hulkStart)) <= 5) {
                    if (!b.beenTurned) {
                        b.ballImage = hulk;
                        b.beenTurned = true;
                        hulkCounter++;
                        quack.play();
                    }
                    revalidate();
                    repaint();
                } else {
                    isEnded = true;
                }
            }
        }

    }


    /**
     * Paints the game image to the frame.
     * Worked on this together
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (!isEnded) {
            g2.drawImage(backgroundImage, 0, 0, this);
            g2.drawImage(player.getImage(), player.x, player.y, this);
            player.draw(g);

            for (Ball b : balls) {
                g2.drawImage(b.getImage(), b.x, b.y, this);
                b.draw(g);
            }

            g.setColor(Color.BLACK);
            g.setFont(new Font("Impact", Font.BOLD, 50));
            g.drawString(Double.toString(currentTime), (getWidth() / 2) - 50, 100);

            screenBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            g2.drawImage(screenBuffer, null, 0, 0);
        } else {
            if (!isWon) {
                loser.play();
                g2.setFont(new Font("Impact", Font.BOLD, 50));
                g2.setColor(new Color(192, 192, 192, 127));
                g2.fillRect(250, 250, getWidth() - 500, getHeight() - 500);
                g2.setColor(Color.YELLOW);
                g2.drawString("GAME OVER", (getWidth() / 2) - 130, (getHeight() / 2) - 10);
                //            restartBox = new Rectangle((getWidth() / 2) - 130, (getHeight() / 2) + 10, 250, 40);
                //            g2.draw(restartBox);
                //            g2.drawString("PLAY AGAIN", (getWidth() / 2) - 130, (getHeight() / 2) + 50);
            } else {
                winner.play();
                g2.setFont(new Font("Impact", Font.BOLD, 50));
                g2.setColor(new Color(192, 192, 192, 127));
                g2.fillRect(250, 250, getWidth() - 500, getHeight() - 500);
                g2.setColor(Color.GREEN);
                g2.drawString("YOU WIN", (getWidth() / 2) - 160, (getHeight() / 2) - 10);
            }
        }
    }


    //	/**
    //	 * Runs the game.
    //   * Worked on this together
    //	 */
    private void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}

        startTime = currentTimeMillis();
        long lastUpdateTime = nanoTime();

        while (!isEnded) {
            if (nanoTime() - lastUpdateTime >= framePeriod) {
                lastUpdateTime = nanoTime();
                for (Ball b : balls) {
                    b.move(getWidth(), getHeight());
                }
                revalidate();
                repaint();
                checkCollision();
            }

            calculateTime();

            if (hulkCounter == balls.size()) {
                isWon = true;
                isEnded = true;
            }

            try {
                runTime = System.currentTimeMillis();

                //prevents sleeping for a negative amount of time
                if (fps - (runTime - startTime) > 0)
                    Thread.sleep((long) fps - (long) (runTime - startTime));

            } catch (InterruptedException e) {
            }
        }
        revalidate();
        repaint();
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        new GameMain();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    public class MyMouseAdapter extends MouseAdapter {
        private int mouseX, mouseY;

        @Override
        public void mouseClicked(MouseEvent e) {
//            System.out.print(e.getX()); System.out.print(e.getY());
            if (restartBox.contains(e.getX(), e.getY())) {
                dispose();
                System.gc();
                new GameMain();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
//            System.out.print(e.getX()); System.out.print(e.getY());

            if (player.collisionBox.contains(e.getX(), e.getY())) {
                player.mouseDrag = true;
            }
            mouseX = e.getX();
            mouseY = e.getY();
        }


        @Override
        public void mouseDragged(MouseEvent e) {
            int dx = e.getX() - player.x;
            int dy = e.getY() - player.y;

            if (player.mouseDrag) {
                player.collisionBox.x += dx;
                player.x += dx - 50;
                player.collisionBox.y += dy;
                player.y += dy - 50;
                player.collisionBox = new Rectangle(player.x + 25, player.y + 25, 50, 50);
            }
        }


        @Override
        public void mouseReleased(MouseEvent e) {
            player.mouseDrag = false;
        }


        public void locationUpdate(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (player.mouseDrag){
                System.out.println("Game Over");
                isEnded = true;
            }
        }
    }

    public AudioClip getAudioClip(String filename) {
        URL url = null;
        try {
            File file = new File(filename);
            if (file.canRead())
                url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        if (url == null)
            throw new RuntimeException("audio " + filename + " not found");
        return Applet.newAudioClip(url);
    }

}
