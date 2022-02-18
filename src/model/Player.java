package model;
import java.util.ArrayList;

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
     * Try to pick up an item.  If there is an item, add it to the inventory
     * and remove it from the room. Otherwise print an error message.
     */
    public boolean getItem(Command command) 
    {
        if(!command.hasSecondWord() && getCurrentLocation().hasItem()){
            // if there is no second word, we don't know what to pick up...
            System.out.println("Pick up what?");
            return false;            
        }

        String item = command.getSecondWord();

        if(!getCurrentLocation().hasItem()){
            System.out.println("There is nothing to pick up here.");
            return false;
        }

        if (item.equals(getCurrentLocation().getItemName())){
            addToInventory(getCurrentLocation().getItem());
            getCurrentLocation().pickUpItem();
            System.out.println("You have picked up the " + item + ".");
            return true;
        } else {
            System.out.println("That item is not recognised.");
            return false;
        }

    }

	/**
	 * If an item has been picked up, add it to the inventory
	 */
	public void addToInventory(Item item){
		inventory.add(item);
	}

	public ArrayList<Item> getInventory(){
		return inventory;
	}

	public void printInventory(){
		if(inventory.size() > 0){
			System.out.print("You've picked up these items: ");
			for(Item item: inventory){
				System.out.print(item.getName() + " ");
			}
			System.out.println("");
		} else {
			System.out.println("You have no items.");
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
