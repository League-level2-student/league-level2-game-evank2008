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

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	int gridSpeedPx = 53;
	int timerCounter = 0;
	Timer driveTimer = new Timer(1000 / 60, this);
	Car carBlue;
	Car carRed;
	Graphics graphic;
	BufferedImage image;
	boolean lockKeyPress = false;
	boolean singlePlayer = false;
	ObjectManager man;
	TrackBuilder bob;
	String gameEndReason;

	public GamePanel() {
		carBlue = new Car(1219, 159, "cars/car_blue_left.png", true, driveTimer, gridSpeedPx);
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
				case 0:
					//no winner
					System.out.println("nothing happened");
					break;
				case 1:
					//blue survived
					System.out.println("blue wins");
						break;
				case 2:
					//red survived
					System.out.println("red wins");
						break;
				case 3:
					//none survived
					System.out.println("yall losers");
						break;
				}
				System.out.println(gameEndReason);
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
				} else {
					//blue finished before red
					caseTemp=1;
					gameEndReason="Blue finished before Red";
				}
			} else {
				//blue did not hit the finish line
				if(man.checkFinishCollision(carRed)) {
					//red finished before blue
					caseTemp=2;
					gameEndReason="Red finished before Blue";
				} else {
					//nobody finished
					caseTemp=0;
					gameEndReason="Nothing interesting happened";
				}
			}
			break;
		case 1:
			//blue survived
		caseTemp=1;
		gameEndReason="Red experienced kinetic energy";
				break;
		case 2:
			//red survived
		caseTemp=2;
		gameEndReason="Blue experienced kinetic energy";
				break;
		case 3:
			//none survived
		caseTemp=3;
		gameEndReason="Both cars experienced kinetic energy";
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
}
