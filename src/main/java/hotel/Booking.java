package hotel;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Booking {
    private static final ArrayList<Booking> BOOKINGS = new ArrayList<Booking>();
    private LocalDateTime startdate = LocalDateTime.MIN;
    private LocalDateTime enddate = LocalDateTime.MIN;
    private Room room;
    private Customer customer;
    private Double price;
    private LocalDateTime bookingDate = LocalDateTime.MIN;
    private Boolean isBusiness = false;
    private Integer guestsAmount = 1;
    private LocalDateTime checkedIn = LocalDateTime.MIN;
    private LocalDateTime checkedOut = LocalDateTime.MIN;

    public Booking(LocalDateTime startdate, LocalDateTime enddate, Room room, Customer customer, boolean isBusiness) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.room = room;
        this.customer = customer;
        this.price = this.calculatePrice(startdate, enddate, isBusiness);
        this.bookingDate = LocalDateTime.now();
        this.isBusiness = isBusiness;

        BOOKINGS.add(this);
    }

    public static ArrayList<Booking> getBookings() {
        return BOOKINGS;
    }

    public Double calculatePrice(LocalDateTime startdate, LocalDateTime enddate, boolean isBusiness) {
        Double price = 0.0;

        for (LocalDateTime date = startdate; date.isBefore(enddate); date = date.plusDays(1)) {
            // default price is 120
            Double dayPrice = 120.0;

            // add 20 euro if booking is business related
            if (isBusiness) {
                dayPrice += 20;
            }

            // Add 20 percent discount on saterday and sunday
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                dayPrice = dayPrice * 0.8;
            }

            price += dayPrice;
        }

        return price;
    }

    public Double getPrice() {
        return this.price;
    }

    public LocalDateTime getStartDate() {
        return this.startdate;
    }

    public LocalDateTime getEndDate() {
        return this.enddate;
    }

    public LocalDateTime checkIn() {
        LocalDateTime currentTime = LocalDateTime.now();
        this.checkedIn = currentTime;

        return currentTime;
    }

    public LocalDateTime checkOut() {
        LocalDateTime currentTime = LocalDateTime.now();
        this.checkedOut = currentTime;

        return currentTime;
    }

    public static Integer getBookingsToday() {
        Integer bookingsCount = 0;

        for (Booking booking : BOOKINGS) {
            LocalDateTime now = LocalDateTime.now();

            if (now.isAfter(booking.startdate) && now.isBefore(booking.enddate)) {
                bookingsCount++;
            }
        }

        return bookingsCount;
    }

    public static void printBookingsToday() {
        Integer bookingsCount = getBookingsToday();

        System.out.println("Total bookings today: " + bookingsCount);
    }
}
