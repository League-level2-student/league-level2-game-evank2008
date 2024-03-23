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
	int timerCounter = 0;
	Timer driveTimer = new Timer(1000/60, this);
	Car carBlue;
	Car carRed;
	Graphics graphic;
	BufferedImage image;
	boolean lockKeyPress = false;
	boolean singlePlayer = false;
	ObjectManager man;
	public GamePanel() {
		carBlue = new Car(530,424,"cars/car_blue_right.png",true, driveTimer);
		carBlue.facingLeft=false;
		if(!singlePlayer) {carRed = new Car(795,424,"cars/car_red_left.png",false, driveTimer); carRed.facingLeft=true;}
		 man = new ObjectManager(carBlue,carRed, singlePlayer);
	}
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, Momentum.WIDTH, Momentum.HEIGHT);	
		//draw grid
		g.setColor(Color.gray);
		for(int i=53;i<1908;i+=53){
			g.drawLine(i, 0, i, 954);
		}
		for(int i=53;i<954;i+=53){
			g.drawLine(0, i, 1908, i);
		}
		if(!singlePlayer) {carRed.draw(g);}
		carBlue.draw(g);
		if(areYaReady()) {
			lockKeyPress=true;
			carBlue.drive();
			if(!singlePlayer) {
				carRed.drive();}
			driveTimer.start();
			resetKeys();
			lockKeyPress=false;
			repaint();
		}
	}

	boolean areYaReady() {
		if(carBlue.keys[4].hasBeenPressed) {
			if(singlePlayer) {
				return(true);
			} else {
			if(carRed.keys[4].hasBeenPressed) {
		return(true);
			} else {
				return(false);
			}		
			}
	} else {return(false); }
	}
	
	void resetKeys() {
		for(LetterKey l:carBlue.keys) {
			l.hasBeenPressed=false;
			l.color=1;
		}
		if(!singlePlayer) {
		for(LetterKey l:carRed.keys) {
			l.hasBeenPressed=false;
			l.color=1;
		} }
		
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
			if(!singlePlayer) {
			//red car
			for(LetterKey l:carRed.keys) {
				if(l.getLetter()==e.getKeyChar()) {
					l.color=2;
				}
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
			if(!singlePlayer) {
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
		}
		repaint();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		 if(e.getSource()==driveTimer) {
			if(timerCounter<26) {
				timerCounter++;
				if(!carBlue.hasThisCarCrashed) {
				carBlue.move(26);
				man.checkCollision(carBlue, carRed);
				}
				if(!singlePlayer) {
					if(!carRed.hasThisCarCrashed) {
					carRed.move(26);
					man.checkCollision(carRed, carBlue);
					}
				}
			} else {
				driveTimer.stop();
				timerCounter=0;
			} 
			repaint();
		} 
	}
	
}
