/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Nov 11, 2015
 * Time: 8:26:48 AM
 *
 * Project: csci205FinalProject
 * Package: Game
 * File: Board
 * Description: the game's board is represented in this class, showing a 2D array
 * in which all of the pieces are located
 *
 * ****************************************
 */
package Game;

import Piece.AmmoPowerUp;
import Piece.DamagePowerUp;
import Piece.Hole;
import Piece.Piece;
import Piece.Player;
import Piece.Wall;
import Piece.Zombie;
import java.awt.Point;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Class Board's main purpose is to store the 25x25 array of pieces which make
 * up the game board. The different boards are selected with a number boardNum.
 * Board also stores an instance of the player in order to be placed on the
 * board. The round number will determine the number of zombies in the round,
 * and since not all boards have the same number of holes, the number of zombies
 * per hole on any given map may change.
 *
 * @author rth013
 */
public class Board {

    private Piece[][] cells;
    private Piece[][] orgBoard;
    private final int boardNum;
    private int roundNum;
    private int numZombies;
    private int numHoles;
    private Player newPlayer;
    private Point orgPlayerLoc;
    private LinkedList<Zombie> zomList;
    private LinkedList<Hole> holeList;

    /**
     * The Constructor for board w/ 1 parameter
     *
     * @param boardNum
     */
    public Board(int boardNum) throws IOException {
        this.boardNum = boardNum;
        this.cells = buildBoard();
    }

    /**
     * The Constructor for board w/ 2 parameters
     *
     * @param boardNum
     * @param roundNum
     */
    public Board(int boardNum, int roundNum) throws IOException {
        this.boardNum = boardNum;
        this.roundNum = roundNum;
        this.numZombies = this.roundNum * 5;  // the current equation to calculate difficulty increase with number of rounds completed
        this.cells = buildBoard();
        this.orgBoard = cells;
        this.zomList = new LinkedList<Zombie>();
        this.holeList = new LinkedList<Hole>();
    }

    public int getRoundNum() {
        return this.roundNum;
    }
    
    

    /**
     * When the player wins the round, they earn a point bonus, depending on the
     * round
     */
    public void winRound() {
        newPlayer.incrementPoints(this.roundNum * 10);
        
    }

