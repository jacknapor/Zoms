/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Nov 11, 2015
 * Time: 8:20:47 AM
 *
 * Project: csci205FinalProject
 * Package: Piece
 * File: Zombie
 * Description: Zombie is the parent class for all of the enemies that the player
 * will encounter while playing the game.
 *
 * ****************************************
 */
package Piece;

import Game.Board;
import Game.Sound;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author drt008
 */
public class Zombie extends Character {
    public static double initialHealth = 100, initialDamage = 20, initialStrength = 15;
    public static int pointValue = 15;
    protected Board board;
    protected Player target;
    protected int xTarget, yTarget;
    protected int Orientation;
    protected static final int LEFT = 0;
    protected static final int RIGHT = 1;
    protected static final int[] moves = {0, 1, 2, 3};

    public Zombie(String name, int x, int y) throws IOException {
        super(name, x, y, initialHealth, initialDamage);
        this.img = ImageIO.read(getClass().getResourceAsStream("/rsz_zombie_new.png"));
        this.Orientation = RIGHT;
    }

    public Zombie(String name, int x, int y, int boardNum) {
        super(name, x, y, initialHealth, initialDamage);
        this.target = board.getPlayer();
        this.xTarget = this.target.getX();
        this.yTarget = this.target.getY();
    }

    /**
     * The class that adjusts the health of the zombie based on damage done by
     * player
     *
     * @param damageTaken
     */
    @Override
    public void takeDamage(int damageTaken) {
        if (this.liveliness!=false ){
        this.health -= damageTaken;
        if (this.health <= 0) {
            //Shows Zombie is now dead
            Sound.kill.play();
            liveliness = false;
        }}
    }

    /**
     *
     * @return whether or not the zombie is alive
     */
    public boolean isAlive() {
        return liveliness;
    }

    @Override
    public String toString() {
        return "Z";
    }

    /**
     *
     * @param dir, corrects the position for the zombie, after it takes a step
     */
    @Override
    public void takeStep(int dir) {

        switch (dir) {
            case 0://UP
                y -= 1;
                break;
            case 1://Right
                x += 1;
                if (this.Orientation == LEFT) {
                    this.flipImage();
                    this.Orientation = RIGHT;
                }
                break;
            case 2://Down
                y += 1;
                break;
            case 3://Left
                x -= 1;
                if (this.Orientation == RIGHT) {
                    this.flipImage();
                    this.Orientation = LEFT;
                }
                break;
            default:
                System.out.println("ERROR");
                break;
        }
    }

    /**
     * Checks whether or not the move is valid, before the move is made
     *
     * @param dir
     * @return whether or not you can move
     */
    @Override
    public boolean isValidStep(Board board, int dir) {
        int tempx = x;
        int tempy = y;
        switch (dir) {
            case 0:
                tempy -= 1;
                break;
            case 1:
                tempx += 1;
                break;
            case 2:
                tempy += 1;
                break;
            case 3:
                tempx -= 1;
                break;
            default:
                System.out.println("ERROR");
                break;
        }
        if (board.getCells()[tempy][tempx] != null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Actually takes the step on the game board. Shows when the Zombie will
     * actually move, with respect to the timer in milliseconds
     *
     * @param board
     */
    @SuppressWarnings("empty-statement")
    public void takeValidStep(Board board) {
        Random r = new Random();
        int randStep = 0;
        do {
            randStep = r.nextInt(4);
        } while (!isValidStep(board, randStep));
        takeStep(randStep);

    }

    /**
     * If zombie is adjacent to player, zombie is close enough to attack
     *
     * @return true if zombie is close enough to hurt the player
     */
    public boolean isNextToPlayer(Board board) {
        if (Math.abs(board.getPlayer().getX() - x) == 1) {
            return true;
        } else if (Math.abs(board.getPlayer().getY() - y) == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * The distance formula, used to get distance from player
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public double distance(int x1, int y1, int x2, int y2) {
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        return distance;
    }

    /**
     * moves the zombie close to player code seems redundant but the purpose is
     * for randomness in zombie movements
     *
     * @param board
     */
    public void trackTarget(Board board) {
        List<Integer> validMoves = new ArrayList();
        double currentDist = this.distance(x, y,
                                           board.getPlayer().getY(),
                                           board.getPlayer().getX());
        if (currentDist < 14 && currentDist > 1) { //zombie is within 14 units of player, and not ontop of player
            for (int i : moves) {
                if (this.isValidStep(board, i)) {
                    validMoves.add(i);
                }
            }
            if (validMoves.size() == 1) {
                takeStep((int) validMoves.get(0));
            } else {
                double minDistance = 100.0;
                int correctMove = -1;
                for (int move : validMoves) {
                    int tempx = x;
                    int tempy = y;
                    switch (move) {
                        case 0:
                            tempy -= 1;
                            break;
                        case 1:
                            tempx += 1;
                            break;
                        case 2:
                            tempy += 1;
                            break;
                        case 3:
                            tempx -= 1;
                            break;
                    }
                    double dist = this.distance(tempx, tempy,
                                                board.getPlayer().getY(),
                                                board.getPlayer().getX());
                    if (minDistance > dist) {
                        minDistance = dist;
                        correctMove = move;
                    }
                }
                takeStep(correctMove);
            }
        } else if (currentDist <= 1) {
            board.getPlayer().takeDamage(25);
        } else {
            this.takeValidStep(board);
        }

    }

    /**
     * flips the image of the zombie so that you can see which direction it has
     * most recently moved in
     */
    //http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/Flipanimage.htm
    private void flipImage() {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-this.img.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx,
                                                     AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        this.img = op.filter(this.img, null);

    }
}
