
public class ObjectManager {
	Car carBlue;
	Car carRed;
	boolean singlePlayer;
	TrackBuilder builder;

	public ObjectManager(Car carBlue, Car carRed, boolean singlePlayer, TrackBuilder builder) {
		this.carBlue = carBlue;
		this.carRed = carRed;
		this.singlePlayer = singlePlayer;
		this.builder = builder;
	}

	void checkCollision(Car carToCheck, Car bystanderCar) {
		// car on car
		if (!singlePlayer) {
			if (carToCheck.collisionHull.intersects(bystanderCar.collisionHull)) {
				carToCheck.crash();
				bystanderCar.crash();
			}
		}
		// car on wall
		for (Wall w : builder.wallsList) {
			if (carToCheck.collisionHull.intersects(w)) {
				carToCheck.crash();
			}
		}
	}

	boolean checkFinishCollision(Car carToCheck) {
		if(carToCheck.leftKeys) {
			//blue car
			for (FinishLine f : builder.finishListBlue) {
				if(carToCheck.collisionHull.intersects(f)) {
					return(true);
				}
			}
		} else {
			//red car
			for (FinishLine f : builder.finishListRed) {
				if(carToCheck.collisionHull.intersects(f)) {
					return(true);
				} 
			}
		}
		return(false);
	}
}