package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PlayerTest {
    @Test
    void testGetCurrentLocation() {
        Room r = new Room("place");
        Player p = new Player(r);
        assertEquals(p.getCurrentLocation().getDescription(), "place");
    }
}
