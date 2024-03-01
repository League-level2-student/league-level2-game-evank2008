import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Car {
	LetterKey keyW;
	LetterKey keyA;
	LetterKey keyS;
	LetterKey keyD;
	LetterKey keyQ;
	LetterKey keyI;
	LetterKey keyJ;
	LetterKey keyK;
	LetterKey keyL;
	LetterKey keyU;
	int x;
	int y;
	int width;
	int height;
	boolean leftKeys;
	BufferedImage carImage;
	public Car(int x, int y, String fileName, boolean leftKeys) {
		this.x=x;
		this.y=y;
		this.width=174;
		this.height=68;
		this.leftKeys=leftKeys;
		carImage = loadImage(fileName);
		if(leftKeys==true) {
			 keyW = new LetterKey(this,"w");
			 keyA = new LetterKey(this,"a");
			 keyS = new LetterKey(this,"s");
			 keyD = new LetterKey(this,"d");
			 keyQ = new LetterKey(this,"q");
		} else {
			 keyI = new LetterKey(this,"i");
			 keyJ = new LetterKey(this,"j");
			 keyK = new LetterKey(this,"k");
			 keyL = new LetterKey(this,"l");
			 keyU = new LetterKey(this,"u");
		}
	}
	void draw(Graphics g) {
		g.drawImage(carImage, x, y, width, height, null);
		
		if(leftKeys) {
			keyS.draw(g,1);
			keyW.draw(g,1);
			keyA.draw(g,1);
			keyD.draw(g,1);
			keyQ.draw(g,1);
			}
		else {
			keyI.draw(g,1);
			keyJ.draw(g,1);
			keyK.draw(g,1);
			keyL.draw(g,1);
			keyU.draw(g,1);
			}
	}
	public int getX() {
		return(x);
	}
	public int getY() {
		return(y);
	}
	BufferedImage loadImage (String fileName) {
		try {
			return (ImageIO.read(this.getClass().getResourceAsStream(fileName)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR loading image: " + fileName);
			e.printStackTrace();
			return(null);
		}
		}
}
