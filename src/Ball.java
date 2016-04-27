import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by suggs on 20/04/2016.
 */
public class Ball {
    public int x, y;
    public double bouncingTime;
    private double xVel, yVel;
    int radius;
    boolean active, beenTurned;
    public Image ballImage;
    public Rectangle collisionBox;
    AudioClip bounce;

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
        beenTurned = false;
        bounce = getAudioClip("BOUNCE.WAV");

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
            bounce.play();
        }
        if (y < 1 | y > height) {
            yVel = yVel * -1;
            bounce.play();
        }


    }


    public void removeRectangle(Graphics g) {
        g.clearRect(collisionBox.x, collisionBox.y, collisionBox.width, collisionBox.height);
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
