/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Nov 24, 2015
 * Time: 12:34:12 PM
 *
 * Project: csci205FinalProject
 * Package: Game
 * File: DrawZombies
 * Description:
 *
 * ****************************************
 */
package Game;

import Piece.Piece;
import Piece.Zombie;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 *
 * @author RGreene
 */
public class DrawZombies extends JComponent {

    private Board board;
    private int boxSize = 20;
    private BufferedImage zom;
    private Timer mover;
    private Timer adder;
    private ActionListener moveListener;
    private ActionListener addListener;
    private static final int initialMove = 750;
    private static final int initialAdd = 15000;

    public DrawZombies(Board board) throws IOException {
        this.board = board;
        super.setPreferredSize(new Dimension(500, 500));
        super.setBounds(0, 0, 500, 500);
        super.setOpaque(false);
        createListeners();

        this.mover = new Timer(initialMove, moveListener);
        this.adder = new Timer(initialAdd, addListener);
        this.mover.start();
        this.adder.start();
    }

    private void createListeners() {
        moveListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.getPlayer().isAlive()) {
                    board.moveZombies();
                    repaint();
                }
            }
        };
        addListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (board.getPlayer().isAlive()) {
                    try {
                        board.addMoreZombies();
                    } catch (IOException ex) {
                        Logger.getLogger(DrawZombies.class.getName()).log(
                                Level.SEVERE,
                                null, ex);
                    }
                    repaint();
                }
            }
        };
    }

    /**
     * Paints the components with Graphics g, and draws the zombies.
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawZombies(g);
    }

    /**
     * Adds the zombies to the board in the correct space, and uses their sprite
     * Ensures that the zombie comes out of a hole
     *
     */
    public void drawZombies(Graphics g) {
        Piece array[][] = board.getCells();
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[x].length; y++) {
                if (array[y][x] instanceof Zombie) { // need to add 4 image not square
                    g.drawImage(array[y][x].getImg(), boxSize * x + 4,
                                boxSize * y,
                                null);

                }
            }
        }
    }

    /**
     * Stops the zombie movement
     */
    public void stopZombies() {
        this.mover.stop();
        this.adder.stop();
    }

    public void updateBoard(Board board) {
        this.board = board;
        this.mover.setDelay(initialMove - (22 * this.board.getRoundNum()));
        this.adder.setDelay(initialAdd - (100 * this.board.getRoundNum()));
        this.mover.start();
        this.adder.start();
    }

}
