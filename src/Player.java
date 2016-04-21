import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created on 20/04/2016.
 */
public class Player implements KeyListener, MouseMotionListener, MouseListener {
    public int x, y, position, angle, powerUpTimeLeft;
    public int[] xPoints, yPoints;
    public boolean rotatingLeft, rotatingRight, hasPowerUp, mouseDrag=false;
    ImageIcon icon = new ImageIcon("team2-goalie.png");
    Image playerImage = icon.getImage();

    public final int PLAYER_WIDTH = 100, PLAYER_HEIGHT = 100;

    /**
     * Create the Player the player moves - determines where to redraw the shape based on the
     * velocity and the current points.
     */
    public Player(int startingX, int startingY) {
        this.x = startingX;
        this.y = startingY;
        x = 100;
        y = 100;
//        addMouseMotionListener(this);
        // x = ScreenHeight / 2
        // y = ScreenHeight / 2
    }


    /**
     * Checks for collision with the walls - if so, the game ends.
     * Uses point arrays to determine when the player either touches or passes outside the
     * boundary of the frame.
     * @return true if collision is detected - false otherwise.
     */
    public boolean wallCollision() {
        return false;

    }


    /**
     * When a Power-Up is collided with by the Player, this class is triggered, giving the player
     * a power-up for a certain amount of time.
     * @return true if the player has a power-up - false otherwise.
     */
    private boolean hasPowerUp() {
        return hasPowerUp;
    }


    public void draw(Graphics g) {

    }


    public void move(int frameWidth, int frameHeight) {

    }


    public Image getImage() {
        return playerImage;
    }

    // KEY LISTENER METHODS BEGIN HERE
    /**
     * When a key is pressed (A/D or the left or right arrow), begins decreasing or increasing the
     * rotation variable for the block.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }


    /**
     * When the key is released, sets the rotational boolean to false and stops rotation - the
     * current angle is kept.
     */
    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }


    /**
     * Used for entering name in leaderboard.
     */
    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }


    /**
     * Handles motion of the Player.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Dragging block motion
//        while(mouseDrag) {
            Point mouseLocation = e.getLocationOnScreen();
        int dx = (int) mouseLocation.getX() - x;
        int dy = (int) mouseLocation.getY() - y;

        x += dx;
        y += dy;
//        }

    }


    /**
     * Unpauses the game when the user presses and holds the mouse button.
     * Two options (option menu?): pauses when the mouse button is released or
     * continues and doesn't pause when the mouse is released.
     */
    @Override
    public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub
        mouseDrag = true;
    }


    /**
     * Game is paused (a la SuperHot)
     */
    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
        mouseDrag = false;
    }



    /**
     * Unused.
     */
    @Override
    public void mouseMoved(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }



    /**
     * Unused.
     */
    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }



    /**
     * Unused.
     */
    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }



    /**
     * Unused.
     */
    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

    }

}
