import javax.swing.*;
import java.awt.*;

/**
 * Created by suggs on 20/04/2016.
 */
public class Ball {
    public int x, y;
    private double xVel, yVel;
    int radius;
    boolean active;
    private Image ballImage;

    /**
     * Instantiates an opponent block.
     */
    public Ball(int x, int y, int radius, double xVel, double yVel) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.xVel = xVel;
        this.yVel = yVel;
        active = false;
        ImageIcon icon = new ImageIcon("dodgeball-sprite.png");
        ballImage = icon.getImage();

    }



    /**
     * Given the screen width and height, determines how and where to move the block next.
     * @param width of the screen
     * @param height of the screen
     */
    public void moveBall(int width, int height) {
        x += xVel;
        y += yVel;
    }


    /**
     * Determines if the block has collided with the player - if so, the game ends.
     * @param player passes the PlayerBlock class representing the current player.
     */
    public void ballCollision(Player player) {

    }

    /**
     * Draws the opponent block to the screen.
     * @param g2
     */
    public void draw(Graphics2D g2) {

    }
    public Image getImage() {
        return ballImage;
    }
}
