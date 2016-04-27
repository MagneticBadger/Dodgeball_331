import javax.swing.*;
import java.awt.*;

/**
 * Created on 20/04/2016.
 * Written by Michael Suggs
 */
public class Player {
    public int x, y, position, angle, powerUpTimeLeft;
    public boolean rotatingLeft, rotatingRight, hasPowerUp, mouseDrag = false;
    public int[] xPoints, yPoints;
    Rectangle collisionBox;
    //    MyMouseAdapter mouse = new MyMouseAdapter();
    public Image playerImage;
    public String mode;
    public int lives;
    public double hulkStart;

    ;
    /**
     * Create the Player the player moves - determines where to redraw the shape based on the
     * velocity and the current points.
     * Written by Michael Suggs
     */
    public Player(int startingX, int startingY) {
        this.x = startingX;
        this.y = startingY;

        ImageIcon icon = new ImageIcon("team2-goalie.png");
        playerImage = icon.getImage();
        mode = "HOCKEY";
        lives = 1;
//        x = 100;
//        y = 100;

        // x = ScreenHeight / 2
        // y = ScreenHeight / 2
    }


    /**
     * When a Power-Up is collided with by the Player, this class is triggered, giving the player
     * a power-up for a certain amount of time.
     *
     * @return true if the player has a power-up - false otherwise.
     */
    public void hulkMode() {
        mode = "HULK";
    }


    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        collisionBox = new Rectangle(x + 33, y + 33, 33, 33);
        g2.draw(collisionBox);

    }


    public void move(int frameWidth, int frameHeight) {

    }


    public Image getImage() {
        return playerImage;
    }
}