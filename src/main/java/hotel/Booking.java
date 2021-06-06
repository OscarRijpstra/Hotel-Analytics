package hotel;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Booking {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    // TODO: make array singleton
    private static final ArrayList<Booking> BOOKINGS = new ArrayList<Booking>();
    private static Integer bookingsNumberIncrease = 1;

    private Integer bookingsNumber;
    private LocalDateTime startdate = LocalDateTime.MIN;
    private LocalDateTime enddate = LocalDateTime.MIN;
    private Room room;
    private Customer customer;
    private Double price;
    private LocalDateTime bookingDate = LocalDateTime.MIN;
    private Boolean isBusiness = false;
    private LocalDateTime checkedIn = LocalDateTime.MIN;
    private LocalDateTime checkedOut = LocalDateTime.MIN;

    public Booking(LocalDateTime startdate, LocalDateTime enddate, Room room, Customer customer, boolean isBusiness)
            throws Exception {
        if (room == null) {
            throw new Exception("Room not found");
        }

        if (customer == null) {
            throw new Exception("Customer not found");
        }

        // check if room is available
        if (!room.isAvailable(startdate, enddate)) {
            throw new Exception("Room is not available");
        }

        this.bookingsNumber = bookingsNumberIncrease;

        bookingsNumberIncrease++;

        this.startdate = startdate;
        this.enddate = enddate;
        this.room = room;
        this.customer = customer;
        this.price = this.calculatePrice(startdate, enddate, room, isBusiness);
        this.bookingDate = LocalDateTime.now();
        this.isBusiness = isBusiness;

        BOOKINGS.add(this);
    }

    public static ArrayList<Booking> getBookings() {
        return BOOKINGS;
    }

    public Double calculatePrice(LocalDateTime startdate, LocalDateTime enddate, Room room, boolean isBusiness) {
        Double price = 0.0;

        for (LocalDateTime date = startdate; date.isBefore(enddate); date = date.plusDays(1)) {

            // default price
            Double dayPrice = room.getPrice();

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

    public Integer getBookingNumber() {
        return this.bookingsNumber;
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

    public Room getRoom() {
        return this.room;
    }

    public Boolean getCheckedIn() {
        return !LocalDateTime.MIN.equals(this.checkedIn);
    }

    public Boolean getCheckedOut() {
        return !LocalDateTime.MIN.equals(this.checkedOut);
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

    public static ArrayList<Booking> getBookingsToday() {
        ArrayList<Booking> bookingsToday = new ArrayList<Booking>();

        for (Booking booking : BOOKINGS) {
            LocalDateTime now = LocalDateTime.now();

            if (now.isAfter(booking.getStartDate()) && now.isBefore(booking.getEndDate())) {
                bookingsToday.add(booking);
            }
        }

        return bookingsToday;
    }

    public static void printBookingsToday() {
        Scanner scanner = new Scanner(System.in);

        Main.clearConsole();

        ArrayList<Booking> bookingsToday = getBookingsToday();

        for (Booking booking : bookingsToday) {
            System.out.println(booking.getBookingNumber() + ". Room: " + booking.getRoom().getRoomNumber() + " checked in: " + (booking.getCheckedIn() ? "yes" : "no"));
        }

        System.out.println("Total bookings today: " + bookingsToday.size());

        System.out.println("Press enter to continue:");
        scanner.nextLine();

        Main.printMainMenu();
    }

    public static ArrayList<Booking> getAll() {
        return BOOKINGS;
    }

    public static void createBooking() {
        Scanner scanner = new Scanner(System.in);

        Main.clearConsole();

        LocalDateTime startdate = LocalDateTime.MIN;

        while (startdate.isEqual(LocalDateTime.MIN)) {
            try {
                System.out.println("Startdate: (dd-mm-yyyy)");
                String startdateString = scanner.nextLine() + " 16:00";

                LocalDateTime parsedStartdate = LocalDateTime.parse(startdateString, formatter);

                if (parsedStartdate.plusHours(6).isBefore(LocalDateTime.now())) {
                    throw new Exception("Startdate is in the past");
                }

                startdate = parsedStartdate;
            } catch (Exception error) {
                Main.clearConsole();

                System.out.println(error.getMessage());
            }
        }

        Main.clearConsole();

        LocalDateTime enddate = LocalDateTime.MIN;

        while (enddate.isEqual(LocalDateTime.MIN)) {
            try {
                System.out.println("Enddate: (dd-mm-yyyy)");
                String enddateString = scanner.nextLine() + " 12:00";

                LocalDateTime parsedEnddate = LocalDateTime.parse(enddateString, formatter);

                if (parsedEnddate.isBefore(startdate)) {
                    throw new Exception("Enddate is before startdate");
                }

                enddate = parsedEnddate;

            } catch (Exception error) {
                Main.clearConsole();

                System.out.println(error.getMessage());
            }
        }

        Main.clearConsole();

        Room.printAvailableRooms(startdate, enddate);

        Room room = null;

        while (room == null) {
            try {
                System.out.println("Roomnumber:");
                String input = scanner.nextLine();

                Integer roomNumber = Integer.parseInt(input);

                Room foundRoom = Room.getRoom(roomNumber);

                if (foundRoom == null) {
                    throw new Exception("Room not found");
                }

                if (!foundRoom.isAvailable(startdate, enddate)) {
                    throw new Exception("Room not available on selected date");
                }

                room = foundRoom;
            } catch (Exception error) {
                Main.clearConsole();

                System.out.println(error.getMessage());
            }
        }

        Main.clearConsole();

        Customer customer = null;

        while (customer == null) {
            try {
                System.out.println("Customernumber: (enter for new customer):");
                String input = scanner.nextLine();

                if (input.length() > 0) {
                    Integer customerNumber = Integer.parseInt(input);

                    Customer foundCustomer = Customer.getCustomer(customerNumber);

                    if (foundCustomer == null)
                        throw new Exception("Customer not found");
                } else {
                    Main.clearConsole();

                    System.out.println("New customer");

                    System.out.println("Firstname:");
                    String firstname = scanner.nextLine();

                    Main.clearConsole();

                    System.out.println("New customer");

                    System.out.println("Lastname:");
                    String lastname = scanner.nextLine();

                    customer = new Customer(firstname, lastname);
                }

            } catch (Exception error) {
                Main.clearConsole();

                System.out.println(error.getMessage());
            }
        }
        
        Main.clearConsole();

        System.out.println("Is business? y/n");

        Boolean isBusiness = scanner.nextLine() == "y";

        try {
            Booking booking = new Booking(startdate, enddate, room, customer, isBusiness);

            Main.clearConsole();

            System.out.println("Price: " + booking.getPrice() + " Room: " + booking.getRoom().getRoomNumber());

        } catch (Exception error) {
            System.out.println(error.getMessage());
        }

        System.out.println("Press enter to continue:");
        scanner.nextLine();

        Main.printMainMenu();
    }
}
