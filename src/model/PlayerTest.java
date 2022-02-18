package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PlayerTest {

    /**
    * Test that the probabilies of an item / edible appearing at the specified
    * value hold true in practice.  Create 100 of each and check how many are
    * acually instantiated.
    */
    @Test
    void testProbabilities() {
        int countOfEdibles = 0;
        int countOfItems = 0;
        for(int i = 0; i < 100; i++){
            if(!(null == Edible.buildEdible())){
                countOfEdibles ++;
            }
            if(!(null == Item.buildItem())){
                countOfItems ++;
            }
        }

        //check that the percentage of edibles & items instantiated is between 50% and 70%:
        assertEquals(true, countOfEdibles > 50 && countOfEdibles < 70);
        assertEquals(true, countOfItems > 50 && countOfItems < 70);

    }


    /**
    * Test the ability to pick up items only if they
    * exist in current location, and that the inventory array is updating correctly
    * The Boolean in the Room constructor indicates testing is on, and edibles / items will
    * always be created.
    */
    @Test
    void testInventory() {
        Room outside = new Room("outside the main entrance of the university", true);
        Room theater = new Room("in a lecture theater", true);
        Room pub = new Room("in the campus pub", true);
        Room lab = new Room("in a computing lab", true);
        Room office = new Room("in the computing admin office", true);
        
        // initialise room Items
        theater.setItem("notebook");
        pub.setItem("drink");
        lab.setItem("laptop");
        office.setItem("printer");
    
        Player p = new Player(outside);
        Command c = new Command("get", "testObject");
        //check that trying to pick up an item doesn't work
        assertEquals(false, p.getItem(c));

        //go to the pub
        p.setCurrentLocation(pub);
        c = new Command("get", "drink");
        //check that picking up the drink will work
        assertEquals(true, p.getItem(c));        

        //go to the theatre
        p.setCurrentLocation(theater);
        c = new Command("get", "notebook");
        //check that picking up the notebook will work
        assertEquals(true, p.getItem(c)); 
        
        //go to the lab
        p.setCurrentLocation(lab);
        c = new Command("get", "laptop");
        //check that picking up the laptop will work
        assertEquals(true, p.getItem(c));
        //also check that picking up the printer will not work
        c = new Command("get", "printer"); 
        assertEquals(false, p.getItem(c)); 

        //check that the inventory now has three items:
        assertEquals(3, p.getInventory().size());
    }

    /**
    * Test the effect of healthy/ unhealthy edibles on player health
    */
    @Test
    void testEdibles() {
        //Room outside = new Room("outside the main entrance of the university", true);
        Room theater = new Room("in a lecture theater", true);
        Room pub = new Room("in the campus pub", true);
        Room lab = new Room("in a computing lab", true);
        Room office = new Room("in the computing admin office", true);
        
        // initialise an edible in every room: 2 healthy, 2 unhealthy
        //boolean value indicates healthy (true) or unhealthy (false)
        //Test edibles will always increase or decrease health by 1.
        theater.setTestEdible(true);
        pub.setTestEdible(true);
        lab.setTestEdible(false);
        office.setTestEdible(false);
    
        //create a new Player in the theater
        Player p = new Player(theater);  
        //check health is at 10
        assertEquals(p.getHealth(), 10); 
        p.eat();
        //health should increase by 1 to 11
        assertEquals(p.getHealth(), 11);
        
        //check that it's no longer possible to eat in this room
        assertEquals(p.eat(), "Eat what? There's nothing here.");

        //go to the pub
        p.setCurrentLocation(pub);
        p.eat();
        //health should increase by 1 to 12
        assertEquals(p.getHealth(), 12);

        //go to the lab
        p.setCurrentLocation(lab);
        p.eat();
        //health should decrease by 1 to 11
        assertEquals(p.getHealth(), 11);

        //go to the lab
        p.setCurrentLocation(office);
        p.eat();
        //health should decrease by 1 to 10
        assertEquals(p.getHealth(), 10);

    }

}
