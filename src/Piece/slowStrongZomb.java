/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Nov 16, 2015
 * Time: 8:06:08 AM
 *
 * Project: csci205FinalProject
 * Package: Piece
 * File: slowStrongZomb
 * Description: A child of the zombie class, they have all of the same functionality
 * except for a change in their attributes. This one is slower and stronger.
 *
 * ****************************************
 */
package Piece;

import java.io.IOException;

public class slowStrongZomb extends Zombie {

    protected int boardNum;

    /**
     * constructor for the slowStrongZombie, it is slower and stronger than its
     * normal zombie parent
     *
     * @param name
     * @param x
     * @param y
     */
    public slowStrongZomb(String name, int x, int y) throws IOException {
        super(name, x, y);
        this.damage = initialDamage * 1.5;
        this.health = initialHealth * 1.5;
    }

}