    /**
     * Builds the board corresponding to the board number given. Different
     * boards may have different numbers of holes, so numZomsPerHole differs
     * with each board. The two ints in each Hole() parameter is the x and y
     * position of the hole, respectively.
     *
     * @return board is a 2d array of Pieces
     */
    private Piece[][] buildBoard() throws IOException {
        Piece[][] board = new Piece[0][0];
        int numZomsPerHole = 0;
        if (this.boardNum == 1) {
            int playerX = 10, playerY = 13;
            newPlayer = new Player("player1", playerX, playerY);
            orgPlayerLoc = new Point(playerX, playerY);
            numHoles = 5;
            numZombies = numHoles * this.roundNum;
            numZomsPerHole = this.numZombies / numHoles;
            board = new Piece[][]{{new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall()},
                                  {new Wall(), new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Wall()},
                                  {new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, new Wall()},
                                  {new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, new Wall()},
                                  {new Wall(), new Wall(), null, null, new Wall(), new Wall(), new Hole(
                                   numZomsPerHole, 5, 12), null, new Wall(), null, null, null, null, null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, new Wall()},
                                  {new Wall(), null, null, null, null, null, new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), null, new Wall()},
                                  {new Wall(), null, null, null, null, null, null, null, null, new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, null, new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, null, null, null, null, null, null, null, new Wall(), new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, null, null, new Wall()},
                                  {new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Hole(
                                   numZomsPerHole, 5, 23), null, null, null, null, null, new Wall()},
                                  {new Wall(), null, null, null, new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Wall()},
                                  {new Wall(), null, null, null, new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Wall()},
                                  {new Wall(), null, null, null, new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, null, new Wall(), new Hole(
                                   numZomsPerHole, 6, 4), new Wall(), null, null, null, null, null, null, null, new Wall(), null, null, null, null, null, null, new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, null, null, null, new Wall(), new Wall(), null, null, null, null, new Wall(), new Wall(), null, null, null, null, null, null, null, new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, null, null, null, null, null, new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, null, null, null, null, null, null, null, new Wall(), null, null, null, null, null, null, null, null, null, new Wall(), null, null, null, new Wall()},
                                  {new Wall(), null, null, null, null, null, null, null, null, null, new Wall(), null, null, null, null, null, null, null, null, new Wall(), new Wall(), null, null, null, new Wall()},
                                  {new Wall(), null, null, null, new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, new Wall(), new Wall(), null, null, null, null, new Wall()},
                                  {new Wall(), null, null, null, null, new Wall(), null, null, null, null, null, null, null, null, null, new Wall(), new Wall(), null, new Wall(), null, null, null, null, null, new Wall()},
                                  {new Wall(), new Wall(), null, null, null, null, null, null, null, null, new Wall(), null, null, null, null, null, null, new Wall(), null, null, null, null, null, null, new Wall()},
                                  {new Wall(), new Wall(), null, null, null, null, null, null, null, new Wall(), new Wall(), null, null, null, null, null, null, new Wall(), null, null, null, new Hole(
                                   numZomsPerHole, 18, 8), null, null, new Wall()},
                                  {new Wall(), new Wall(), null, null, null, null, null, null, null, new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, new Wall(), new Wall()},
                                  {new Wall(), new Wall(), new Wall(), null, null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, null, null, new Wall(), new Wall()},
                                  {new Wall(), new Wall(), new Wall(), new Wall(), null, new Hole(
                                   numZomsPerHole, 21, 20), null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, new Wall(), new Wall(), new Wall()},
                                  {new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall()}};

        } else if (this.boardNum == 2) {
            int playerX = 12, playerY = 12;
            newPlayer = new Player("player1", playerX, playerY);
            orgPlayerLoc = new Point(playerX, playerY);
            numHoles = 8;
            numZombies = numHoles * this.roundNum;
            numZomsPerHole = this.numZombies / numHoles;
            board = new Piece[][]{{new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall()},
                                  {new Wall(), new Hole(numZomsPerHole, 1, 1), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Hole(
                                   numZomsPerHole, 23, 1), new Wall()},
                                  {new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Wall()},
                                  {new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, new Wall(), null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, new Wall(), null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, new Wall(), null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, new Wall(), null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, new Wall(), null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, new Wall(), null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, new Wall(), null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Hole(
                                   numZomsPerHole, 11, 11), new Wall(), new Hole(
                                   numZomsPerHole, 13, 11), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Hole(
                                   numZomsPerHole, 11, 13), new Wall(), new Hole(
                                   numZomsPerHole, 13, 13), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, new Wall(), null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, new Wall(), null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, new Wall(), null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, new Wall(), null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, new Wall(), null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, new Wall(), null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, new Wall(), null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, new Wall()},
                                  {new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Wall()},
                                  {new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Wall()},
                                  {new Wall(), new Hole(numZomsPerHole, 1, 23), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Hole(
                                   numZomsPerHole, 23, 23), new Wall()},
                                  {new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall()}};
        } else if (this.boardNum == 3) {
            int playerX = 2, playerY = 12;
            newPlayer = new Player("player1", playerX, playerY);
            orgPlayerLoc = new Point(playerX, playerY);
            numHoles = 4;
            numZombies = numHoles * this.roundNum;
            numZomsPerHole = this.numZombies / numHoles;
            board = new Piece[][]{{new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall()},
                                  {new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall()},
                                  {new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall()},
                                  {new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall()},
                                  {new Wall(), new Wall(), new Wall(), null, null, null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, null, null, null, new Wall(), new Wall(), new Wall()},
                                  {new Wall(), new Wall(), null, null, null, null, null, new Wall(), new Wall(), null, null, null, null, null, null, null, new Wall(), new Wall(), null, null, null, null, null, new Wall(), new Wall()},
                                  {new Wall(), new Wall(), null, null, null, null, new Hole(
                                   numZomsPerHole, 6, 6), null, null, null, null, null, null, null, null, null, null, null, new Hole(
                                   numZomsPerHole, 18, 6), null, null, null, null, new Wall(), new Wall()},
                                  {new Wall(), new Wall(), null, null, null, new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, null, new Wall(), null, null, null, new Wall(), new Wall()},
                                  {new Wall(), null, null, null, null, new Wall(), null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, null, new Wall(), null, null, null, null, new Wall()},
                                  {new Wall(), null, null, null, new Wall(), null, null, null, null, new Wall(), null, null, null, null, null, new Wall(), null, null, null, null, new Wall(), null, null, null, new Wall()},
                                  {new Wall(), null, null, null, null, null, null, null, new Wall(), null, null, null, null, null, null, null, new Wall(), null, null, null, new Wall(), null, null, null, new Wall()},
                                  {new Wall(), null, null, null, null, null, null, null, new Wall(), null, null, null, null, null, null, null, null, null, null, null, new Wall(), null, null, null, new Wall()},
                                  {new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Wall()},
                                  {new Wall(), null, null, null, null, null, null, null, new Wall(), null, null, null, null, null, null, null, null, null, null, null, new Wall(), null, null, null, new Wall()},
                                  {new Wall(), null, null, null, null, null, null, null, new Wall(), null, null, null, null, null, null, null, new Wall(), null, null, null, new Wall(), null, null, null, new Wall()},
                                  {new Wall(), null, null, null, new Wall(), null, null, null, null, new Wall(), null, null, null, null, null, new Wall(), null, null, null, null, new Wall(), null, null, null, new Wall()},
                                  {new Wall(), null, null, null, null, new Wall(), null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, null, new Wall(), null, null, null, null, new Wall()},
                                  {new Wall(), new Wall(), null, null, null, new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, null, new Wall(), null, null, null, new Wall(), new Wall()},
                                  {new Wall(), new Wall(), null, null, null, null, new Hole(
                                   numZomsPerHole, 6, 18), null, null, null, null, null, null, null, null, null, null, null, new Hole(
                                   numZomsPerHole, 18, 18), null, null, null, null, new Wall(), new Wall()},
                                  {new Wall(), new Wall(), null, null, null, null, null, new Wall(), new Wall(), null, null, null, null, null, null, null, new Wall(), new Wall(), null, null, null, null, null, new Wall(), new Wall()},
                                  {new Wall(), new Wall(), new Wall(), null, null, null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, null, null, null, new Wall(), new Wall(), new Wall()},
                                  {new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall()},
                                  {new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall()},
                                  {new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null, null, null, null, null, null, null, null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall()},
                                  {new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall()}};
        } else {
            System.out.println("No such board");
            return new Piece[0][0];
        }
        //put player in board
        board[newPlayer.getX()][newPlayer.getY()] = newPlayer;

