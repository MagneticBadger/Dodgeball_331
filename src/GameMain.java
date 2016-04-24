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
    private int  difficulty = 0, fps = 30;
    private double startTime, currentTime;
    private long gameEndTime, runTime;                // done in seconds for checking, minutes:seconds for leaderboard menu
    private boolean isPaused, isEnded;
    private final int OPP_RADIUS = 17;
    private static Player player;
    private Image screenBuffer;
    private Graphics g;
    ArrayList<Ball> balls = new ArrayList<Ball>();
    PowerUp[] powerUpArray;
//    JPanel panel = new JPanel(new BorderLayout(), true);



    /**
     * Initialize all variables for instance of the game.
     */
    public GameMain() {
        setSize(750, 750);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        player = new Player(getWidth()/2, getHeight()/2);
        addMouseListener(new MyMouseAdapter());
        addMouseMotionListener(new MyMouseAdapter());
        addKeyListener(this);

        // init power ups

        // set difficulty - handled in menu
        // if not, jdialog
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
        run();
    }


    /**
     * Calculates the current running time of the program by negating the start time
     * from the current time.
     * @return
     */
    public void calculateTime() {
        if(isPaused == false) {
            double currentTime = (currentTimeMillis() - startTime)/1000;
            this.currentTime = currentTime;
        }
    }



//	public void placePowerUp(int powerX, int powerY) {
//		// Has RNG to determine power-up
//		PowerUp power = new PowerUp(powerX, powerY, -1);
//	}


    /**
     * Instantiates an opponent block to an initial starting location that is passed in via
     * the oppX and oppY fields.
     * @param numberToAdd
     */
    private void generateOpponents(int numberToAdd) {
        int minVelocity;
        int maxVelocity;
        if(difficulty == 0 | difficulty == 2){
            minVelocity = 8;
            maxVelocity = 20;
        }
        else{
            minVelocity = 12;
            maxVelocity = 30;
        }

        if (numberToAdd == 4) {
            // Top left block
            balls.add(new Ball(20 + OPP_RADIUS, 20 + OPP_RADIUS, OPP_RADIUS,
                    minVelocity + Math.random() * (maxVelocity - minVelocity), minVelocity + Math.random() * (maxVelocity - minVelocity)));
            // top right block
            balls.add(new Ball(getWidth() - OPP_RADIUS, 20 + OPP_RADIUS, OPP_RADIUS,
                    -(minVelocity + Math.random() * (maxVelocity - minVelocity)), minVelocity + Math.random() * (maxVelocity - minVelocity)));
            // bottom left
            balls.add(new Ball(20, getHeight() - OPP_RADIUS, OPP_RADIUS + 20,
                    minVelocity + Math.random() * (maxVelocity - minVelocity), -(minVelocity + Math.random() * (maxVelocity - minVelocity))));
            // bottom right
            balls.add(new Ball(getWidth() - OPP_RADIUS - 20, getHeight() - OPP_RADIUS - 20, OPP_RADIUS,
                    -(minVelocity + Math.random() * (maxVelocity - minVelocity)), -(minVelocity + Math.random() * (maxVelocity - minVelocity))));

        } else if(numberToAdd == 6) {

            // Top left block
            balls.add(new Ball(20 + OPP_RADIUS, 20 + OPP_RADIUS, OPP_RADIUS,
                    minVelocity + Math.random() * (maxVelocity - minVelocity), minVelocity + Math.random() * (maxVelocity - minVelocity)));

            // top right block
            balls.add(new Ball(getWidth() - OPP_RADIUS, 20 + OPP_RADIUS, OPP_RADIUS,
                    -(minVelocity + Math.random() * (maxVelocity - minVelocity)), minVelocity + Math.random() * (maxVelocity - minVelocity)));

            // bottom left
            balls.add(new Ball(20, getHeight() - OPP_RADIUS, OPP_RADIUS + 20,
                    minVelocity + Math.random() * (maxVelocity - minVelocity), -(minVelocity + Math.random() * (maxVelocity - minVelocity))));

            // bottom right
            balls.add(new Ball(getWidth() - OPP_RADIUS - 20, getHeight() - OPP_RADIUS - 20, OPP_RADIUS,
                    -(minVelocity + Math.random() * (maxVelocity - minVelocity)), -(minVelocity + Math.random() * (maxVelocity - minVelocity))));

            // Top Middle block
            balls.add(new Ball(getWidth() / 2, OPP_RADIUS, OPP_RADIUS,
                    minVelocity + Math.random() * (maxVelocity - minVelocity), minVelocity + Math.random() * (maxVelocity - minVelocity)));

            // bottom middle block
            balls.add(new Ball(getWidth() / 2, getHeight() - OPP_RADIUS, OPP_RADIUS,
                    -(minVelocity + Math.random() * (maxVelocity - minVelocity)), (minVelocity + Math.random() * (maxVelocity - minVelocity))));
        }
    }


    public void checkCollision() {
        for (Ball b : balls) {
            if (player.collisionBox.intersects(b.collisionBox)) {
                System.out.print("GAME OVER");
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
        g2.setColor(new Color(255, 165, 79));
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2.drawImage(player.getImage(), player.x, player.y, this);
        player.draw(g);

        for(Ball b: balls){
            g2.drawImage(b.getImage(),b.x,b.y,this);
            b.draw(g);
        }
        // TODO Draw power-ups
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, 50));
        g.drawString(Double.toString(currentTime), (getWidth()/2) - 50, 100);

        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        g2.drawImage(bufferedImage, null, 0, 0);
    }


    /**
     * Calls paint and all associated methods.
     */
    @Override
    public void update(Graphics g) {
        paint(g);
    }


    //	/**
    //	 * Runs the game.
    //	 */
    //	public void run() {
    //
    //	}
    private void run() {
        startTime = currentTimeMillis();
        long lastUpdateTime = nanoTime();
        long pauseStart;
        long pauseEnd;
        while (!isEnded) {

            while (isPaused) {

            }

            if (nanoTime() - lastUpdateTime >= framePeriod) {
                lastUpdateTime = nanoTime();
                for (Ball b : balls) {
                    b.move(getWidth(), getHeight());
                }
                checkCollision();
                repaint();

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
    }


    public void pause() {
        // TODO go through all objects and set active -> true/false
        if (isPaused == false) {
            isPaused = true;
        } else {
            isPaused = false;
        }
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
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.print("Pausey pausey");
            pause();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    public class MyMouseAdapter extends MouseAdapter {
        private int mouseX, mouseY;

        @Override
        public void mouseClicked(MouseEvent e) {
//            System.out.print(e.getX()); System.out.print(e.getY());
            if (player.collisionBox.contains(e.getX(), e.getY())) {
                System.out.print("It's in!");
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
//            System.out.print(e.getX()); System.out.print(e.getY());
            player.mouseDrag = true;
            if (player.collisionBox.contains(e.getX(), e.getY())) {
                System.out.print("It's in!");
            }
            mouseX = e.getX();
            mouseY = e.getY();
        }


        @Override
        public void mouseDragged(MouseEvent e) {
            int dx = e.getX() - player.x;
            int dy = e.getY() - player.y;

            if (player.collisionBox.getBounds2D().contains(player.x, player.y)) {
                player.collisionBox.x += dx;
                player.x += dy;
                player.collisionBox.y += dy;
                player.y += dy;
            }
            player.x += dx - 50;
            player.y += dy - 50;
        }


        @Override
        public void mouseReleased(MouseEvent e) {
            player.mouseDrag = false;
        }


        public void locationUpdate(MouseEvent e) {

        }

    }
}
