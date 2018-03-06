/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Dec 2, 2015
 * Time: 6:29:29 PM
 *
 * Project: csci205FinalProject
 * Package: Game
 * File: DrawBullet
 * Description:
 *
 * ****************************************
 */
package Game;

import Piece.Bullet;
import Piece.Wall;
import Piece.Zombie;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 *
 * @author jrn011
 */
public class DrawBullet extends JComponent implements KeyListener {
    private Board board;
    private BufferedImage bullet;
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;
    private int orientation;
    private int boxSize = 20;
    private LinkedList<Bullet> bullets;
    private Timer timer;
    private boolean active=false;

    public DrawBullet(Board board) throws IOException {
        this.board = board;
        super.setPreferredSize(new Dimension(500, 500));
        super.setBounds(0, 0, 500, 500);
        super.setOpaque(false);
        this.bullet = ImageIO.read(getClass().getResourceAsStream("/testbullet.png"));
        this.bullets = new LinkedList<Bullet>();
        ActionListener moveListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.getPlayer().isAlive() && !bullets.isEmpty()) {
                    repaint();
                }
            }
        };
        timer = new Timer(25, moveListener);
        timer.start();
    }

    /**
     * Detects when the bullet makes contact with any of the objects that it may
     * encounter, zombies and walls. If it hits a zombie it deals damage, and if
     * it hits a wall, it stops.
     *
     * @param bullet
     * @return
     */
    public boolean collision(Bullet bullet) {
        int boardx = (bullet.getX()) / 20;
        int boardy = (bullet.getY()) / 20;
        System.out.println(bullet.getX());
        
            if (board.getCells()[boardy][boardx] instanceof Wall) {
                return true;
            }
            if (board.getCells()[boardy][boardx] instanceof Zombie) {
                Zombie zom = (Zombie) board.getCells()[boardy][boardx];
                int d = (int) board.getPlayer().getDamage();
                zom.takeDamage(d);
                return true;
            }

        
        return false;
    }

    @Override

    /**
     * Paints the bullet onto the board, and moves it along
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Iterator<Bullet> iter = bullets.iterator();
        if (board.getPlayer().isAlive() && !bullets.isEmpty()) {
            while (iter.hasNext()) {
                Bullet b = iter.next();
                b.moveBullet();
                if (!collision(b)) {
                    g.drawImage(b.getImg(), b.getX(), b.getY(), this);
                } else {
                    iter.remove();
                }

            }

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Makes sure that the bullet is shot from the player's position, and
     * orientation when the space bar key is pressed.
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && this.active) {

            if (board.getPlayer().getAmmo() > 0) {
                try {
                    Sound.bullet.play();
                    switch (board.getPlayer().getOrientation()) {
                        case UP:
                            this.bullets.add(new Bullet(
                                    (board.getPlayer().getY()) * this.boxSize,
                                    (board.getPlayer().getX()) * this.boxSize,
                                    board.getPlayer().getOrientation()));
                            break;
                        case DOWN:

                            this.bullets.add(new Bullet(
                                    (board.getPlayer().getY()) * this.boxSize,
                                    (board.getPlayer().getX()) * this.boxSize,
                                    board.getPlayer().getOrientation()));
                            break;
                        case LEFT:
                            this.bullets.add(new Bullet(
                                    (board.getPlayer().getY()) * this.boxSize,
                                    (board.getPlayer().getX()) * this.boxSize,
                                    board.getPlayer().getOrientation()));
                            break;
                        case RIGHT:
                            this.bullets.add(new Bullet(
                                    (board.getPlayer().getY()) * this.boxSize,
                                    (board.getPlayer().getX()) * this.boxSize,
                                    board.getPlayer().getOrientation()));

                            break;
                    }
                    board.getPlayer().updateAmmo();

                } catch (IOException ex) {
                    Logger.getLogger(DrawBullet.class.getName()).log(
                            Level.SEVERE,
                            null, ex);
                }
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ALT) {

        }
    }

    /**
     * key released
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void updateBoard(Board board) {
        this.board = board;
    }
    
    public void setActive(boolean a){
        this.active=a;
    }

}
