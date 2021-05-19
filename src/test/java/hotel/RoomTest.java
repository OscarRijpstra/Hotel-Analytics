package hotel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class RoomTest {
    @Test
    void testIsRoomAvailable() {
        Room room1 = new Room(1);
        Customer customer1 = new Customer("firstname", "lastname");

        assertEquals(true, Room.isRoomAvailable(room1));

        new Booking(LocalDateTime.of(2021, Month.MAY, 8, 0, 0), LocalDateTime.of(2022, Month.MAY, 9, 0, 0), room1, customer1, false);

        assertEquals(false, Room.isRoomAvailable(room1));
    }
}
