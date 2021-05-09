package hotel;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Booking {
    private LocalDateTime startdate = LocalDateTime.MIN;
    private LocalDateTime enddate = LocalDateTime.MIN;
    private Room room;
    private Customer customer;
    private Double price;
    private LocalDateTime bookingDate = LocalDateTime.MIN;
    private boolean isBusiness = false;

    public Booking(LocalDateTime startdate, LocalDateTime enddate, Room room, Customer customer, boolean isBusiness) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.room = room;
        this.customer = customer;
        this.price = this.calculatePrice(startdate, enddate, isBusiness);
        this.bookingDate = LocalDateTime.now();
        this.isBusiness = isBusiness;
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
}
