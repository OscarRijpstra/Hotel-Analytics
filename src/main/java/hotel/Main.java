package hotel;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LocalDateTime startdate = LocalDateTime.of(2021, Month.JUNE, 6, 0, 0);  
        LocalDateTime enddate = LocalDateTime.of(2021, Month.JUNE, 7, 0, 0);

        Room room1 = new Room(1, 100.0);
        Room room2 = new Room(2, 100.0);
        new Room(3, 100.0);
        
        Customer customer1 = new Customer("Oscar", "Rijpstra");

        try {
            Booking booking1 = new Booking(startdate, enddate, room1, customer1, false);
            booking1.checkIn();

            new Booking(startdate, enddate, room2, customer1, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        printMainMenu();
    }

    public static void printMainMenu() {
        Scanner scanner = new Scanner(System.in);

        clearConsole();

        String[] menuItemsTexts = { "Book a room", "List bookings today", "Check room availability", "Exit" };

        for (int i = 0; i < menuItemsTexts.length; i++) {
            System.out.println((i + 1) + ". " + menuItemsTexts[i]);
        }

        System.out.print("Your choice:");

        try {
            int choice = scanner.nextInt();

            executeMenuChoice(choice);
        } catch (Exception e) {
            System.out.println(e);
        }

        scanner.close();
    }

    public static void executeMenuChoice(int choice) {

        switch (choice) {
            case 1:
                Booking.createBooking(); 
                break;

            case 2:
                Booking.printBookingsToday();
                break;

            case 3:
                Room.checkAvailability();
                break;

            case 4:
                break;

            default:
                System.out.println("Option unknown");
                break;
        }
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
