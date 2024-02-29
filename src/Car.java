import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Car {

	int x;
	int y;
	int width;
	int height;
	BufferedImage carImage;
	public Car(int x, int y, int width, int height, String fileName) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		carImage = loadImage(fileName);
	}
	void draw(Graphics g) {
		g.drawImage(carImage, x, y, width, height, null);
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
