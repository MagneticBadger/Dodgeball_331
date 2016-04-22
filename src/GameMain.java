import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;


/**
 * Created by suggs on 20/04/2016.
 */
public class GameMain extends JFrame {
    public int startTime, difficulty;
    public double currentTime, gameEndTime;				// done in seconds for checking, minutes:seconds for leaderboard menu
    public boolean isPaused, isEnded;
    protected final int OPP_RADIUS = 50;
    public static Player player;
    ArrayList<Ball> balls = new ArrayList<Ball>();
    PowerUp[] powerUpArray;



    /**
     * Initialize all variables for instance of the game.
     */
    public GameMain() {
        setSize(1600, 900);
        player = new Player(getWidth()/2, getHeight()/2);
        generateOpponents(0);
        // init power ups

        // set difficulty - handled in menu
        // if not, jdialog

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
            double currentTime = currentTimeMillis() - startTime;
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
    public void generateOpponents(int numberToAdd) {

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
        g.setColor(new Color(255,165,79));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2.drawImage(player.getImage(), player.x, player.y, this);

        for(Ball b: balls){
            g2.drawImage(b.getImage(),b.x,b.y,this);
        }
        // TODO Draw power-ups

        //g.drawString(Double.toString(currentTime), getWidth()/2, 100);
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
public void run() {
    while (true) {
        repaint();
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
