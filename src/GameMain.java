import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.nanoTime;


/**
 * Created by suggs on 20/04/2016.
 */
public class GameMain extends JFrame {
    Thread thread;
    private final long framePeriod = 1000000000 / 30;
    private int  difficulty, fps = 30;
    private double timeSinceLastFrame, startTime, currentTime;
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
        generateOpponents(4);
        // init power ups

        // set difficulty - handled in menu
        // if not, jdialog
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
//            this.timeSinceLastFrame = currentTime - this.currentTime;
//            for (Ball b: balls){
//                b.bouncingTime -= timeSinceLastFrame;
//            }
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

        if (numberToAdd == 4) {
            // Top left block
            balls.add(new Ball(20 + OPP_RADIUS, 20 + OPP_RADIUS, OPP_RADIUS, 5, 5));
            // top right block
            balls.add(new Ball(getWidth() - OPP_RADIUS, 20 + OPP_RADIUS, OPP_RADIUS, -7, 12));
            // bottom left
            balls.add(new Ball(20, getHeight() - OPP_RADIUS, OPP_RADIUS + 20, 1.8, -5));
            // bottom right
            balls.add(new Ball(getWidth() - OPP_RADIUS - 20, getHeight() - OPP_RADIUS - 20, OPP_RADIUS, -1.8, -4.8));

        } else if(numberToAdd == 6) {
            // Top left block
            balls.add(new Ball(0 + OPP_RADIUS, 0 + OPP_RADIUS, OPP_RADIUS, 50.8, 32.8));
            // top right block
            balls.add(new Ball(getWidth() - OPP_RADIUS, 0 + OPP_RADIUS, OPP_RADIUS, -20.8, 19.8));
            // bottom left
            balls.add(new Ball(15, getHeight() - OPP_RADIUS, OPP_RADIUS, 1.8, -23.8));
            // bottom right
            balls.add(new Ball(getWidth() - OPP_RADIUS, getHeight() - OPP_RADIUS, OPP_RADIUS, -1.8, -34.8));
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

        for(Ball b: balls){
            g2.drawImage(b.getImage(),b.x,b.y,this);
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
        long lastUpdateTime = nanoTime();
        while (!isEnded) {

            if (nanoTime() - lastUpdateTime >= framePeriod) {
                lastUpdateTime = nanoTime();
                for (Ball b : balls) {
                    b.move(getWidth(), getHeight(), timeSinceLastFrame);
                }
                repaint();

            }

            calculateTime();

            try {
                runTime = System.currentTimeMillis();

//              prevents sleeping for a negative amount of time
                if (fps - (runTime - startTime) > 0)
                    Thread.sleep((long) fps - (long) (runTime - startTime));

            } catch (InterruptedException e) {
            }

        }
    }


    public static void pause() {
        // TODO go through all objects and set active -> true/false
    }


    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        new GameMain();

    }
}
