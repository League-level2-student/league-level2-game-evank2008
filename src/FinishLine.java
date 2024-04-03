
public class FinishLine extends Wall{
boolean forBlue;
	public FinishLine(int x, int y, int width, int height, String textureFile, int gridSize, boolean forBlue) {
		super(x, y, width, height, textureFile, gridSize);
		this.forBlue=forBlue;
	}

}
