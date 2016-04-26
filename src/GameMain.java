import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.nanoTime;


/**
 * Created by suggs on 20/04/2016.
 */
public class GameMain extends JFrame implements KeyListener {
    Thread thread;
    private final long framePeriod = 1000000000 / 30;
    public int difficulty = 0, fps = 30, width, height;
    private double startTime, currentTime;
    private long gameEndTime, runTime;                // done in seconds for checking, minutes:seconds for leaderboard menu
    private boolean isEnded;
    private final int OPP_RADIUS = 17;
    private static Player player;
    private ImageIcon backgroundII = new ImageIcon("basketball-court.jpeg");
    private Image backgroundImage = backgroundII.getImage();
    private BufferedImage screenBuffer;
    private Graphics g;
    private Graphics2D g2;
    Rectangle restartBox;
    ArrayList<Ball> balls = new ArrayList<Ball>();
    PowerUp[] powerUpArray;

//    JPanel panel = new JPanel(new BorderLayout(), true);



    /**
     * Initialize all variables for instance of the game.
     */
    public GameMain() {
        setSize(1000, 750);
        width = getWidth();
        height = getHeight();
        setLocation(250, 250);
        setTitle("Dodgeball");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        player = new Player(getWidth()/2, getHeight()/2);
        addMouseListener(new MyMouseAdapter());
        addMouseMotionListener(new MyMouseAdapter());
        addKeyListener(this);
        backgroundImage = backgroundImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);


        // init power ups

        // set difficulty - handled in menu
        // if not, jdialog


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

        thread = new Thread();
        thread.start();

        setVisible(true);
        repaint();
        run();
    }


//    public void restart() {
//        System.out.print("Restarty");
//        removeAll();
//        revalidate();
//        isEnded = false;
//        player = new Player(getWidth()/2, getHeight()/2);
////        addMouseListener(new MyMouseAdapter());
////        addMouseMotionListener(new MyMouseAdapter());
////        addKeyListener(this);
//
//        balls.clear();
//
//        // generates opponent balls based on difficulty level
//        if(difficulty == 0) {
//            generateOpponents(4);
//        }
//        else if(difficulty == 1){
//            generateOpponents(4);
//        }
//        else if(difficulty == 2){
//            generateOpponents(6);
//        }
//        else if(difficulty == 3){
//            generateOpponents(6);
//        }
//        repaint();
//        run();
//
//    }


    /**
     * Calculates the current running time of the program by negating the start time
     * from the current time.
     * @return
     */
    public void calculateTime() {
        double currentTime = (currentTimeMillis() - startTime)/1000;
        this.currentTime = currentTime;

    }



    /**
     * Instantiates an opponent block to an initial starting location that is passed in via
     * the oppX and oppY fields.
     * @param numberToAdd
     */
    private void generateOpponents(int numberToAdd) {
        int minVelocity;
        int maxVelocity;
        if(difficulty == 0 | difficulty == 2){
            minVelocity = 10;
            maxVelocity = 20;
        }
        else{
            minVelocity = 15;
            maxVelocity = 25;
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


    public void checkCollision() {
        for (Ball b : balls) {
            if (b.collisionBox == null || player.collisionBox == null) {
                continue;
            } else if (player.collisionBox.intersects(b.collisionBox)) {
                isEnded = true;
            }
        }

    }


    /**
     * Paints the game image to the frame.
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
            g2.setFont(new Font("Impact", Font.BOLD, 50));
            g2.setColor(new Color(192, 192, 192, 127));
            g2.fillRect(250, 250, getWidth() - 500, getHeight() - 500);
            g2.setColor(Color.YELLOW);
            g2.drawString("GAME OVER", (getWidth() / 2) - 130, (getHeight() / 2) - 10);
            restartBox = new Rectangle((getWidth() / 2) - 130, (getHeight() / 2) + 10, 250, 40);
            g2.draw(restartBox);
            g2.drawString("PLAY AGAIN", (getWidth() / 2) - 130, (getHeight() / 2) + 50);
        }
    }


    //	/**
    //	 * Runs the game.
    //	 */
    private void run() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {}

        startTime = currentTimeMillis();
        long lastUpdateTime = nanoTime();

        while (!isEnded) {
            if (nanoTime() - lastUpdateTime >= framePeriod) {
                lastUpdateTime = nanoTime();
                for (Ball b : balls) {
                    b.move(getWidth(), getHeight());
                }
                repaint();
                checkCollision();
            }

            calculateTime();

            try {
                runTime = System.currentTimeMillis();

                //prevents sleeping for a negative amount of time
                if (fps - (runTime - startTime) > 0)
                    Thread.sleep((long) fps - (long) (runTime - startTime));

            } catch (InterruptedException e) {
            }
        }
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
                System.out.print("Clicky");
//                restart();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
//            System.out.print(e.getX()); System.out.print(e.getY());

            if (player.collisionBox.contains(e.getX(), e.getY())) {
                System.out.println("It's in!");
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
}
