package hotel;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class BookingTest {
    @Test
    void calculatePrice() {
        
        Booking booking1 = new Booking(LocalDateTime.of(2021, Month.MAY, 8, 0, 0), LocalDateTime.of(2021, Month.MAY, 9, 0, 0), new Room(), new Customer(), false);


        assertEquals(96, booking1.getPrice());

    }
}
