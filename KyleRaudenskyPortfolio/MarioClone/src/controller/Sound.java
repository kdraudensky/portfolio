//
// MusicController
// Music/SE Utility Class
// (c) 2013 Chase and the Cat Daddiez
//
// A class that wraps all of the annoying background calls to handle
// changing/playing music and sound effects.
//
// Controlling the playing of BGM and SFX is annoying if used in an
// object-oriented manner; too many references to keep track of.
// By implementing the controller as an enum, anything can
// easily reference it from anywhere in the application.
//

package controller;

import java.io.File;
import java.net.URI;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public enum Sound {
	
    BOWSERFALLS("bowserfalls.wav"),
    BOWSERFIRE("bowserfire.wav"),
    BREAKBLOCK("breakblock.wav"),
    BUMP("bump.wav"), 
    COIN("coin.wav"),
    FIREBALL("fireball.wav"),
    FIREWORKS("fireworks.wav"),
    FLAGPOLE("flagpole.wav"),
    GAMEOVER("gameover.wav"),
    JUMPSMALL("jumpsmall.wav"),
    JUMPSUPER("jumpsuper.wav"),
    KICK("kick.wav"),
    MARIODIES("mariodies.wav"),
    ONEUP("oneup.wav"),
    PAUSE("pause.wav"),
    PIPE("pipe.wav"),
    POWERUP("powerup.wav"),
    POWERUPAPPEARS("powerupappears.wav"),
    STAGECLEAR("stageclear.wav"),
    STOMP("stomp.wav"),
    VINE("vine.wav"),
    WARNING("warning.wav"),
    WORLDCLEAR("worldclear.wav"),
    MAINTHEME("JoeMarioMixFinal.wav");

    private String soundTheme;
    private Media myMedia;
    private MediaPlayer myMediaPlayer;

    Sound(String fileName) {
		
		this.soundTheme = "mario";

		fileName = "sfx/" + this.soundTheme + "/" + fileName;
		File file = new File(fileName);
		URI myURI = file.toURI();
	
		// Create a new JFXPanel to force the sounds to be loaded and the Media
		// and MediaPlayer classes to be initialized. 
		// Warnings suppressed.
	
		@SuppressWarnings("unused")
		JFXPanel jfxPanel = new JFXPanel();
	
		myMedia = new Media(myURI.toString());
		myMediaPlayer = new MediaPlayer(myMedia);
	
    }
    
    public void changeVolume(Double volume) {
		myMediaPlayer.setVolume(volume);
    }

    public void play() {
		
		if (myMediaPlayer.onPlayingProperty() != null) {
	    	myMediaPlayer.stop();
		}
		
		myMediaPlayer.play();
		
    }
    
    public void playLoop() {
		
		this.play();
		myMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		
    }

    public void pause() {
		myMediaPlayer.pause();
    }
    
    public void continuePlay() {
		myMediaPlayer.play();
    }

    public void stop() {
		myMediaPlayer.stop();
    }
    
}