package Piece;

/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Nov 11, 2015
 * Time: 8:14:08 AM
 *
 * Project: csci205FinalProject
 * Package: Piece
 * File: Player
 * Description: Player is a child of Character, and represents the user's
 * character on the board, while playing the game
 *
 * ****************************************
 */
/**
 *
 * @author drt008
 */
import Game.Board;
import Game.Sound;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Player extends Character {

    public static double initialHealth = 100, initialDamage = 34, initialArmor = 0;
    public static int initialAmmo = 100, initialX = 13, initialY = 10;
    private Board board;
    public int points, gunLevel;
    private int killCount, ammo, armor;
    protected java.util.Timer AmmoPowerUp, DamagePowerUp;
    protected int horzOrientation;
    protected int vertOrientation;
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;
    private BufferedImage helmet;
    private BufferedImage hunter;
    private boolean isHunterIMG = true;
    public boolean diedbyhole=false;
    public boolean hasAmmoPowerup=false;
    public boolean hasDamagePowerup=false;

    public Player(String name, int x, int y) throws IOException {
        super(name, initialX, initialY, initialHealth,
              initialDamage);
        this.killCount = 0;
        this.ammo = initialAmmo;
        this.points = 0;
        this.gunLevel = 0;
        this.armor = 0;
        this.hunter = ImageIO.read(getClass().getResourceAsStream("/rsz_1hunter.png"));
        this.helmet = ImageIO.read(getClass().getResourceAsStream("/rsz_2helmet.png"));
        this.img = this.hunter;
        this.horzOrientation = RIGHT;
        this.vertOrientation = DOWN;
        
    }

    public Player(String name, Board board) {
        super(name, initialX, initialY, initialHealth,
              initialDamage);
        this.board = board;
        this.killCount = 0;
        this.ammo = initialAmmo;
        this.points = 0;
        this.gunLevel = 0;
        this.armor = 0;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param damageTaken, adjusts the health of the player based on damage done
     */
    @Override
    public void takeDamage(int damageTaken) {
        Sound.hurt.play();
        BufferedImage dmg=null;
        BufferedImage dmgh=null;
        try {
            dmg = ImageIO.read(getClass().getResourceAsStream("/damaged.png"));
            dmgh = ImageIO.read(getClass().getResourceAsStream("/damagedhelmet.png"));
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (this.armor >= damageTaken) {
            if(getOrientation()==LEFT){
                this.img=dmg;
                flipImageHorz();
                this.isHunterIMG=true;
            } else if(getOrientation()==RIGHT){
                this.img=dmg;
                this.isHunterIMG=true;
            }else if(getOrientation()==UP){
                this.img=dmgh;
                flipImageVert();
            }else if(getOrientation()==DOWN){
                this.img=dmgh;
                
            }
            this.armor -= damageTaken;
            
        } else if (damageTaken > this.armor) {
            if(getOrientation()==LEFT){
                this.img=dmg;
                flipImageHorz();
                this.isHunterIMG=true;
            }else if(getOrientation()==RIGHT){
                this.img=dmg;
                this.isHunterIMG=true;
            }else if(getOrientation()==UP){
                this.img=dmgh;
                flipImageVert();
            }else if(getOrientation()==DOWN){
                this.img=dmgh;
                
            }
            int newDamage = damageTaken - this.armor;
            this.armor = 0;
            this.health -= newDamage;
            
        }
        if(this.health<=25){
            
        }
        if (this.health <= 0) {
            this.liveliness = false;
            
            //Shows Player is now dead
        }

    }

    public void win() {
        JOptionPane.showMessageDialog(null,
                                      ("You win, you killed: " + this.killCount + " zombies!"),
                                      "CONGRADULATIONS YOU WON",
                                      JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * updates the ammo after the gun is fired
     */
    public void updateAmmo() {
        if (this.ammo > 0 & !this.hasAmmoPowerup) {
            this.ammo -= 1;
        }
    }

    /**
     * Updates the gun's level after an upgrade is purchased in the shop
     */
    public void upgradeGun() {
        gunLevel += 1;
        this.damage = damage + 33;
    }

    /**
     *
     * @return's whether or not the player is alive
     */
    public boolean isAlive() {
        return liveliness;
    }

    /**
     *
     * @return the kill count for the player
     */
    public int getKillCount() {
        return killCount;
    }

    /**
     *
     * @return orientation for the player
     */
    public int getOrientation() {
        if (this.isHunterIMG) {
            return horzOrientation;
        } else {
            return vertOrientation;
        }

    }

    /**
     *
     * @return the total player's points
     */
    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "P";
    }

    /**
     * Updates every time that the Player takes a step, and accounts for
     * direction
     *
     * @param dir
     */
    @Override
    public void takeStep(int dir) {
        switch (dir) {
            case 0:
                y -= 1;
                if (!this.isHunterIMG) {
                    this.img = this.hunter;
                    this.horzOrientation = RIGHT;
                    this.isHunterIMG = true;
                }
                if (this.horzOrientation != LEFT) {
                    this.flipImageHorz();
                    this.horzOrientation = LEFT;
                }
                break;
            case 1:
                x += 1;
                if (this.isHunterIMG) {
                    this.img = this.helmet;
                    this.vertOrientation = DOWN;
                    this.isHunterIMG = false;
                }
                if (this.vertOrientation != DOWN) {
                    this.flipImageVert();
                    this.vertOrientation = DOWN;
                }
                break;
            case 2:
                y += 1;
                if (!this.isHunterIMG) {
                    this.img = this.hunter;
                    this.horzOrientation = RIGHT;
                    this.isHunterIMG = true;
                }
                if (this.horzOrientation != RIGHT) {
                    this.flipImageHorz();
                    this.horzOrientation = RIGHT;
                }
                break;
            case 3:
                x -= 1;
                if (this.isHunterIMG) {
                    this.img = this.helmet;
                    this.vertOrientation = DOWN;
                    this.isHunterIMG = false;
                }
                if (this.vertOrientation != UP) {
                    this.flipImageVert();
                    this.vertOrientation = UP;
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
        if (dir == 0) {
            tempy -= 1;
        } else if (dir == 1) {
            tempx += 1;
        } else if (dir == 2) {
            tempy += 1;
        } else if (dir == 3) {
            tempx -= 1;
        } else {
            System.out.println("ERROR");
        }
        if (board.getCells()[tempx][tempy] instanceof Hole) {
            //Kills Player for stepping in the hole
            this.diedbyhole=true;
            this.takeDamage(200);
            return true;
        } else if (board.getCells()[tempx][tempy] instanceof AmmoPowerUp) {
            AmmoPowerUp s = (AmmoPowerUp) board.getCells()[tempx][tempy];
            s.applyPowerUp(this);
            this.hasAmmoPowerup=true;
            
            return true;
        } else if (board.getCells()[tempx][tempy] instanceof DamagePowerUp) {
            DamagePowerUp s = (DamagePowerUp) board.getCells()[tempx][tempy];
            s.applyPowerUp(this);
            this.hasDamagePowerup=true;
           

            return true;
        } else if (board.getCells()[tempx][tempy] != null) {
            return false;
        } else {

            return true;
        }
    }

    /**
     * To be called whenever a player kills a zombie, updates all of the
     * important information that needs updating after the player kills a zombie
     *
     * @param zombieValue
     */
    public double getHealth() {
        return this.health;
    }

    public double getDamage() {
        return this.damage;
    }

    public int getAmmo() {
        return this.ammo;
    }

    public double getArmor() {
        return this.armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getGunLevel() {
        return gunLevel;
    }

    /**
     * accounts for all the player should do when it kills a zombie. Updates its
     * points and the kill count
     */
    public void kill() {
        this.killCount += 1;
        this.points += 5;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void incrementPoints(int inc) {
        this.points += inc;
    }

    public void setGunLevel(int gunLevel) {
        this.gunLevel = gunLevel;
    }

    public boolean getLiveliness() {
        return this.liveliness;
    }
    
    public void setLiveliness(boolean status){
        this.liveliness= status;
    }

    public void setKillCount(int count) {
        this.killCount = count;
    }

    /**
     * adds number of points to this.points
     *
     * @param points
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * Flips the image of the player, horizontally, so that it is a more
     * realistic movement
     *
     * <a href="http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/Flipanimage.htm">
     * http://www.java2s.com/Tutorial/Java/0261__2D-Graphics/Flipanimage.htm</a>
     *
     */
    private void flipImageHorz() {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-this.img.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx,
                                                     AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        this.img = op.filter(this.img, null);

    }

    /**
     * flips the image of the player, vertically, so that it is a more realistic
     * movement
     */
    private void flipImageVert() {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, -1);
        tx.translate(-this.img.getWidth(null), -this.img.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx,
                                                     AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        this.img = op.filter(this.img, null);
    }
}
