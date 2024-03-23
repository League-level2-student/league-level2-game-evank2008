import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;


public class Car{
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
	String carImageName;
	BufferedImage carImage;
	Timer carTimer;
	Rectangle collisionHull;
	boolean hasThisCarCrashed = false;
	boolean facingLeft;
	public Car(int x, int y, String fileName, boolean leftKeys, Timer timer) {
		this.x=x;
		this.y=y;
		this.carImageName=fileName;
		this.width=159;
		this.height=53;
		this.leftKeys=leftKeys;
		carImage = loadImage(fileName);
		this.carTimer=timer;
		collisionHull = new Rectangle(x,y,width,height);
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
		if(!hasThisCarCrashed) {
		for(int i = 0; i<5;i++) {
			keys[i].draw(g);
		} }
		//bounding box
		collisionHull.setBounds(x, y, width, height); 
		g.setColor(Color.red);
		g.drawRect(collisionHull.x, collisionHull.y, collisionHull.width, collisionHull.height);
		g.setColor(Color.PINK);
		//predictor of destination
		if(!hasThisCarCrashed) {
		if(momentumX+getXAccel()>=0) {
			g.drawLine(x+159, y+27, x+159+(momentumX+getXAccel())*gridSpeedPx, y+27+(momentumY+getYAccel())*gridSpeedPx);
		} else {
			
		g.drawLine(x, y+27, x+(momentumX+getXAccel())*gridSpeedPx, y+27+(momentumY+getYAccel())*gridSpeedPx);
		}
		}
		}
	void drive() {
		momentumX+=getXAccel();
		momentumY+=getYAccel();
		x+=momentumX;
		y+=momentumY;
		//change direction if you changed direction
		if(momentumX<0) {
			facingLeft=true;
			if(leftKeys) {
				//you're blue now
				carImage=loadImage("cars/car_blue_left.png");
			} else {
				carImage=loadImage("cars/car_red_left.png");
			}	
		} else if(momentumX>0){
			facingLeft=false;
			if(leftKeys) {
				//you're blue now
				carImage=loadImage("cars/car_blue_right.png");
			} else {
				carImage=loadImage("cars/car_red_right.png");
			}	
		}
	}
	void move(int howManyTimesIsThisGoingToMove) {
		if(!hasThisCarCrashed) {
		x+=momentumX*(gridSpeedPx-1)/howManyTimesIsThisGoingToMove;
		y+=momentumY*(gridSpeedPx-1)/howManyTimesIsThisGoingToMove;
		}
	}
	void crash() {
		System.out.println("crash start, am i blue? " + leftKeys);
		hasThisCarCrashed=true;
		if(leftKeys&&facingLeft) {
			carImage=loadImage("cars/car_blue_left_smashed.png");
		} else if(leftKeys&&!facingLeft) {
			carImage=loadImage("cars/car_blue_right_smashed.png");
		} else if(!leftKeys&&facingLeft) {
			carImage=loadImage("cars/car_red_left_smashed.png");
		} else if(!leftKeys&&!facingLeft) {
			carImage=loadImage("cars/car_red_right_smashed.png");
		}
		System.out.println("crash done, am i blue? " + leftKeys);
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
