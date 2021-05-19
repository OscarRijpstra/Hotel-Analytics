package hotel;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class BookingTest {
    @Test
    void testCalculatePrice() {
        
        Booking booking1 = new Booking(LocalDateTime.of(2021, Month.MAY, 8, 0, 0), LocalDateTime.of(2021, Month.MAY, 9, 0, 0), new Room(1), new Customer("firstname", "lastname"), false);


        assertEquals(96, booking1.getPrice());
    }


    @Test
    void testgetBookingsToday() {
        Room room1 = new Room(1);
        Customer customer1 = new Customer("firstname", "lastname");

        // is between today
        new Booking(LocalDateTime.of(2021, Month.MAY, 8, 0, 0), LocalDateTime.of(2022, Month.MAY, 9, 0, 0), room1, customer1, false);
        new Booking(LocalDateTime.of(2021, Month.MAY, 8, 0, 0), LocalDateTime.of(2022, Month.MAY, 9, 0, 0), room1, customer1, false);

        // is not between today
        new Booking(LocalDateTime.of(2021, Month.MAY, 8, 0, 0), LocalDateTime.of(2021, Month.MAY, 10, 0, 0), room1, customer1, false);

        assertEquals(2, Booking.getBookingsToday());
    }
}
