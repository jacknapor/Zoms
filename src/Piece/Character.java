/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Nov 11, 2015
 * Time: 8:21:02 AM
 *
 * Project: csci205FinalProject
 * Package: Piece
 * File: Character
 * Description: Character is the abstract for both Zombie and Player, and helps to show
 * things such as health, movement, damage, speed, and a name.
 *
 * ****************************************
 */
package Piece;

import Game.Board;

/**
 *
 * @author drt008
 */
public abstract class Character extends Piece {

    protected double health;
    protected double damage;
    protected boolean liveliness;

    public Character(String name, int x, int y,
                     double health,
                     double damage) {
        super(name, x, y);
        this.liveliness = true;
        this.health = health;
        this.damage = damage;
    }

    /**
     * The abstract class for all characters, and how they take damage
     *
     * @param damageTaken
     */
    public abstract void takeDamage(int damageTaken);

    /**
     * all characters will need to be able to take a step, changes their
     * location
     *
     * @param dir
     */
    public abstract void takeStep(int dir);

    /**
     * Checks what the character will do at each step
     *
     * @param board
     * @param dir
     * @return whether the character will go there
     */
    public abstract boolean isValidStep(Board board, int dir);
}