        // add powerups to board
        Random ran = new Random();
        int boardSize = 25;

        int puX, puY;
        while (true) {
            puX = ran.nextInt(boardSize);
            puY = ran.nextInt(boardSize);
            if (board[puX][puY] == null) {
                board[puX][puY] = new AmmoPowerUp(newPlayer);
                break;
            }
        }
        while (true) {
            puX = ran.nextInt(boardSize);
            puY = ran.nextInt(boardSize);
            if (board[puX][puY] == null) {
                board[puX][puY] = new DamagePowerUp();
                break;
            }
        }
        return board;
    }

    public Piece[][] getCells() {
        return cells;
    }

    public Player getPlayer() {
        return this.newPlayer;
    }

    /**
     * Moves player on the board in the specified direction. Calls the character
     * class' method to have the player take a step
     *
     * @param direction
     */
    public void movePlayer(int direction) {
        cells[newPlayer.getX()][newPlayer.getY()] = null;
        newPlayer.takeStep(direction);
        Sound.move.play();
        cells[newPlayer.getX()][newPlayer.getY()] = newPlayer;
    }

    public void setPlayerLocation(Player tempPlayer) {
        cells[newPlayer.getX()][newPlayer.getY()] = null;
        int x = tempPlayer.getX();
        int y = tempPlayer.getY();
        this.cells[x][y] = tempPlayer;
        this.newPlayer = tempPlayer; // set new player
    }

    /**
     * Adds zombies onto the board, makes sure that they're coming out of holes,
     * and creates new zombies.
     *
     * @throws java.io.IOException
     */
    public void addZombies() throws IOException {
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                if (cells[y][x] instanceof Hole) {
                    Hole tempHole = (Hole) cells[y][x];

                    //So you don't have to read this if...
                    //It basically checks to make sure that the spot adjacent
                    //To the hole is a null before adding the zombie
                    if (!((cells[tempHole.getX() + 1][tempHole.getY()] != null) && (cells[tempHole.getX()][tempHole.getY() + 1] != null) && (cells[tempHole.getX() - 1][tempHole.getY()] != null) && (cells[tempHole.getX()][tempHole.getY() - 1] != null))) {

                        holeList.add(tempHole);
                        if (tempHole.releaseZombie()) {
                            Zombie tempZom = new Zombie("Zom", x, y);
                            tempZom.takeValidStep(this);
                            cells[tempZom.getY()][tempZom.getX()] = tempZom;
                            zomList.add(tempZom);
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds a new zombie at a specified time interval, out of a hole (child of
     * piece class)
     *
     * @throws IOException
     */
    public void addMoreZombies() throws IOException {
        if (!holeList.isEmpty()) {
            for (Hole h : holeList) {
                if (h.releaseZombie()) {
                    Zombie tempZom = new Zombie("Zom", h.getX(), h.getY());
                    tempZom.takeValidStep(this);
                    cells[tempZom.getY()][tempZom.getX()] = tempZom;
                    zomList.add(tempZom);
                }
            }
        }

    }

    /**
     * Moves zombies, makes sure that the steps they're taking are valid, and
     * changes their positions on the board
     */
    public void moveZombies() {
        Iterator<Zombie> iter = zomList.iterator();
        if (!zomList.isEmpty()) {
            while (iter.hasNext()) {
                Zombie z = iter.next();
                if (z.isAlive()) {
                    cells[z.getY()][z.getX()] = null;
                    z.trackTarget(this);
                    cells[z.getY()][z.getX()] = z;
                } else {
                    cells[z.getY()][z.getX()] = null;
                    iter.remove();
                    newPlayer.kill();
                    this.numZombies -= 1;
                }
            }
        }
    }

    public boolean areZombies() {
        if (this.numZombies != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * A textual representation of the board, in the toString
     *
     * @return
     */
    @Override
    public String toString() {
        String s = "Board " + this.boardNum + ":\n";
        for (int row = 0; row < this.cells.length; row++) {
            for (int col = 0; col < this.cells[row].length; col++) {
                if (this.cells[row][col] == null) {
                    s += "  ";
                } else {
                    s += this.cells[row][col] + " ";
                }
            }
            s += "\n";
        }
        return s;
    }

}
