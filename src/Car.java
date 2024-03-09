import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Car {
	LetterKey[]keys=new LetterKey[5];
	private int xAccel;
	private int yAccel;
	private int gridSpeedPx = 53;
	int x;
	int y;
	int width;
	int height;
	int momentumX=0;
	int momentumY=0;
	boolean leftKeys;
	BufferedImage carImage;
	public Car(int x, int y, String fileName, boolean leftKeys) {
		this.x=x;
		this.y=y;
		this.width=159;
		this.height=53;
		this.leftKeys=leftKeys;
		carImage = loadImage(fileName);
		if(leftKeys==true) {
			keys[0] = new LetterKey(this,'w');
			keys[1] = new LetterKey(this,'a');
			keys[2] = new LetterKey(this,'s');
			keys[3] = new LetterKey(this,'d');
			keys[4] = new LetterKey(this,'q');
		} else {
			keys[0] = new LetterKey(this,'i');
			keys[1] = new LetterKey(this,'j');
			keys[2] = new LetterKey(this,'k');
			keys[3] = new LetterKey(this,'l');
			keys[4] = new LetterKey(this,'u');
		}
	}
	void draw(Graphics g) {
		g.drawImage(carImage, x, y, width, height, null);
		for(int i = 0; i<5;i++) {
			keys[i].draw(g);
		}
		//bounding box
		g.setColor(Color.red);
		g.drawRect(x, y, width, height);
		g.setColor(Color.PINK);
		//predictor of destination
		if(leftKeys) {
			g.drawLine(x+159, y+27, x+159+(momentumX+getXAccel())*gridSpeedPx, y+27+(momentumY+getYAccel())*gridSpeedPx);
		} else {
			//maybe replace with drawPolygon to increase thickness
		g.drawLine(x, y+27, x+(momentumX+getXAccel())*gridSpeedPx, y+27+(momentumY+getYAccel())*gridSpeedPx);
		}
		}
	void drive() {
		momentumX+=getXAccel();
		momentumY+=getYAccel();
		x+=momentumX*gridSpeedPx;
		y+=momentumY*gridSpeedPx;
	}
	public int getX() {
		return(x);
	}
	public int getY() {
		return(y);
	}
	public int getXAccel() {
		xAccel=0;
		if(keys[1].hasBeenPressed) {
			xAccel--;
		}
		if(keys[3].hasBeenPressed) {
			xAccel++;
		}
		return(xAccel);
	}
	public int getYAccel() {
		yAccel=0;
		if(keys[0].hasBeenPressed) {
			yAccel--;
		}
		if(keys[2].hasBeenPressed) {
			yAccel++;
		}
		return(yAccel);
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
