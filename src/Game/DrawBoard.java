/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Nov 16, 2015
 * Time: 2:32:54 PM
 *
 * Project: csci205FinalProject
 * Package: Game
 * File: DrawBoard
 * Description: Adds all of the sprites and pictures to correspond to the array
 * of the current board
 *
 * ****************************************
 */
package Game;

import Piece.Piece;
import Piece.Zombie;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JComponent;

/**
 *
 * @author rtg008
 */
public class DrawBoard extends JComponent implements KeyListener {

    //Timer t = new Timer(WIDTH, listener);
    private Board board;
    private int boxSize = 20;
    private boolean active = false;

    /**
     * constructor for the DrawBoard class
     *
     * @param board
     * @throws IOException
     */
    public DrawBoard(Board board) throws IOException {
        this.board = board;
        super.setPreferredSize(new Dimension(500, 500));
        super.setBounds(0, 0, 500, 500);
        super.setOpaque(false);
        
        
    }

    /**
     * Paints in the components on the board for the view
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //drawGrid(g);
        drawSprites(g);
    }

    /**
     * Draws the grid on the view for the board
     *
     * @param g
     */
    private void drawGrid(Graphics g) {
        int boxSize = 20;
        g.setColor(Color.BLACK);

        for (int i = 0; i < 25; i++) {
            g.drawLine(i * boxSize, 0, i * boxSize, this.getHeight());

        }
        for (int i = 0; i < 25; i++) {
            g.drawLine(0, i * boxSize, this.getWidth(), i * boxSize);
        }
    }

    /**
     *
     * @param g, creates the graphical representation of the board on the view
     */
    private void drawSprites(Graphics g) {
        Piece array[][] = board.getCells();
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[x].length; y++) {
                if (array[y][x] != null && !(array[y][x] instanceof Zombie)) {
                    g.drawImage(array[y][x].getImg(), this.boxSize * x,
                                this.boxSize * y,
                                this);
                }
            }
        }
    }

    /**
     * Getter for the size of the box
     *
     * @return boxSize
     */
    public int getBoxSize() {
        return boxSize;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * No events occur if the key is typed
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * The key pressed event for all of the arrow keys, to move the player
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (active && board.getPlayer().isAlive()) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_UP:
                    if (board.getPlayer().isValidStep(board, 3)) {
             
                        board.movePlayer(3);
                    }
                    repaint();
                    break;
                case KeyEvent.VK_DOWN:
                    if (board.getPlayer().isValidStep(board, 1)) {
                        board.movePlayer(1);
                    }
                    repaint();
                    break;
                case KeyEvent.VK_LEFT:
                    if (board.getPlayer().isValidStep(board, 0)) {
                        board.movePlayer(0);
                    }
                    repaint();
                    break;
                case KeyEvent.VK_RIGHT:
                    if (board.getPlayer().isValidStep(board, 2)) {
                        board.movePlayer(2);
                    }

                    repaint();
                    break;
            }
        }
    }

    /**
     * No events occur as the key is released
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e
    ) {

    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void updateBoard(Board board) {
        this.board = board;
    }

}
