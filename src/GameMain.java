import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;


/**
 * Created by suggs on 20/04/2016.
 */
public class GameMain extends JFrame {
    Thread thread;
    private int startTime, difficulty, fps = 30;
    private long currentTime, gameEndTime, runTime;                // done in seconds for checking, minutes:seconds for leaderboard menu
    private boolean isPaused, isEnded;
    private final int OPP_RADIUS = 50;
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
        player = new Player(getWidth()/2, getHeight()/2);
        generateOpponents(0);
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
        while(isPaused = false) {
            currentTime = currentTimeMillis() - startTime;
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

        if (numberToAdd == 0) {
            // Top left block
            balls.add(new Ball(15, 0 + OPP_RADIUS, OPP_RADIUS, 9.8, 9.8));
            // top right block
            balls.add(new Ball(getWidth() - OPP_RADIUS, 0 + OPP_RADIUS, OPP_RADIUS, 9.8, -9.8));
            // bottom left
            balls.add(new Ball(15, getHeight() - OPP_RADIUS, OPP_RADIUS, -9.8, 9.8));
            // bottom right
            balls.add(new Ball(getWidth() - OPP_RADIUS, getHeight() - OPP_RADIUS, OPP_RADIUS, -9.8, -9.8));

        } else {
            // TODO Not working now - needs placement values
            for (int i = 0; i < numberToAdd; i++) {
                balls.add(new Ball(0, 0, 0, 0, 0));
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

        for(Ball b: balls){
            g2.drawImage(b.getImage(),b.x,b.y,this);
        }
        // TODO Draw power-ups

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
        while (true) {
            repaint();

            try {
                runTime = System.currentTimeMillis();

                // prevents sleeping for a negative amount of time
                if (fps - (runTime - startTime) > 0)
                    Thread.sleep(fps - (runTime - startTime));

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
