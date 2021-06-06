package hotel;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class BookingTest {
    @Test
    void testBooking() {
        LocalDateTime startDate1 = LocalDateTime.of(2021, Month.MAY, 4, 0, 0);
        LocalDateTime endDate1 = LocalDateTime.of(2021, Month.MAY, 5, 0, 0);

        Integer bookingCount1 = Booking.getAll().size();

        Room room1 = new Room(1, 100.0);

        assertEquals(0, bookingCount1);

        try {
            new Booking(startDate1, endDate1, Room.getRoom(2), Customer.getCustomer(2), false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Integer bookingCount2 = Booking.getAll().size();

        assertEquals(0, bookingCount2);

        try {
            new Booking(startDate1, endDate1, Room.getRoom(1), Customer.getCustomer(1), false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Integer bookingCount3 = Booking.getAll().size();

        assertEquals(1, bookingCount3);

        room1.delete();
    }

    @Test
    void testCalculatePrice() {
        Double roomPrice1 = 100.0;
        Double roomPrice2 = 120.0;

        // new room with 100 per night
        Room room1 = new Room(1, roomPrice1);

        // new room with 120 per night
        Room room2 = new Room(2, roomPrice2);

        Customer customer1 = new Customer("firstname", "lastname");

        LocalDateTime startDate1 = LocalDateTime.of(2021, Month.MAY, 4, 0, 0);
        LocalDateTime endDate1 = LocalDateTime.of(2021, Month.MAY, 5, 0, 0);
        LocalDateTime endDate2 = LocalDateTime.of(2021, Month.MAY, 6, 0, 0);

        LocalDateTime startDate3 = LocalDateTime.of(2021, Month.MAY, 8, 0, 0);
        LocalDateTime endDate3 = LocalDateTime.of(2021, Month.MAY, 9, 0, 0);

        // booking not in the weekend, on room 1 and not business
        Booking booking1 = null;
        try {
            booking1 = new Booking(startDate1, endDate1, room1, customer1, false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // booking not in the weekend, on room 1 and business
        Booking booking2 = null;
        try {
            booking2 = new Booking(startDate1, endDate1, room1, customer1, true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // booking not in the weekend, on room 2 and not business
        Booking booking3 = null;
        try {
            booking3 = new Booking(startDate1, endDate1, room2, customer1, false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // booking not in the weekend, on room 2 and business
        Booking booking4 = null;
        try {
            booking4 = new Booking(startDate1, endDate1, room2, customer1, true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // booking not the weekend, on room 2 and not business
        Booking booking5 = null;
        try {
            booking5 = new Booking(startDate1, endDate2, room1, customer1, false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // booking not the weekend, on room 2 and business
        Booking booking6 = null;
        try {
            booking6 = new Booking(startDate1, endDate2, room1, customer1, true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // booking in the weekend, on room 2 and not business
        Booking booking7 = null;
        try {
            booking7 = new Booking(startDate3, endDate3, room1, customer1, false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // booking in the weekend, on room 2 and business
        Booking booking8 = null;
        try {
            booking8 = new Booking(startDate3, endDate3, room1, customer1, true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // roomPrice1 * 1 for private booking
        assertEquals(roomPrice1, booking1.getPrice());

        // roomPrice1 * 1 for business booking
        assertEquals(roomPrice1 + 20, booking2.getPrice());

        // roomPrice2 * 1 for business booking
        assertEquals(roomPrice2, booking3.getPrice());

        // roomPrice2 * 1 for business booking
        assertEquals(roomPrice2 + 20, booking4.getPrice());

        // roomPrice1 * 2 for private booking
        assertEquals(roomPrice1 * 2, booking5.getPrice());

        // roomPrice1 * 2 for business booking
        assertEquals((roomPrice1 + 20) * 2, booking6.getPrice());

        // roomPrice1 * 1 for private booking in weekend
        assertEquals(roomPrice1 * 0.8, booking7.getPrice());

        // roomPrice1 * 1 for business booking in weekend
        assertEquals((roomPrice1 + 20) * 0.8, booking8.getPrice());

        room1.delete();
        room2.delete();
    }

    @Test
    void testgetBookingsToday() {
        Room room1 = new Room(1, 100.0);
        Customer customer1 = new Customer("firstname", "lastname");

        // is between today
        try {
            new Booking(LocalDateTime.of(2021, Month.MAY, 8, 0, 0), LocalDateTime.of(2022, Month.MAY, 9, 0, 0), room1,
                    customer1, false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            new Booking(LocalDateTime.of(2021, Month.MAY, 8, 0, 0), LocalDateTime.of(2022, Month.MAY, 9, 0, 0), room1,
                    customer1, false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // is not between today
        try {
            new Booking(LocalDateTime.of(2021, Month.MAY, 8, 0, 0), LocalDateTime.of(2021, Month.MAY, 10, 0, 0), room1,
                    customer1, false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        assertEquals(2, Booking.getBookingsToday());

        room1.delete();
    }
}
