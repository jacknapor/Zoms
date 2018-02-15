/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Nov 20, 2015
 * Time: 8:28:54 AM
 *
 * Project: csci205FinalProject
 * Package: Piece
 * File: PowerUp
 * Description:
 *
 * ****************************************
 */
package Piece;

/**
 *
 * @author jrn011
 */
public abstract class PowerUp extends Piece {

    public PowerUp(String name) {
        super(name);
    }

    /**
     * Applys the powerup to the player
     *
     * @return the player that is being powered up
     */
    public abstract Player applyPowerUp(Player player);
}
