package hotel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Room {
    // TODO: make array singleton
    private static final ArrayList<Room> ROOMS = new ArrayList<Room>();

    private Integer roomNumber;
    private Double price;

    public Room(Integer roomNumber, Double price) {
        if (Room.getRoom(roomNumber) != null) {
            // TODO: handle error
            return;
        }
        
        this.roomNumber = roomNumber;
        this.price = price;

        ROOMS.add(this);
    }
    
    public Integer getRoomNumber() {
        return this.roomNumber;
    }

    public Double getPrice() {
        return this.price;
    }

    // check if room is currently available
    public Boolean isAvailable(LocalDateTime startdate, LocalDateTime enddate) {
        Boolean available = true;

        for (Booking booking : Booking.getBookings()) {
            if (booking.getRoom() == this &&
                (startdate.isBefore(booking.getStartDate().plusMinutes(1)) && enddate.plusMinutes(1).isAfter(booking.getStartDate()) ||
                (startdate.plusMinutes(1).isAfter(booking.getStartDate()) && enddate.isBefore(booking.getEndDate().plusMinutes(1)) ))
            ) {
                available = false;
            }
        }

        return available;
    }

    public void delete () {
        ROOMS.remove(this);
    }

    public static Room getRoom(Integer roomNumber) {
        for (Room room : ROOMS) {
            if (room.roomNumber == roomNumber) {
                return room;
            }
        }

        return null;
    }

    public static ArrayList<Room> getAllRooms() {
        return ROOMS;
    }

    public static void printAvailableRooms(LocalDateTime startdate, LocalDateTime enddate) {
        System.out.println("Available roomnumbers:");

        for (Room room: ROOMS) {
            if (room.isAvailable(startdate, enddate)) {
                System.out.println(room.getRoomNumber() + ".");
            }
        }
    }

    public static void checkAvailability() {
        Main.clearConsole();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Roomnumber:");
        Integer roomNumber = scanner.nextInt();

        Room room = Room.getRoom(roomNumber);

        Boolean available = room.isAvailable(LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        System.out.println("Room " + room.getRoomNumber() + " " + (available ? "IS": "is NOT") + " available");
        
        System.out.println("Press enter to continue:");
        scanner.nextLine();
        scanner.nextLine();

        Main.printMainMenu();
    }
}
