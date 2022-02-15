package controller;

import model.Command;
import model.Player;
import model.Room;
import util.Parser;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room startingPlace;
    private Player player;
    
    
    
    /**
     * The main method: this is what happens first when the program is run.
     * This just creates a new Game object, then plays the game.
     */
    public static void main(String[] args) {
    	Game theGame = new Game();
    	theGame.play();
    }
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialise room Items
        theater.setItem("notebook");
        pub.setItem("drink");
        lab.setItem("laptop");
        office.setItem("printer");

        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south",  lab);
        outside.setExit("west",  pub);
        theater.setExit("west",  outside);
        pub.setExit("east",  outside);
        lab.setExit("north",  outside);
        lab.setExit("east", office);
        office.setExit("west",  lab);
        
        // We added a tree to demonstrate the benefits of our refactoring
        Room tree = new Room("up a tree");
        outside.setExit("up", tree);
        tree.setExit("down", outside);

        startingPlace = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play (Player quits or dies).
     */
    public void play() 
    {            
    	player = new Player(startingPlace);
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Your final score was "+player.getScore());
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println("You are " + player);
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, or if the Player 
     * 				dies, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            System.out.println("Please type a two-word command.");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("eat")) {
        	System.out.println(player.eat());
        }
        else if (commandWord.equals("get")) {
        	getItem(command);
        }
        if(!player.isAlive()) {
        	System.out.println("You have died");
        	// set the wantToQuit flag to force the game to end
        	wantToQuit = true;
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university - as long as you are healthy.");
        System.out.println("Your health goes down by 2 when you move to a new location, "); 
        System.out.println("but it can also be affected by the things you eat.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   " + parser.getPrintableCommandWords());
    }

    /** 
     * Try to pick up an item.  If there is an item, add it to the inventory
     * and remove it from the room. Otherwise print an error message.
     */
    private boolean getItem(Command command) 
    {
        if(!command.hasSecondWord() && player.getCurrentLocation().hasItem()){
            // if there is no second word, we don't know what to pick up...
            System.out.println("Pick up what?");
            return false;            
        }

        String item = command.getSecondWord();

        if(!player.getCurrentLocation().hasItem()){
            System.out.println("There is nothing to pick up here.");
            return false;
        }

        if (item.equals(player.getCurrentLocation().getItemName())){
            player.getCurrentLocation().getItem();
            System.out.println("You have picked up the " + item + ".");
            return true;
        } else {
            System.out.println("That item is not recognised.");
            return false;
        }

    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentLocation().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player.setCurrentLocation(nextRoom);
            player.incrementScore();
            player.updateHealth(-2);
            System.out.println("You are " + player);
            System.out.println();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
