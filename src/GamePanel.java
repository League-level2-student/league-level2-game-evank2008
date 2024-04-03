import java.awt.Color;
import java.awt.Font;
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

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	int gridSpeedPx = 53;
	int timerCounter = 0;
	Timer driveTimer = new Timer(1000 / 60, this);
	Timer showScreenTimer = new Timer(1000/60, this);
	Car carBlue;
	Car carRed;
	Graphics graphic;
	BufferedImage image;
	BufferedImage endScreen;
	boolean lockKeyPress = false;
	boolean singlePlayer = false;
	ObjectManager man;
	TrackBuilder bob;
	String gameEndReason;
	int screenTimerCounter = 0;
	
	Font deathFont = new Font("Trebuchet MS", Font.BOLD, 30);
	public GamePanel() {
		carBlue = new Car(530, 159, "cars/car_blue_left.png", true, driveTimer, gridSpeedPx);
		carBlue.facingLeft = false;
		if (!singlePlayer) {
			carRed = new Car(1219, 742, "cars/car_red_right.png", false, driveTimer, gridSpeedPx);
			carRed.facingLeft = true;
		}
		bob = new TrackBuilder(gridSpeedPx);
		bob.buildTrack1();
		man = new ObjectManager(carBlue, carRed, singlePlayer, bob);
	}

	@Override
	public void paintComponent(Graphics g) {
			g.setFont(deathFont);
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, Momentum.WIDTH, Momentum.HEIGHT);
		// draw grid
		g.setColor(Color.gray);
		for (int i = 53; i < 1908; i += 53) {
			g.drawLine(i, 0, i, 954);
		}
		for (int i = 53; i < 954; i += 53) {
			g.drawLine(0, i, 1908, i);
		}
		// draw track
		bob.drawTrack(g);
		if (!singlePlayer)
			 {carRed.draw(g);}
			 carBlue.draw(g);
			if (areYaReady()) {
				lockKeyPress = true;
				carBlue.drive();
				if (!singlePlayer) {
					carRed.drive();
				}
				driveTimer.start();
				resetKeys();
				lockKeyPress = false;
				repaint();
			}
		
	if(screenTimerCounter>0) { 
			//time to show the end screen
			g.drawImage(endScreen, ((7*screenTimerCounter)/15-21)*gridSpeedPx,gridSpeedPx*2,gridSpeedPx*21,gridSpeedPx*14,null);
			g.setColor(new Color(0,0,0));
			
			g.drawString(gameEndReason, ((7*screenTimerCounter)/15-15)*gridSpeedPx, 12*gridSpeedPx-15);
	}
		
	}

	boolean areYaReady() {
		if (carBlue.keys[4].hasBeenPressed) {
			if (singlePlayer) {
				return (true);
			} else {
				if (carRed.keys[4].hasBeenPressed) {
					return (true);
				} else {
					return (false);
				}
			}
		} else {
			return (false);
		}
	}

	void resetKeys() {
		for (LetterKey l : carBlue.keys) {
			l.hasBeenPressed = false;
			l.color = 1;
		}
		if (!singlePlayer) {
			for (LetterKey l : carRed.keys) {
				l.hasBeenPressed = false;
				l.color = 1;
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!lockKeyPress) {
			// wasd+q for P1, ijkl+u for p2
			if (e.getKeyChar() == 'w' || e.getKeyChar() == 'a' || e.getKeyChar() == 's' || e.getKeyChar() == 'd'
					|| e.getKeyChar() == 'q') {
				// blue car
				for (LetterKey l : carBlue.keys) {
					if (l.getLetter() == e.getKeyChar()) {
						l.color = 2;
					}
				}

			} else if (e.getKeyChar() == 'i' || e.getKeyChar() == 'j' || e.getKeyChar() == 'k' || e.getKeyChar() == 'l'
					|| e.getKeyChar() == 'u') {
				if (!singlePlayer) {
					// red car
					for (LetterKey l : carRed.keys) {
						if (l.getLetter() == e.getKeyChar()) {
							l.color = 2;
						}
					}
				}
			}
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (!lockKeyPress) {
			if (e.getKeyChar() == 'w' || e.getKeyChar() == 'a' || e.getKeyChar() == 's' || e.getKeyChar() == 'd'
					|| e.getKeyChar() == 'q') {
				// blue car
				for (LetterKey l : carBlue.keys) {
					if (l.getLetter() == e.getKeyChar()) {
						l.hasBeenPressed = !l.hasBeenPressed;
						if (l.hasBeenPressed) {
							l.color = 3;
						} else {
							l.color = 1;
						}
					}
				}

			} else if (e.getKeyChar() == 'i' || e.getKeyChar() == 'j' || e.getKeyChar() == 'k' || e.getKeyChar() == 'l'
					|| e.getKeyChar() == 'u') {
				// red car
				if (!singlePlayer) {
					for (LetterKey l : carRed.keys) {
						if (l.getLetter() == e.getKeyChar()) {
							l.hasBeenPressed = !l.hasBeenPressed;
							if (l.hasBeenPressed) {
								l.color = 3;
							} else {
								l.color = 1;
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
		if (e.getSource() == driveTimer) {
			if (timerCounter < 26) {
				timerCounter++;
				if (!carBlue.hasThisCarCrashed) {
					carBlue.move(26);
					man.checkCollision(carBlue, carRed);
				}
				if (!singlePlayer) {
					if (!carRed.hasThisCarCrashed) {
						carRed.move(26);
						man.checkCollision(carRed, carBlue);
					}
				}
				repaint();
			} else {
				driveTimer.stop();
				timerCounter = 0;
				repaint();
				// check for winner here
				switch(checkWinner()) {
				case 1:
					//blue survived
					
					showEndScreen();
						break;
				case 2:
					//red survived
					showEndScreen();
						break;
				case 3:
					//none survived
					showEndScreen();
						break;
				}
			}

		} else if(e.getSource()==showScreenTimer) {
			if(screenTimerCounter<60) {
			repaint();
			screenTimerCounter++;	
			} else {
			showScreenTimer.stop();
			}
		}
	}
	int checkWinner() {
		int caseTemp;
		//check for a winner by death
		switch(crashStatus()) {
		case 0:
			//no crashes
			//dont return 0 here until you checked for winner by lap
			if(man.checkFinishCollision(carBlue)) {
				//blue hit the finish line
				if(man.checkFinishCollision(carRed)) {
					//but red did too
					
					//tie
					caseTemp=5;
					gameEndReason="Both cars passed their finish line";
					endScreen=loadImage("props/neutralboard.png");
				} else {
					//blue finished before red
					caseTemp=1;
					gameEndReason="Blue finished before Red";
					endScreen=loadImage("props/blueboard.png");
				}
			} else {
				//blue did not hit the finish line
				if(man.checkFinishCollision(carRed)) {
					//red finished before blue
					caseTemp=2;
					gameEndReason="Red finished before Blue";
					endScreen=loadImage("props/redboard.png");
				} else {
					//nobody finished
					caseTemp=0;
					gameEndReason="Nothing interesting happened";
					endScreen=loadImage("props/neutralboard.png");
				}
			}
			break;
		case 1:
			//blue survived
		caseTemp=1;
		gameEndReason="Red experienced kinetic energy";
		endScreen=loadImage("props/blueboard.png");
				break;
		case 2:
			//red survived
		caseTemp=2;
		gameEndReason="Blue experienced kinetic energy";
		endScreen=loadImage("props/redboard.png");
				break;
		case 3:
			//none survived
		caseTemp=3;
		gameEndReason="Both cars experienced kinetic energy";
		endScreen=loadImage("props/neutralboard.png");
			break;
				default:
				caseTemp=1337;
				gameEndReason="help the world is ending";
		}
		return(caseTemp);
	}
	int crashStatus() {
		if(!carBlue.hasThisCarCrashed) {
			if(!carRed.hasThisCarCrashed) {
				//neither car crashed, 0 for no crash
				return(0);
			}
			else {
				//Red crashed but Blue didn't, Blue wins!
				return(1);
			}
		} else {
			if(!carRed.hasThisCarCrashed) {
				//Blue crashed but Red didn't, Red wins!
				return(2);
			}
			else {
				//both cars crashed, everybody loses
				return(3);
			}
		}
	}
	void stopGame() {
		lockKeyPress=true;
	}
	void showEndScreen() {
		stopGame();
		showScreenTimer.start();
	}
	BufferedImage loadImage(String fileName) {
		try {
			return (ImageIO.read(this.getClass().getResourceAsStream(fileName)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR loading image: " + fileName);
			e.printStackTrace();
			return (null);
		}
	}
}
