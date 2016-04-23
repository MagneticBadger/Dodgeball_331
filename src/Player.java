import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created on 20/04/2016.
 */
public class Player {
    public int x, y, position, angle, powerUpTimeLeft;
    public boolean rotatingLeft, rotatingRight, hasPowerUp, mouseDrag=false;
    public int[] xPoints, yPoints;
    Rectangle collisionBox;
    private Image playerImage;


    /**
     * Create the Player the player moves - determines where to redraw the shape based on the
     * velocity and the current points.
     */
    public Player(int startingX, int startingY) {
        this.x = startingX;
        this.y = startingY;

        ImageIcon icon = new ImageIcon("team2-goalie.png");
        playerImage = icon.getImage();
        x = 100;
        y = 100;

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
        Graphics2D g2 = (Graphics2D) g;
        collisionBox = new Rectangle(0, 0, 100, 100);
        g2.draw(collisionBox);

    }


    public void move(int frameWidth, int frameHeight) {

    }


    public Image getImage() {
        return playerImage;
    }


    public class MyMouseAdapter extends MouseAdapter {
        private int mouseX, mouseY;

        @Override
        public void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }


        @Override
        public void mouseDragged(MouseEvent e) {
            int dx = e.getX() - mouseX;
            int dy = e.getY() - mouseY;

            if (collisionBox.getBounds2D().contains(x, y)) {
                collisionBox.x += dx;
                collisionBox.y += dy;
                x += dy;
                y += dy;
            }
            x += dx;
            y += dy;
        }


        @Override
        public void mouseReleased(MouseEvent e) {

        }


        public void locationUpdate(MouseEvent e) {

        }

    }

//    // KEY LISTENER METHODS BEGIN HERE
//    /**
//     * When a key is pressed (A/D or the left or right arrow), begins decreasing or increasing the
//     * rotation variable for the block.
//     */
//    @Override
//    public void keyPressed(KeyEvent e) {
//        // TODO Auto-generated method stub
//
//    }
//
//
//    /**
//     * When the key is released, sets the rotational boolean to false and stops rotation - the
//     * current angle is kept.
//     */
//    @Override
//    public void keyReleased(KeyEvent arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//
//    /**
//     * Used for entering name in leaderboard.
//     */
//    @Override
//    public void keyTyped(KeyEvent arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//
//    /**
//     * Handles motion of the Player.
//     */
//    @Override
//    public void mouseDragged(MouseEvent e) {
//        // TODO Dragging block motion
//        while(mouseDrag) {
//            Point mouseLocation = e.getLocationOnScreen();
//            x = (int) mouseLocation.getX();
//            y = (int) mouseLocation.getY();
//        }
//
//    }
//
//
//    /**
//     * Unpauses the game when the user presses and holds the mouse button.
//     * Two options (option menu?): pauses when the mouse button is released or
//     * continues and doesn't pause when the mouse is released.
//     */
//    @Override
//    public void mousePressed(MouseEvent arg0) {
//        // TODO Auto-generated method stub
//        mouseDrag = true;
//    }
//
//
//    /**
//     * Game is paused (a la SuperHot)
//     */
//    @Override
//    public void mouseReleased(MouseEvent arg0) {
//        // TODO Auto-generated method stub
//        mouseDrag = false;
//    }
//
//
//
//    /**
//     * Unused.
//     */
//    @Override
//    public void mouseMoved(MouseEvent arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//
//
//    /**
//     * Unused.
//     */
//    @Override
//    public void mouseClicked(MouseEvent arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//
//
//    /**
//     * Unused.
//     */
//    @Override
//    public void mouseEntered(MouseEvent arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//
//
//    /**
//     * Unused.
//     */
//    @Override
//    public void mouseExited(MouseEvent arg0) {
//        // TODO Auto-generated method stub
//
//    }

}
