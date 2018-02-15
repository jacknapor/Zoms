/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Dec 2, 2015
 * Time: 7:02:33 PM
 *
 * Project: csci205FinalProject
 * Package: Game
 * File: Bullet
 * Description:
 *
 * ****************************************
 */
package Piece;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author jrn011
 */
public class Bullet extends Piece {
    private int orientation;
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;

    public Bullet(int x, int y, int orientation) throws IOException {
        super("bullet", x, y);
        this.orientation = orientation;
        this.img = ImageIO.read(getClass().getResourceAsStream("/horzBullet.png"));
        if (this.orientation == RIGHT) {
            flipImageHorz();
        }
        if (this.orientation == UP) {
            this.img = ImageIO.read(getClass().getResourceAsStream("/vertbullet1.png"));
        }
        if (this.orientation == DOWN) {
            this.img = ImageIO.read(getClass().getResourceAsStream("/vertbullet1.png"));
            flipImageVert();
        }

    }

    /**
     * updates the bullet's position on the board based on its orientation,
     * moves faster than the characters
     */
    public void moveBullet() {

        switch (orientation) {
            case UP:
                this.y -= 5;
                break;
            case DOWN:
                this.y += 5;
                break;
            case LEFT:
                this.x -= 5;
                break;
            case RIGHT:
                this.x += 5;
                break;
        }

    }

    /**
     * Makes the picture match the orientation of the bullet on the board,
     * horizontally
     */
    private void flipImageHorz() {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-this.img.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx,
                                                     AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        this.img = op.filter(this.img, null);

    }

    /**
     * Makes the picture match the orientation of the bullet on the board,
     * vertically
     */
    private void flipImageVert() {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, -1);
        tx.translate(-this.img.getWidth(null), -this.img.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx,
                                                     AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        this.img = op.filter(this.img, null);
    }

}
