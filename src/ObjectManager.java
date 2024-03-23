
public class ObjectManager {
Car carBlue;
Car carRed;
boolean singlePlayer;
	public ObjectManager(Car carBlue, Car carRed, boolean singlePlayer) {
		this.carBlue=carBlue;
		this.carRed=carRed;
		this.singlePlayer=singlePlayer;
	}
	void checkCollision(Car carToCheck, Car bystanderCar) { 
		if(!singlePlayer) {
			if(carToCheck.collisionHull.intersects(bystanderCar.collisionHull)) {
			carToCheck.crash();
			bystanderCar.crash();
			}	
		}
		
	}
}
