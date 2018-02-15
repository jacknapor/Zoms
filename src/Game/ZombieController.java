/**
 * * ****************************************
 * CSCI205 - Software Engineering and Design Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman Date: Nov 13,
 * 2015 Time: 7:52:40 AM
 *
 * Project: csci205FinalProject Package: Game File: ZombieController
 * Description: The controller for the whole game, works with the ZombieModel
 * and ZombieView
 *
 * ****************************************
 */
package Game;

import Piece.Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

public class ZombieController implements ActionListener {

    private ZombieView theView;
    private Board board;
    private ShopView theStore;
    private DrawBoard drBoard;
    private DrawZombies drZombies;
    private ShopController shopController;
    private JLayeredPane layeredBoard;
    private Timer checkTimer;
    private TimerTask checker;
    private DrawBullet drBullet;
    private JLabel kcLabel;
    private boolean roundTransition = false;
    private int boardNum;

    /**
     * This is the constructor for the Controller class for the ZombieView
     *
     * @param view
     * @throws IOException
     */
    public ZombieController(ZombieView view) throws IOException {
        this.boardNum = new Random().nextInt(3) + 1;
        this.theView = view;
        this.theView.setFocusable(false);
        this.board = new Board(boardNum, 1);
        this.drBoard = new DrawBoard(board);
        this.drZombies = new DrawZombies(board);
        this.drBullet = new DrawBullet(board);
        this.theStore = new ShopView();
        this.shopController = new ShopController(this.theStore,
                                                 board.getPlayer());
        this.theStore.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                roundTransition = true;
            }
        });
        theView.getBoard().setLocation(350, 20);
        this.changeBTN();

        this.layeredBoard = new JLayeredPane();
        this.layeredBoard.setPreferredSize(new Dimension(900, 650));
        this.layeredBoard.setBounds(0, 0, 900, 650);

        theView.getLblRound().setText("Round " + board.getRoundNum());

        theView.getBoard().addKeyListener(this.drBoard);
        theView.getBoard().addKeyListener(this.drBullet);
        theView.getBoard().setFocusable(true);
        theView.getBtnStart().addActionListener(this);
        theView.getBtnExit().addActionListener(this);
        theView.getVitals().setPreferredSize(new Dimension(80, 80));
        theView.pack();

        Sound.song.loop();
    }

    /**
     * Displays all of the vitals on the JLabel, vitalLabel. Updates frequently
     * to keep the player updated on their standing in the game (health, ammo,
     * ect)
     */
    public void displayVitals() {
        theView.getVitals().setForeground(Color.WHITE);
        String text="";
        if(board.getPlayer().hasAmmoPowerup){
             text = "<html>Kill Count: " + board.getPlayer().getKillCount() + "<br>"
                      + " Ammo Remaining: ∞ " + "<br>" + " Armor: " + board.getPlayer().getArmor() + "<br>"
                      + " Health: " + board.getPlayer().getHealth() + "<br>"
                      + " Points: " + board.getPlayer().getPoints() + "</html>";
        }else{
        text = "<html>Kill Count: " + board.getPlayer().getKillCount() + "<br>"
                      + " Ammo Remaining: " + board.getPlayer().getAmmo() + "<br>" + " Armor: " + board.getPlayer().getArmor() + "<br>"
                      + " Health: " + board.getPlayer().getHealth() + "<br>"
                      + " Points: " + board.getPlayer().getPoints() + "</html>"; }
        if(board.getPlayer().hasDamagePowerup){
            theView.getjLabel1().setVisible(true);
            
        }else {
            theView.getjLabel1().setVisible(false);
        }
        if (board.getPlayer().hasAmmoPowerup){
            theView.getjLabel2().setVisible(true);
            
        }else{
            theView.getjLabel2().setVisible(false);
        }
        theView.getVitals().setText(text);
        theView.getVitals().setFont(new Font("Verdana", Font.PLAIN, 25));
        
    }

    /**
     * Starts the game when the button is pressed
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == theView.getBtnStart()) {
            this.drBoard.setActive(true);
            Sound.song.stop();
            theView.getcontrols().setVisible(false);
               this.drBullet.setActive(true);
            try {
                if (!board.getPlayer().isAlive()) {
                    resetBoard();
                }
                theView.getBoard().add(this.layeredBoard, BorderLayout.CENTER);
                board.addZombies();
                drBoard.setActive(true);
                resume();
            } catch (IOException ex) {
                Logger.getLogger(ZombieController.class.getName()).log(
                        Level.SEVERE,
                        null, ex);
            }
            this.layeredBoard.removeAll();
            this.layeredBoard.add(this.drBoard, JLayeredPane.DEFAULT_LAYER);
            this.layeredBoard.add(this.drZombies, JLayeredPane.PALETTE_LAYER);
            this.layeredBoard.add(this.drBullet, JLayeredPane.MODAL_LAYER);

            theView.getBoard().requestFocus();
            theView.getBtnStart().setEnabled(false);
            theView.getBtnExit().setEnabled(true);

        }

        if (e.getSource() == theView.getBtnExit()) {
            drZombies.stopZombies();
            drBoard.setActive(false);
            JOptionPane.showMessageDialog(null,
                                          "Thank you for playing Zombies!.",
                                          "Goodbye!", JOptionPane.PLAIN_MESSAGE);

            theView.getBtnExit().setEnabled(false);
            theView.getBtnStart().setEnabled(true);
            System.exit(0);
        }
    }

    private void changeBTN() {
        //CHANGE BUTTON LOGO
        theView.getBtnStart().setIcon(new ImageIcon(getClass().getResource("/start.png")));
        theView.getBtnStart().setOpaque(false);
        theView.getBtnStart().setContentAreaFilled(false);
        theView.getBtnStart().setBorderPainted(false);
        theView.getBtnStart().setText("");
        theView.getBtnExit().setIcon(new ImageIcon(getClass().getResource("/exit.png")));
        theView.getBtnExit().setOpaque(false);
        theView.getBtnExit().setContentAreaFilled(false);
        theView.getBtnExit().setBorderPainted(false);
        theView.getBtnExit().setText("");
    }

    private void isPlayerAlive() throws IOException {
        if (!board.getPlayer().isAlive()) {
            pause();
            this.drBoard.repaint();
            theView.getBoard().remove(this.layeredBoard);
            theView.getBtnStart().setEnabled(true);
            theView.getBtnExit().setEnabled(false);
           
         
            if(board.getPlayer().diedbyhole){
                Sound.falling.play();
                JOptionPane.showMessageDialog(null,
                                          ("You fell in a hole and died."),
                                          "You Died",
                                          JOptionPane.INFORMATION_MESSAGE);
            }else{
                 Sound.death.play();
                 
             if(board.getPlayer().getKillCount()==1){
                 JOptionPane.showMessageDialog(null,
                                          ("You were overwhelmed by the zombie horde. \n You managed to hold off " + board.getPlayer().getKillCount() + " zombie."),
                                          "You Died",
                                          JOptionPane.INFORMATION_MESSAGE);
             }else{
            JOptionPane.showMessageDialog(null,
                                          ("You were overwhelmed by the zombie horde. \n You managed to hold off " + board.getPlayer().getKillCount() + " zombies."),
                                          "You Died",
                                          JOptionPane.INFORMATION_MESSAGE);}
            }resetBoard();
        }
    }

    public void winRound() throws IOException, InterruptedException {
        
        if (!board.areZombies()) {
            Sound.battle.stop();
            Sound.shop.clip.loop(Clip.LOOP_CONTINUOUSLY);
            
            this.shopController.updateView(board.getPlayer());
            this.theStore.setVisible(true);
            drBoard.setActive(false);
            drZombies.stopZombies();
            this.drBullet.setActive(false);
        }
        if (roundTransition) {
             Sound.shop.clip.stop();
             Sound.shop.clip.setMicrosecondPosition(0);
             Sound.battle.clip.setMicrosecondPosition(0);
             Sound.battle.clip.loop(Clip.LOOP_CONTINUOUSLY);
              FloatControl gc = (FloatControl) Sound.battle.clip.getControl(
                FloatControl.Type.MASTER_GAIN);
        gc.setValue(-12.0f); 
            this.theStore.setVisible(false);
            int roundNum = board.getRoundNum() + 1;
            theView.getLblRound().setText("Round " + roundNum);
            Player tempPlayer = board.getPlayer();
            board = new Board(boardNum, roundNum);
            board.setPlayerLocation(tempPlayer);
            board.addZombies();
            this.drBoard.updateBoard(board);
            this.drZombies.updateBoard(board);
            this.drBullet.updateBoard(board);
            this.drBullet.setActive(true);
            drBoard.setActive(true);
            roundTransition = false;
        }
    }

    private void pause() {
        this.checkTimer.cancel();
        Sound.battle.stop();
       
            
    }

    private void resume() {
       Sound.battle.clip.setMicrosecondPosition(0);
       Sound.battle.clip.loop(Clip.LOOP_CONTINUOUSLY);
       FloatControl gc = (FloatControl) Sound.battle.clip.getControl(
                FloatControl.Type.MASTER_GAIN);
        gc.setValue(-12.0f); 
        
        board.winRound();
        checkTimer = new Timer();
        checker = new TimerTask() {
            @Override
            public void run() {
                try {
                    isPlayerAlive();
                } catch (IOException ex) {
                    Logger.getLogger(ZombieController.class.getName()).log(Level.SEVERE, null, ex);
                }
                displayVitals();
                try {
                    winRound();
                } catch (IOException ex) {
                    Logger.getLogger(ZombieController.class.getName()).log(
                            Level.SEVERE,
                            null,
                            ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ZombieController.class.getName()).log(
                            Level.SEVERE,
                            null,
                            ex);
                }
            }
        };
        checkTimer.schedule(checker, 0, 1000);
    }

    private void resetBoard() throws IOException {
        Sound.song.clip.setMicrosecondPosition(0);
        Sound.song.clip.loop(Clip.LOOP_CONTINUOUSLY);
        boardNum = new Random().nextInt(3) + 1;
        this.drBullet.setActive(false);
        this.drBoard.setActive(false);
        this.board = new Board(boardNum, 1);
        this.drBoard = new DrawBoard(board);
        this.drZombies = new DrawZombies(board);
        this.drBullet = new DrawBullet(board);
        theView.getBoard().addKeyListener(this.drBoard);
        theView.getBoard().addKeyListener(this.drBullet);
        this.board.getPlayer().setHealth(100);
        this.board.getPlayer().setAmmo(100);
        this.board.getPlayer().setGunLevel(1);
        this.board.getPlayer().setArmor(0);
        this.board.getPlayer().setKillCount(0);
        theView.getLblRound().setText("Round 1");
        theView.getcontrols().setVisible(true);
        
        this.theStore = new ShopView();
        this.shopController = new ShopController(this.theStore,
                                                 board.getPlayer());
        this.theStore.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                roundTransition = true;
            }
        });
    }
}
