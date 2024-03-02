import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener{
	Car carBlue;
	Car carRed;
	Graphics graphic;
	BufferedImage image;
	boolean lockKeyPress = false;
	public GamePanel() {
		carBlue = new Car(500,400,"cars/car_blue_right.png",true);
		carRed = new Car(800,400,"cars/car_red_left.png",false);
	}
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, Momentum.WIDTH, Momentum.HEIGHT);	
		carRed.draw(g);
		carBlue.draw(g);
		if(areYaReady()) {
			lockKeyPress=true;
			//drive
			resetKeys();
			lockKeyPress=false;
			repaint();
		}
	}

	boolean areYaReady() {
		if(carBlue.keys[4].hasBeenPressed&&carRed.keys[4].hasBeenPressed) {
			return(true);
		} else {
			return(false);
		}
	}
	
	void resetKeys() {
		for(LetterKey l:carBlue.keys) {
			l.hasBeenPressed=false;
			l.color=1;
		}
		for(LetterKey l:carRed.keys) {
			l.hasBeenPressed=false;
			l.color=1;
		}
		System.out.println("keys have been reset");
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(!lockKeyPress) {
		//wasd+q for P1, ijkl+u for p2
		if(e.getKeyChar()=='w'||e.getKeyChar()=='a'||e.getKeyChar()=='s'||e.getKeyChar()=='d'||e.getKeyChar()=='q') {
			//blue car
			for(LetterKey l:carBlue.keys) {
				if(l.getLetter()==e.getKeyChar()) {
					l.color=2;
				}
			}
			
		} else if(e.getKeyChar()=='i'||e.getKeyChar()=='j'||e.getKeyChar()=='k'||e.getKeyChar()=='l'||e.getKeyChar()=='u') {
			//red car
			for(LetterKey l:carRed.keys) {
				if(l.getLetter()==e.getKeyChar()) {
					l.color=2;
				}
			}
		}
		repaint();
	}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(!lockKeyPress) {
		if(e.getKeyChar()=='w'||e.getKeyChar()=='a'||e.getKeyChar()=='s'||e.getKeyChar()=='d'||e.getKeyChar()=='q') {
			//blue car
			for(LetterKey l:carBlue.keys) {
				if(l.getLetter()==e.getKeyChar()) {
					l.hasBeenPressed = !l.hasBeenPressed;
					if(l.hasBeenPressed) {
						l.color=3;
					} else {
						l.color=1;
					}
				}
			}
			
		} else if(e.getKeyChar()=='i'||e.getKeyChar()=='j'||e.getKeyChar()=='k'||e.getKeyChar()=='l'||e.getKeyChar()=='u') {
			//red car
			for(LetterKey l:carRed.keys) {
				if(l.getLetter()==e.getKeyChar()) {
					l.hasBeenPressed = !l.hasBeenPressed;
					if(l.hasBeenPressed) {
						l.color=3;
					} else {
						l.color=1;
					}
				}
			}
		}
		repaint();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}
