package view;
//
// GameWindow
// Window Controller
// (c) 2013 Chase and the Cat Daddiez
//
// This class handles the creation of the main window and the drawing of
// all of the objects onto a canvas.
//
// 
//

import controller.Game;
import model.VisibleObject;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import java.awt.event.KeyListener;
import java.awt.RenderingHints;
import java.awt.Font;
import java.io.FileInputStream;

public class GameWindow {
	
	private static final String TITLE = "Mario Clone";
	
	private static final int WIDTH = 256;
	private static final int HEIGHT = 224;
	
	private static int scaleFactor = 3;
	
	private JFrame window;
	private Canvas view;
	
	private BufferStrategy bs;
	
	private Graphics2D g;
	private Font emulogic;
	
	Color black = new Color(0, 0, 0); // Black
	Color white = new Color(255, 255, 255);
	
	public GameWindow(Game game) {
		
		window = new JFrame(TITLE);
		view = new Canvas();
		
		view.setBounds(0, 0, WIDTH * scaleFactor, HEIGHT * scaleFactor);
		view.setIgnoreRepaint(true);
		
		view.addKeyListener((KeyListener) game);
		
		window.setSize(WIDTH * scaleFactor, HEIGHT * scaleFactor);
		window.setIgnoreRepaint(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(view);
		window.getContentPane().setPreferredSize(new Dimension(WIDTH * scaleFactor, HEIGHT * scaleFactor));
		window.pack();
		window.setResizable(false);
		window.setVisible(true);
		
		view.createBufferStrategy(2);
		
		bs = view.getBufferStrategy();
		g = (Graphics2D) view.getBufferStrategy().getDrawGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		
		try {
			emulogic = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("font/emulogic.ttf"));
		}
		catch (Exception e) {
			System.exit(0);
		}
		
		emulogic = emulogic.deriveFont(8f * scaleFactor);
		
		g.setFont(emulogic);
		
		clear();
		
	}
	
	public void drawVisibleObject(VisibleObject v, int xOffset, int yOffset, boolean flipped) {
		
		if (flipped) {
			g.drawImage(v.getImage(), ((v.getX() - xOffset) + v.getWidth()) * scaleFactor, (v.getY() - yOffset) * scaleFactor, -v.getWidth() * scaleFactor, v.getHeight() * scaleFactor, null);
		}
		else {
			g.drawImage(v.getImage(), (v.getX() - xOffset) * scaleFactor, (v.getY() - yOffset) * scaleFactor, v.getWidth() * scaleFactor, v.getHeight() * scaleFactor, null);
		}
		
	}
	
	public void drawBackgroundColor(Color c) {
		g.setColor(c);
		g.fillRect(0, 0, WIDTH * scaleFactor, HEIGHT * scaleFactor);
	}
	
	public void drawString(String s, int x, int y) {
		g.setColor(white);
		g.drawString(s, x * scaleFactor, (y + 8) * scaleFactor);
		
	}
	
	public void swapBuffers() {
		bs.show();
	}
	
	public void clear() {
		
		g.setColor(black);
		g.fillRect(0, 0, WIDTH * scaleFactor, HEIGHT * scaleFactor);	
			
	}
	
}