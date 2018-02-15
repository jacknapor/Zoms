/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Nov 20, 2015
 * Time: 8:30:37 AM
 *
 * Project: csci205FinalProject
 * Package: Piece
 * File: SpeedPowerUp
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
public class AmmoPowerUp extends PowerUp {
    private Player player;
    private TimerTask task;

    public AmmoPowerUp(Player s) throws IOException {
        super("ammo");
        this.player = s;
        this.img = ImageIO.read(getClass().getResourceAsStream("/rsz_bullets.png"));
    }

    /**
     * Adds the power up of speed to the player for a set amount of time
     *
     * @return this.player
     */
    @Override
    public Player applyPowerUp(Player player) {
        Sound.powerup.play();
        player.AmmoPowerUp = new java.util.Timer();
        int tempammo = player.getAmmo();
        this.task = new TimerTask() {
            public void run() {
                player.setAmmo(tempammo);
                player.hasAmmoPowerup=false;
            }
        };
        player.setAmmo(Integer.MAX_VALUE);
        player.AmmoPowerUp.schedule(task, 5000);
        return this.player;
    }

    @Override
    public String toString() {
        return "U";
    }

}
