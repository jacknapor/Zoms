/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Nov 13, 2015
 * Time: 8:35:07 AM
 *
 * Project: csci205FinalProject
 * Package: Piece
 * File: Wall
 * Description: The obstacles that are put on the board, child of piece.
 * They don't do much, just make sure no other piece can inhabit that space
 *
 * ****************************************
 */
package Piece;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author drt008
 */
public class Wall extends Piece {

    public Wall() throws IOException {
        super("wall");
        this.img = ImageIO.read(getClass().getResourceAsStream("/wall.png"));
    }

    @Override
    public String toString() {
        return "#";
    }
}
