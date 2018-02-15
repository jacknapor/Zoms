/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Nov 16, 2015
 * Time: 8:06:41 AM
 *
 * Project: csci205FinalProject
 * Package: Piece
 * File: fastWeakZomb
 * Description: A child of the zombie class, they have all of the same functionality
 * except for a change in their attributes. This one is faster and weaker.
 *
 * ****************************************
 */
package Piece;

import java.io.IOException;

public class fastWeakZomb extends Zombie {

    protected int boardNum;

    /**
     * constructor for the Fast and Weak Zombie, it is faster and weaker than
     * its normal zombie parent
     *
     * @param name
     * @param x
     * @param y
     */
    public fastWeakZomb(String name, int x, int y) throws IOException {
        super(name, x, y);
        this.damage = initialDamage * .6;
        this.health = initialHealth * .6;
    }

}
