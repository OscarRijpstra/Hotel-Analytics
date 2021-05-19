package hotel;

import java.time.LocalDateTime;

public class Room {
    private Integer roomNumber;

    public Room(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public static Boolean isRoomAvailable(Room room) {
        Boolean available = true;

        for (Booking booking : Booking.getBookings()) {
            LocalDateTime now = LocalDateTime.now();

            if (now.isAfter(booking.getStartDate()) && now.isBefore(booking.getEndDate())) {
                available = false;
            }
        }

        return available;
    }
}
