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
	public GamePanel() {
		carBlue = new Car(500,400,"car_blue_right.png",true);
		carRed = new Car(800,400,"car_red_left.png",false);
	}
	@Override
	public void paintComponent(Graphics g) {
		drawBackground(g);
		carRed.draw(g);
		carBlue.draw(g);
	}
	void drawBackground(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, Momentum.WIDTH, Momentum.HEIGHT);	
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		//wasd+q for P1, ijkl+u for p2
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
}
