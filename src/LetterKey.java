import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LetterKey {
	int color = 1;
	int offsetX;
	int offsetY;
	boolean hasBeenPressed=false;
	
Car car;
char letter;
String direction;
BufferedImage whiteImage;
BufferedImage grayImage;
BufferedImage greenImage;
BufferedImage image;
	public LetterKey(Car car, char letter) {
		this.car=car;
		this.letter=letter;
		whiteImage = loadImage("letter keys/"+letter+"_white.png");
		grayImage = loadImage("letter keys/"+letter+"_gray.png");
		greenImage = loadImage("letter keys/"+letter+"_green.png");
		if(letter=='s'||letter=='k') {
			offsetX=55;
			offsetY=-70;
		}
		if(letter=='q'||letter=='u') {
			offsetX=55;
			offsetY=-120;
		}
		if(letter=='a'||letter=='j') {
			offsetX=5;
			offsetY=-120;
		}
		if(letter=='d'||letter=='l') {
			offsetX=108;
			offsetY=-120;
		}
		if(letter=='w'||letter=='i') {
			offsetX=55;
			offsetY=-170;
		}
	}
	void draw(Graphics g) {
		switch(color) {
		case 1:
			image = whiteImage;
			break;
		case 2:
			image = grayImage;
			break;
		case 3:
			image = greenImage;
			break;
		}
		g.drawImage(image, car.getX()+offsetX, car.getY()+offsetY, 50, 50, null);
	}
	char getLetter() {
		return(letter);
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
