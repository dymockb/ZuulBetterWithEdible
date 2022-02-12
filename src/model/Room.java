package model;

import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */

public class Room 
{
    private String description;
    
    /* 
     * exits maps a direction (e.g. "north") to the destination reached
     * if you move in that direction from 'this' room. 
     */
    private HashMap<String,Room> exits;

    private Edible edible;
    
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        edible = Edible.buildEdible();
    }

    /**
     * Define an exit of this room.
     * to another room or is null (no exit there).
     * @param direction Which way to move
     * @param destination Where you get to
     */
    public void setExit(String direction, Room destination) 
    {
        exits.put(direction, destination);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Get the Room object representing a destination reached if
     * the player moves in the stated direction.
     * @param direction Where the player is going (e.g. "north")
     * @return The Room reached
     */
    public Room getExit(String direction) {
    	return exits.get(direction);
    }
    
    /**
     * Determine whether this room contains an edible item
     * @return The room has an edible item (true) or doesn't (false)
     */
    public boolean hasEdible() {
    	return edible != null;
    }
    
    /**
     * Consume the edible item, so it will no longer be available within the Room.
     * If no edible item exists in the room, return 0.
     * @return The effect that the item has on the consumer
     */
    public int eatEdible() {
    	if(hasEdible()) {
    		int effect = edible.getEffect();
    		edible = null;
    		return effect;
    	}
    	return 0;
    }
    
    
    
    public String toString() {
    	String longDescription = getDescription() + "\n";
    	if(hasEdible()) {
    		longDescription += "There is something to eat here.\n";
    		
    	}
    	longDescription += "Exits:";
       	for(String direction : exits.keySet()) {
       		longDescription += " " + direction;
       	}
       	return longDescription;
    }

}
