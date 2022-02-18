package model;

import java.util.Random;

public class Edible {
	private static final double CREATION_PROB = 0.6;
	private static final double HEALTHY_PROB = 0.5;
	private static final Random rng = new Random();
	private int effect;
	
	/**
	 * Sets up the effect of the Edible item.
	 * Called via buildEdible() to enforce creation probability.
	 */
	private Edible() {
		effect = rng.nextInt(5);
		if(rng.nextDouble()>HEALTHY_PROB) {
			effect *= -1;
		}
	}

	private Edible(boolean healthy) {
		if(healthy){
			effect = 1;
		} else {
			effect = -1;
		}
	}
	
	/**
	 * Factory method to build an Edible object according to
	 * the CREATION_PROBABILITY. Returns null if chance so determines.
	 * @return The Edible object, or null
	 */
	public static Edible buildEdible() {
		if(rng.nextDouble() <= CREATION_PROB) {
			return new Edible();
		}
		return null;
	}

	/**
	 * Factory methods to build a healthy Edible object for using in tests
	 * @return The Edible object (will increase health by 1)
	 */
	public static Edible buildHealthyEdible() {
		return new Edible(true);
	}

	/**
	 * Factory methods to build an unhealthy Edible object for using in tests
	 * @return The Edible object (will decrease health by 1)
	 */
	public static Edible buildUnhealthyEdible() {
		return new Edible(false);
	}
	
	/**
	 * Returns the +ve or -ve effect that eating this Edible will have
	 * on a Player's health (note that the effect could also be 0). 
	 * @return The value of effect
	 */
	public int getEffect() {
		return effect;
	}
	
	/**
	 * Determines whether this Edible is considered healthy or not. 'Healthy'
	 * here means that it doesn't have a negative effect on a consuming
	 * Player (so an effect of '0' is considered healthy).
	 * @return Whether eating this Edible will have a non-negative effect
	 *          (true) or not (false)
	 */
	public boolean isHealthy() {
		return effect >= 0;
	}

}
