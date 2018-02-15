/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Nov 13, 2015
 * Time: 8:56:28 AM
 *
 * Project: csci205FinalProject
 * Package: Piece
 * File: Hole
 * Description: Another type of obstacle for the character to avoid, but if a
 * character walks on the hole they fall through and LOSE. Zombies come out of the holes
 *
 * ****************************************
 */
package Piece;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author rth013
 */
public class Hole extends Piece {

    private int numZoms; // number of zombies in hole

    public Hole(int numZoms, int x, int y) throws IOException {
        super("hole", x, y);
        this.numZoms = numZoms;
        this.img = ImageIO.read(getClass().getResourceAsStream("/hole.png"));
    }

    public int getNumZoms() {
        return numZoms;
    }

    /**
     * Lets a zombie out onto the board, called from a class that would release
     * zombies at set intervals
     *
     * @return
     */
    public boolean releaseZombie() {
        if (numZoms > 0) {
            numZoms -= 1;
            return true;
        } else {
            return false;
        }
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "0";
    }
}
