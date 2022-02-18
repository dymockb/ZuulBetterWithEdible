package model;

import java.util.Random;

public class Item {
	private static final double CREATION_PROB = 0.6;
	private static final Random rng = new Random();
	
	private String name;
	/**
	 * Sets up the effect of the Edible item.
	 * Called via buildItem() to enforce creation probability.
	 */
	private Item() {
		name = "blankItem";
	}
	
	/**
	 * Factory method to build an Item object according to
	 * the CREATION_PROBABILITY. Returns null if chance so determines.
	 * @return The Item object, or null
	 */
	public static Item buildItem() {
		if(rng.nextDouble() <= CREATION_PROB) {
			return new Item();
		}
		return null;
	}

	/**
	 * Factory method to build an Item object for use in tests.
	 * @return The Item object
	 */
	public static Item buildTestItem() {
		return new Item();
	}

	public void setItemName(String itemName){
		name = itemName;
	}
	
	/** 
	 * @return The item name
	 */
	public String getName() {
		return name;
	}
	
}
