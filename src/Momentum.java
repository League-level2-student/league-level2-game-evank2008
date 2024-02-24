import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Momentum {

	GamePanel gpanel;
	JFrame frame;
	public static final int WIDTH = 1511;
	public static final int HEIGHT = 850;
	
	public static void main(String[] args) {
		Momentum men = new Momentum();
		
		men.setup();
	}
	
	public Momentum() {
		frame = new JFrame();
		gpanel = new GamePanel();
	}
	void setup() {
		frame.add(gpanel);
		frame.addKeyListener(gpanel);
		frame.setVisible(true);
		frame.setSize(WIDTH,HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
