import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wall extends Rectangle {
	BufferedImage texture;

	public Wall(int x, int y, int width, int height, String textureFile, int gridSize) {
		this.x = gridSize * x;
		this.y = gridSize * y;
		this.width = gridSize * width;
		this.height = gridSize * height;
		texture = loadImage(textureFile);
	}

	void draw(Graphics g) {
		g.drawImage(texture, x, y, width, height, null);
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
