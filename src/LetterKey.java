import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LetterKey {
	int white = 1;
	int gray = 2;
	int green = 3;
	int offsetX;
	int offsetY;
Car car;
String letter;
String direction;
BufferedImage whiteImage;
BufferedImage grayImage;
BufferedImage greenImage;
	public LetterKey(Car car, String letter) {
		this.car=car;
		this.letter=letter;
		whiteImage = loadImage("letter keys/"+letter+"_white.png");
		grayImage = loadImage("letter keys/"+letter+"_gray.png");
		greenImage = loadImage("letter keys/"+letter+"_green.png");
		if(letter=="s"||letter=="k") {
			offsetX=55;
			offsetY=-70;
		}
		if(letter=="q"||letter=="u") {
			offsetX=55;
			offsetY=-120;
		}
		if(letter=="a"||letter=="j") {
			offsetX=5;
			offsetY=-120;
		}
		if(letter=="d"||letter=="l") {
			offsetX=108;
			offsetY=-120;
		}
		if(letter=="w"||letter=="i") {
			offsetX=55;
			offsetY=-170;
		}
	}
	void draw(Graphics g, int color) {
		switch(color) {
		case 1:
			g.drawImage(whiteImage, car.getX()+offsetX, car.getY()+offsetY, 50, 50, null);
			break;
		case 2:
			break;
		case 3:
			break;
		}
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
