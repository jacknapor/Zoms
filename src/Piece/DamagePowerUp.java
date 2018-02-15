/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Nov 20, 2015
 * Time: 8:34:44 AM
 *
 * Project: csci205FinalProject
 * Package: Piece
 * File: DamagePowerUp
 * Description:
 *
 * ****************************************
 */
package Piece;

import Game.Sound;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import javax.imageio.ImageIO;

/**
 *
 * @author jrn011
 */
public class DamagePowerUp extends PowerUp {
    private Player player;
    private TimerTask task;

    public DamagePowerUp() throws IOException {
        super("damage");
        this.img = ImageIO.read(getClass().getResourceAsStream("/damage.png"));
    }

    /**
     * Gives the player a power up for a set amount of time,
     *
     * @return this.Player
     */
    @Override
    public Player applyPowerUp(Player player) {
        Sound.powerup.play();
        player.DamagePowerUp = new java.util.Timer();

        this.task = new TimerTask() {
            public void run() {
                player.damage = player.damage - 100;
                player.hasDamagePowerup=false;
            }
        };
        player.damage = player.damage + 100;
        player.DamagePowerUp.schedule(task, 10000);
        return this.player;

    }

    @Override
    public String toString() {
        return "u";
    }

}
