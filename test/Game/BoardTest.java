/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Dec 8, 2015
 * Time: 5:30:37 PM
 *
 * Project: csci205FinalProject
 * Package: Game
 * File: BoardTest
 * Description:
 *
 * ****************************************
 */
package Game;

import Piece.Player;
import java.io.IOException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author rth013
 */
public class BoardTest {

    static final double EPSILON = 1.0E-12;
    private Board board;

    public BoardTest() {
    }

    @Before
    public void setUp() throws IOException {
        board = new Board(1);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of movePlayer method, of class Board.
     */
    @Test
    public void testMovePlayer() {
        System.out.println("movePlayer");
        int currentX = board.getPlayer().getX();
        int currentY = board.getPlayer().getY();
        int direction = 0;
        board.movePlayer(direction);
        int expectedX = board.getPlayer().getX();
        int expectedY = board.getPlayer().getY() + 1;
        assertEquals(currentX, expectedX, EPSILON);
        assertEquals(currentY, expectedY, EPSILON);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of setPlayerLocation method, of class Board.
     */
    @Test
    public void testSetPlayerLocation() throws IOException {
        System.out.println("setPlayerLocation");
        Player tempPlayer = new Player("tempPlayer", 3, 3);
        board.setPlayerLocation(tempPlayer);
        assertEquals(board.getPlayer().getX(), 3, EPSILON);
        assertEquals(board.getPlayer().getY(), 3, EPSILON);
        tempPlayer = new Player("tempPlayer", 8, 8);
        board.setPlayerLocation(tempPlayer);
        assertEquals(board.getPlayer().getX(), 8, EPSILON);
        assertEquals(board.getPlayer().getY(), 8, EPSILON);
    }

    /**
     * Test of moveZombies method, of class Board.
     */
    @Test
    public void testMoveZombies() {
        System.out.println("moveZombies");
        Board instance = null;
        instance.moveZombies();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of areZombies method, of class Board.
     */
    @Test
    public void testAreZombies() {
        System.out.println("areZombies");
        Board instance = null;
        boolean expResult = false;
        boolean result = instance.areZombies();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
