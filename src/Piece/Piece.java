/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Nov 11, 2015
 * Time: 8:47:28 AM
 *
 * Project: csci205FinalProject
 * Package: Piece
 * File: Piece
 * Description: Piece is the highest hierarchle class in the whole Piece package.
 * It helps with location and visibility for everything on the board
 *
 * ****************************************
 */
package Piece;

import java.awt.image.BufferedImage;

/**
 *
 * @author drt008
 */
public abstract class Piece {

    protected String name;
    protected int x;
    protected int y;
    protected BufferedImage img;

    /**
     * The constructor for a wall, child of Piece, w/o a position
     *
     * @param name
     */
    public Piece(String name) {
        this.name = name;
    }

    /**
     * The constructor for a non-wall Piece, w/ a position
     *
     * @param name
     * @param x
     * @param y
     */
    public Piece(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public BufferedImage getImg() {
        return img;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
