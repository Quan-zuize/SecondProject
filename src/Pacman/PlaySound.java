/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pacman;

/**
 *
 * @author nguye
 */
public class PlaySound implements Runnable {

    public void run() {
        startSound();
    }

    public static void startSound() {
        // new SpaceInvaders();
        MusicPlayer player = new MusicPlayer();
        player.play("audio.wav");
    }
}
