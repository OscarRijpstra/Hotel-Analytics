package hotel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class RoomTest {
    
    @Test
    void testConstructor() {
        Integer roomsCount1 = Room.getAllRooms().size();

        assertEquals(0, roomsCount1);

        Room room1 = new Room(1, 100.0);

        Integer roomsCount2 = Room.getAllRooms().size();

        assertEquals(1, roomsCount2);

        room1.delete();
    }
    
    @Test
    void testGetRoom() {
        Integer roomNumber11 = 11;
        Integer roomNumber12 = 12;
        Integer roomNumber13 = 13;

        Room room12 = new Room(roomNumber12, 100.0);

        assertNotSame(Room.getRoom(roomNumber11), room12); 
        
        assertSame(Room.getRoom(roomNumber12), room12); 
        
        assertNotSame(Room.getRoom(roomNumber13), room12); 
        
        room12.delete();
    }

    @Test
    void testIsRoomAvailable() {
        Room room1 = new Room(1, 100.0);
        Room room2 = new Room(2, 100.0);

        Customer customer1 = new Customer("firstname1", "lastname1");
        Customer customer2 = new Customer("firstname2", "lastname2");

        LocalDateTime startdate = LocalDateTime.of(2021, Month.MAY, 8, 0, 0);
        LocalDateTime enddate = LocalDateTime.of(2022, Month.MAY, 9, 0, 0);
        
        assertEquals(true, room1.isAvailable(startdate, enddate));
        assertEquals(true, room2.isAvailable(startdate, enddate));

        try {
            new Booking(startdate, enddate, room1, customer1, false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertEquals(false, room1.isAvailable(startdate, enddate));
        assertEquals(true, room2.isAvailable(startdate, enddate));

        try {
            new Booking(startdate, enddate, room2, customer2, false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(false, room2.isAvailable(startdate, enddate));

        room1.delete();
        room2.delete();
    }
}
