import java.awt.Graphics;
import java.util.ArrayList;

public class TrackBuilder {
	int gridSize;

	public TrackBuilder(int gridSize) {
		this.gridSize = gridSize;
	}

	ArrayList<Wall> wallsList = new ArrayList<>();
	ArrayList<FinishLine> finishListRed = new ArrayList<>();
	ArrayList<FinishLine> finishListBlue = new ArrayList<>();
	ArrayList<Wall> yellowList = new ArrayList<>();

	void buildTrack1() {
		wallsList = new ArrayList<>();
		// mainframe
		addWall(0, 0, 36, 1);
		addWall(0, 17, 36, 1);
		addWall(0, 1, 1, 16);
		addWall(35, 1, 1, 16);
		// top left curve
		addWall(1, 1, 1, 5);
		addWall(2, 1, 4, 1);
		addWall(2, 2, 2, 1);
		addWall(2, 3, 1, 1);
		// bottom left curve
		addWall(1, 12, 1, 5);
		addWall(2, 16, 4, 1);
		addWall(2, 15, 2, 1);
		addWall(2, 14, 1, 1);
		// top right curve
		addWall(34, 1, 1, 5);
		addWall(30, 1, 4, 1);
		addWall(32, 2, 2, 1);
		addWall(33, 3, 1, 1);
		// top right curve
		addWall(34, 12, 1, 5);
		addWall(30, 16, 4, 1);
		addWall(32, 15, 2, 1);
		addWall(33, 14, 1, 1);
		// top protrusion
		addWall(13, 1, 10, 1);
		addWall(15, 2, 6, 1);
		addWall(16, 3, 4, 2);
		// bottom protrusion
		addWall(13, 16, 10, 1);
		addWall(15, 15, 6, 1);
		addWall(16, 13, 4, 2);

		addWall(17, 8, 2, 2);

		addWall(7, 13, 1, 1);
		addWall(28, 4, 1, 1);

		for (int i = 0; i < 6; i++) {
			addWall(20 + i, 13 - i, 2, 1);
		}
		for (int i = 0; i < 6; i++) {
			addWall(9 + i, 9 - i, 2, 1);
		}
		
		addLineBlue(19,13,4,3);
		yellowList.add(new Wall(23,12,1,5,"textures/yellow.png",gridSize));
		addLineRed(13,2,4,3);
		yellowList.add(new Wall(12,1,1,5,"textures/yellow.png",gridSize));
	}
	void addLineRed(int x, int y, int width, int height) {
		for (int iy = y; iy < height + y; iy++) {
			for (int ix = x; ix < width + x; ix++) {
				finishListRed.add(new FinishLine(ix, iy, 1, 1, "textures/checkerboard_red.png", gridSize,false));
			}
		}
	}
	void addLineBlue(int x, int y, int width, int height) {
		for (int iy = y; iy < height + y; iy++) {
			for (int ix = x; ix < width + x; ix++) {
				finishListBlue.add(new FinishLine(ix, iy, 1, 1, "textures/checkerboard_blue.png", gridSize,true));
			}
		}
	}

	void addWall(int x, int y, int width, int height) {
		wallsList.add(new Wall(x, y, width, height, "textures/heavy.png", gridSize));
	}

	void drawTrack(Graphics g) {
		for (FinishLine f : finishListBlue) {
			f.draw(g);
		}
		for (FinishLine f : finishListRed) {
			f.draw(g);
		}
		for (Wall y : yellowList) {
			y.draw(g);
		}
		for (Wall w : wallsList) {
			w.draw(g);
		}
	}
}
