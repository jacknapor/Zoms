/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Nov 16, 2015
 * Time: 8:22:05 AM
 *
 * Project: csci205FinalProject
 * Package: Game
 * File: ShopController
 * Description: The controller class for the Shop, a popup window that allows
 * the player to make in game purcahses
 *
 * ****************************************
 */
package Game;

import Piece.Player;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author jrn011
 */
public class ShopController implements ActionListener {
    private ShopView shopView;
    private Player player;
    private boolean isWindowClosed = true;

    public ShopController(ShopView s, Player p) {
        
        this.shopView = s;
        this.player = p;

        this.shopView.getJlabel6().setText(Double.toString(p.getHealth()));
        this.shopView.getJlabel7().setText(Double.toString(p.getAmmo()));
        this.shopView.getJlabel8().setText(Double.toString(p.getArmor()));
        this.shopView.getJlabel9().setText(Double.toString(p.getDamage()));
        this.shopView.getJlabel15().setText(Double.toString(p.getPoints()));
        this.shopView.getJButton1().addActionListener(this);
        this.shopView.getJButton2().addActionListener(this);
        this.shopView.getJButton3().addActionListener(this);
        this.shopView.getJButton4().addActionListener(this);
    }

    /**
     * This method houses all of the actions that are being performed by the
     * player on the board at any time
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.shopView.getJButton1()) {
            if (player.points >= 50 && player.getHealth() < 100) {
                player.points -= 50;
                player.setHealth(100);
                Sound.cashRegister.play();
                updateView(player);
            } else if (player.points < 50) {
                JOptionPane.showMessageDialog(shopView, "Not enough points!");
            } else if (player.getHealth() == 100) {
                JOptionPane.showMessageDialog(shopView,
                                              "Already at full health!");
            }
        }
        if (e.getSource() == this.shopView.getJButton2()) {

            String response = JOptionPane.showInputDialog(null,
                                                          "How much ammo would you like to purchase (1 Bullet=1 Point)",
                                                          "Purchase Ammo",
                                                          JOptionPane.PLAIN_MESSAGE);
            System.out.println(response);
            if(response!=null){
            try {
                
                int requested = Integer.parseInt(response);
                if(requested>0){
                if (player.points >= requested) {
                    player.points -= requested;
                    player.setAmmo((player.getAmmo() + requested));
                    Sound.cashRegister.play();

                    updateView(player);
                } else if (player.points < requested) {
                    JOptionPane.showMessageDialog(shopView,
                                                  "Not enough points!");

                }

            }else{
                    JOptionPane.showMessageDialog(shopView,
                                              "Please enter a value greater than 0.");
                }} catch (Exception a) {
                JOptionPane.showMessageDialog(shopView,
                                              "Invalid Entry");
            }
            

        }}

        if (e.getSource() == this.shopView.getJButton3()) {
            if (player.points >= 100 && player.getArmor() < 100) {
                player.points -= 100;
                player.setArmor(100);
                Sound.armor.play();
                updateView(player);
            } else if (player.points < 100) {
                JOptionPane.showMessageDialog(shopView, "Not enough points!");
            }
        }

        if (e.getSource() == this.shopView.getJButton4()) {
            System.out.println(player.getGunLevel());
            if (player.getGunLevel() == 0) {
                if (player.points >= 100 && player.getGunLevel() != 2) {
                    Sound.gun.play();
                    player.points -= 100;
                    player.upgradeGun();
                    this.shopView.getJButton4().setText(
                            "<html>Upgrade Weapon<br>(250 points)</html>");
                    updateView(player);
                }
            } else if (player.getGunLevel() == 1) {
                if (player.points >= 250) {
                    Sound.gun.play();
                    player.points -= 250;
                    player.upgradeGun();
                    this.shopView.getJButton4().setEnabled(false);
                    updateView(player);
                }
            }
        }
    }

    /**
     * updates the shop and the board current view
     */
    public void updateView(Player q) {

        this.shopView.getJlabel6().setText(Double.toString(q.getHealth()));
        this.shopView.getJlabel7().setText(Double.toString(q.getAmmo()));
        this.shopView.getJlabel8().setText(Double.toString(q.getArmor()));
        this.shopView.getJlabel9().setText(Double.toString(q.getDamage()));
        this.shopView.getJlabel15().setText(Double.toString(q.points));
        this.shopView.validate();
        this.shopView.repaint();

    }

}
