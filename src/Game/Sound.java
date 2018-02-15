/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2015
 *
 * Name: Ryan Greene, Jack Napor, Danny Toback, & Richard Huffman
 * Date: Dec 6, 2015
 * Time: 11:10:07 PM
 *
 * Project: csci205FinalProject
 * Package: Game
 * File: Sound
 * Description:
 *
 * ****************************************
 */
package Game;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * http://www.dreamincode.net/forums/topic/343804-how-to-add-background-music-to-my-2d-platformer-game/
 *
 * @author rth013
 */
public class Sound {

    public Clip clip;

    public static Sound song = new Sound("/Sounds/song1.wav");
    public static Sound bullet = new Sound("/Sounds/bullet.wav");
    public static Sound powerup = new Sound("/Sounds/powerup.wav");
    public static Sound kill = new Sound("/Sounds/kill.wav");
    public static Sound move = new Sound("/Sounds/move.wav");
    public static Sound hurt = new Sound("/Sounds/hurt.wav");
    public static Sound death = new Sound("/Sounds/death.wav");
    public static Sound falling = new Sound("/Sounds/falling.wav");
    public static Sound cashRegister = new Sound("/Sounds/cashRegister.wav");
    public static Sound armor = new Sound("/Sounds/armor.wav");
    public static Sound gun = new Sound("/Sounds/gun.wav");
    public static Sound shop = new Sound("/Sounds/Shop.wav");
    public static Sound battle = new Sound("/Sounds/battle.wav");

    public Sound(String path) {
        try {
            URL url = this.getClass().getResource(path);
            AudioInputStream aiStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(aiStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * loops a clip in its own separate thread
     */
    public void loop() {

        //this method only used for theme song, which is normally too loud
        FloatControl gc = (FloatControl) clip.getControl(
                FloatControl.Type.MASTER_GAIN);
        gc.setValue(-8.0f); // lower volume 8 decibles

        //loop
        try {
            if (clip != null) {
                new Thread() {
                    public void run() {
                        synchronized (clip) {
                            clip.stop();
                            clip.setFramePosition(0);
                            clip.loop(Clip.LOOP_CONTINUOUSLY);
                        }
                    }
                }.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //plays a clip in it's own separate thread
    public void play() {
        try {
            if (clip != null) {
                new Thread() {
                    public void run() {
                        synchronized (clip) {
                            clip.stop();
                            clip.setFramePosition(0);
                            clip.start();
                            
                        }
                    }
                }.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*** stops a clip if its running ***/
    public void stop(){

        if(clip == null){ return;}

        clip.stop();
    }


}
