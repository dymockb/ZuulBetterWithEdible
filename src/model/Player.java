import java.util.ArrayList;
package model;

public class Player {
	private int score;
	private int health;
	private Room currentLocation;
	private ArrayList<Item> inventory;
	
	/**
	 * Create a Player with default score and health. Player starts
	 * in the stated location
	 * @param currentLocation Where the Player starts the game
	 */
	public Player(Room currentLocation) {
		score = 0;
		health = 10;
		this.currentLocation = currentLocation;
		inventory = new ArrayList<Item>();
	}

	/**
	 * Get the current score of the Player
	 * @return The Player's score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Increase the Player's score by one point
	 */
	public void incrementScore() {
		score++;
	}

	/**
	 * Get the current health of the Player
	 * @return The Player's current health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Alter the Player's health by the amount indicated.
	 * @param health
	 */
	public void updateHealth(int health) {
		this.health += health;
		if(this.health < 0) {
			this.health = 0;
		}
	}
	/**
	 * Is the Player alive? Health will be greater than zero in that case.
	 * @return Whether the Player is alive (true) or dead (false)
	 */
	public boolean isAlive() {
		return health > 0;
		/*
		 * This is the same as:
		 * 
		 * if(health > 0){
		 *     return true;
		 * }
		 * else {
		 *     return false;
		 * }     
		 * 
		 * because the value being returned (true or false) is identical
		 * to the value of the expression that causes it to be returned.
		 * We return true when 'health > 0' evaluates to true; we return
		 * false when 'health > 0' evaluates to false... so we can just
		 * return the value that the expression evaluates to.
		 * 
		 */
	}

	/**
	 * Get the Player's current location
	 * @return The Player's location
	 */
	public Room getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * Change the Player's location, i.e. enter a room
	 * @param currentLocation Where the Player is moving to
	 */
	public void setCurrentLocation(Room currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	public String eat() {
		if(getCurrentLocation().hasEdible()) {
			int effect = getCurrentLocation().eatEdible();
			updateHealth(effect);
			return "Your health changes by " + effect;
		}
		else {
			return "Eat what? There's nothing here.";
		}
	}
	
	/**
	 * Get a human-readable description of the Player-object.
	 * Here we include everything that the Room (current location)
	 * will include in its own description, AND the Player's score
	 * and current health.
	 * @return The formatted description
	 */
	public String toString() {
		String description = currentLocation.toString()+"\n";
		description += "Score: "+getScore()+"     Health: "+getHealth();
		return description;
	}
	

}
