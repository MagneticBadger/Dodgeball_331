import javax.swing.*;
import java.awt.*;

/**
 * Created by suggs on 20/04/2016.
 */
public class Ball {
    public int x, y;
    public double bouncingTime;
    private double xVel, yVel;
    int radius;
    boolean active;
    private Image ballImage;
    public Rectangle collisionBox;

    /**
     * Instantiates an opponent block.
     * Written by Todd Shaughnessy
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
     * Written by Todd Shaughnessy
     * @param width of the screen
     * @param height of the screen
     */
    public void move(int width, int height) {

        x += xVel;
        y += yVel;

        if (x < 1 | x > width) {
            xVel = xVel * -1;
        }
        if (y < 1 | y > height) {
            yVel = yVel * -1;
        }


    }


    /**
     * Draws the opponent block to the screen.
     * Written by Todd Shaughnessy
     * @param
     */
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        collisionBox = new Rectangle(x, y, 33, 33);
        g2.draw(collisionBox);

    }
    public Image getImage() {
        return ballImage;
    }
}
