import java.util.InputMismatchException;
import java.util.Scanner;


public class PlaneManagement {
    // here used compile-time constant variable to define seat prices.
    //https://javarevisited.blogspot.com/2017/01/how-public-static-final-variable-works.html#axzz8VHDWqUf7
    private static final int yellow_price = 200;
    private static final int blue_price = 150;
    private static final int green_price = 180;

    // used two-dimensional array to define the seating arrangement
    private static final char[][] seats = {
            {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
            {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
            {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'},
            {'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'}
    };
    // arrays of objects to store all tickets sold during the session
    private static  Ticket[] ticketSold = new Ticket[0];

    // Method buy_seat
    public static void buy_seat() {
        System.out.println("You have chosen the option to buy a seat");
        //create scanner class object
        Scanner scanner = new Scanner(System.in);

        //declares a variable which will store the row letter entered by the user
        char rowChar;
        do {
            try {
                System.out.print("Enter the row letter(A-D): ");
                //reads the user input as a String, converts it to uppercase,and then extracts the first character (the row letter) and assigns it to rowChar
                rowChar = scanner.next().toUpperCase().charAt(0);

                if (rowChar < 'A' || rowChar > 'D') {
                    throw new InputMismatchException("Invalid row. Please enter a valid row (A-D).");
                }
            } catch (InputMismatchException e) {    //allows us to handle situations where the user enters an invalid input type
                System.out.println(e.getMessage()); //https://www.javatpoint.com/inputmismatchexception-in-java#:~:text=the%20expected%20type.-,The%20java.,input%20is%20out%20of%20range.
                scanner.nextLine();
                rowChar = '\0'; // Set rowChar to a default value
            }
        } while (rowChar < 'A');
        //Continues the loop if the entered row letter is less than 'A', ensuring that the loop repeats until a valid row letter is entered.


        //declares a variable which will store the seat number entered by the user
        int column;
        do {
            try {
                System.out.print("Enter the seat number (1-14): ");
                column = scanner.nextInt();

                if (column < 1 || column > 14) {
                    throw new InputMismatchException("Invalid column. Please enter a valid column (1-14).");
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                scanner.nextLine();
                column = -1; // Set column to a default value
            }
        } while (column < 1);
        //Continues the loop allow condition ,ensuring that the loop repeats until a valid seat number is provided

        int row = rowChar - 'A';
        // characters are represented by their ASCII values so here Convert row character to index
        try {
            if (seats[row][column - 1] == 'X') {        // normally array starts from 0, So I subtract 1 from the column variable to get correct index.
                System.out.println("Seat " + rowChar + column + " is already sold.");
                return; //exits the method
            }
        } catch (ArrayIndexOutOfBoundsException e) {     // used ArrayIndexOutOfBoundsException to access any item of an array at an index which is not present in the array.
            System.out.println("Invalid seat selection.");   //https://www.javatpoint.com/arrayindexoutofboundsexception-in-java
            return;
        }
        //checks if the seat at the specified row and column is available
        if ('O' == seats[row][column - 1]) {
            System.out.println("Great!.. Seat " + rowChar + column + " is available.");
            scanner.nextLine();     //the newline character left in the input buffer to prevent it from interfering with subsequent input operations.
            System.out.print("Enter your first name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your surname: ");
            String surname = scanner.nextLine();
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();

            // Create person object from a Person class
            Person person = new Person(name, surname, email);

            //Calculates the price of the ticket based on the row and column indices using the clone method.
            int price = clone(row, column - 1);

            // Create ticket object from a Ticket class
            Ticket ticket = new Ticket(row, column, price, person);
            addTicket (ticket);  //Adds the newly created ticket to the array of sold tickets
            ticket.save();  //call the save method of the ticket object to save ticket information
            seats[row][column - 1] = 'X';
            System.out.println("Successfully Booked a seat " + rowChar + column);
        } else {
            System.out.println("Seat " + rowChar + column + " is already sold.");
        }
    }

    //method named addTicket, which takes a ticket object as a parameter.
    // This method is responsible for adding a new ticket to the array of sold tickets.
    private static void addTicket(Ticket ticket) {

        //class  is Ticket and object of array is newTicketSold
        Ticket[] newTicketsSold = new Ticket[ticketSold.length + 1];    // Resize the ticketSold array to accommodate the new ticket

        //use arraycopy method to copies all elements from the current ticketSold array to the new newTicketsSold array.
        //https://www.geeksforgeeks.org/array-copy-in-java/
        System.arraycopy(ticketSold, 0, newTicketsSold, 0, ticketSold.length);

        //adds the new ticket object to the last index of the newTicketsSold array, effectively adding it to the end of the list of sold tickets.
        newTicketsSold[ticketSold.length] = ticket;

        //updates the ticketSold reference to point to the newly created array newTicketsSold
        ticketSold = newTicketsSold;
    }


    // clone method calculates and returns the price of a ticket based on its row and column position.
    private static int clone(int row, int column) {

        //calculates the character representing the row letter of the seat based on the provided row index.
        char seatChar = (char) ('A' + row);
        if (column >= 0 && column < 5) { // Yellow seats
            return yellow_price;
        } else if (column >= 5 && column < 9) { // Blue seats
            return blue_price;
        } else if ((column >= 9 && column < 14) && (seatChar == 'A' || seatChar == 'B')) { // Green seats for A and B rows
            return green_price;
        } else if ((column >= 9 && column < 13) && (seatChar == 'C' || seatChar == 'D')) { // Green seats for C and D rows
            return green_price;
        }
        return 0; // Return 0 for invalid seat positions
    }
    // Method cancel_seat
    public static void cancel_seat() {
        System.out.println("You have chosen the option to cancel a seat");
        Scanner scanner = new Scanner(System.in);

        char rowChar;
        do{
           try{
               System.out.print("Enter the row letter(A-D): ");
                rowChar = scanner.next().toUpperCase().charAt(0);

               if (rowChar < 'A' || rowChar > 'D') {
                   throw new InputMismatchException("Invalid row. Please enter a valid row (A-D).");
               }
           } catch (InputMismatchException e) {
               System.out.println(e.getMessage());
               scanner.nextLine(); // Clear input buffer
               rowChar = '\0'; // Set rowChar to a default value
           }
        } while (rowChar < 'A');

        int column;
        do{
            try{
                System.out.print("Enter the seat number (1-14): ");
                column = scanner.nextInt();

                if (column < 1 || column > 14) {
                    throw new InputMismatchException("Invalid column. Please enter a valid column (1-14).");
                }
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                scanner.nextLine(); // Clear input buffer
                column = -1; // Set column to a default value
            }
        } while (column < 1);

        int row = rowChar - 'A'; // Convert row character to index

        if (column <= seats[row].length) {
            if (!is_seat_available(row, column)) {

                seats[row][column - 1] = 'O'; // Mark seat as available
                Scanner m = new Scanner(System.in);
                System.out.println("Do you want to cancel your ticket? (Yes/No) ");
                String answer = m.next().toLowerCase();
                if (answer.equals("yes")) {
                    removeTicket(0);
                    System.out.println("Seat " + rowChar + column + " canceled successfully.");

                }
            } else {
                System.out.println("Seat " + rowChar + column + " is not booked to cancelled.You can book this seat.");
            }
        } else {
            System.out.println("Invalid seat selection.");
        }
    }
    private static void removeTicket(int index) {
        // Create a new array to hold the updated tickets
        Ticket[] updatedTickets = new Ticket[ticketSold.length - 1];

        // Copy all tickets before the removed one
        System.arraycopy(ticketSold, 0, updatedTickets, 0, index);

        // Copy all tickets after the removed one
        System.arraycopy(ticketSold, index + 1, updatedTickets, index, ticketSold.length - index - 1);

        // Update the reference to point to the new array
        ticketSold = updatedTickets;
    }
    // Method to find first available seat
    public static void find_first_available() {
        System.out.println("You have chosen the option to find first available seat");

        //a boolean variable 'found' to false, which will be used to track whether an available seat has been found
        boolean found = false;
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == 'O') {
                    System.out.println("First available seat found at row " + (char) ('A' + i) + ", column " + (j + 1));
                    found = true;
                    break;   //breaks out of the inner loop since the first available seat has been found
                }
            }
            if (found) break; //checks if an available seat has been found. If so, it breaks out of the outer loop as well.
        }
        if (!found) {      //checks if no available seat has been found after looping through all seats.
            System.out.println("No available seats found.");
        }
    }
    // Method to show seating plan
    public static void show_seating_plan() {
        System.out.println("You have chosen the option to show seating plan");
        System.out.println("\n Seating Plan:");
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] == 'X') {
                    System.out.print("X ");
                } else {
                    System.out.print("O ");
                }
            }
            System.out.println();
        }
    }
    // Method to print tickets information and total sales
    public static void print_tickets_info() {
        System.out.println("You have chosen the option to print ticket information and total sales");
        int totalSales = 0;          // Print information of each ticket and calculate total sales

        //starts an enhanced for-loop over each ticket object in the ticketSold array.
        //https://favtutor.com/blogs/java-array-of-objects
        for (Ticket ticket : ticketSold) {
            System.out.println("Tickets Information: \n");
            System.out.println("Seat Number: " + (char) ('A' + ticket.getRow()) + ticket.getSeat());
            System.out.println("Passenger's Full Name: " + ticket.getPerson().getName() + " " + ticket.getPerson().getSurname());
            System.out.println("Email: "+ ticket.getPerson().getEmail());
            System.out.println("Price: £" + ticket.getPrice());
            totalSales += ticket.getPrice();
            System.out.println();
        }
        // Print total sales
        System.out.println("Total Amount: £" + totalSales);
    }



    // Method to search for a ticket based on row and seat number
    public static void search_ticket() {
        System.out.println("You have chosen the option to search for a ticket");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the row letter(A-D): ");
        char rowChar = scanner.next().toUpperCase().charAt(0);
        if (rowChar < 'A' || rowChar > 'D') {
            System.out.println("Invalid row. Please enter a valid row (A-D).");
            return;
        }
        System.out.print("Enter the seat number (1-14): ");
        int column = scanner.nextInt();

        int row = rowChar - 'A'; // Convert row character to index

        if (column >= 1 && column <= seats[row].length) {
            if (!is_seat_available(row, column)) {
                // Seat is sold
                for (Ticket ticket : ticketSold) {
                    if (ticket.getRow() == row && ticket.getSeat() == column) {
                        System.out.println("Ticket Information:");
                        System.out.println("Seat: " + (char) ('A' + ticket.getRow()) + ticket.getSeat());
                        System.out.println("Passenger's Full Name: " + ticket.getPerson().getName() + " " + ticket.getPerson().getSurname());
                        System.out.println("Price: £" + ticket.getPrice());
                        return;
                    }
                }
            } else {
                // Seat is available
                System.out.println("This seat is available.");
            }
        } else {
            System.out.println("Invalid seat selection.");
        }
    }
    //a method is_seat_available that checks whether a seat at a given row and column is available
    //So, the method returns true if the seat at the specified row and column is available ('O'), and false otherwise
    public static boolean is_seat_available(int row, int column) {
        return seats[row][column - 1] == 'O';
    }
    //if it's not, the initializeSeats method initializes all seats as available by setting each seat in the seats array to 'O'.
    public static void initializeSeats() {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                seats[i][j] = 'O';
            }
        }
    }
    // Method to display menu options
    public static void displayMenu() {
        System.out.println("""
                *************************************************
                *                  MENU OPTIONS                 *
                *************************************************
                                    
                1) Buy a seat
                2) Cancel a seat
                3) Find first available seat
                4) Show seating plan
                5) Print ticket
                6) Search ticket
                0) Quit
                                    
                *************************************************
                """);
    }
    // main() method start
    public static void main(String[] args) {
        //create scanner class object
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println();
        //create welcome message
        System.out.println(" 'Welcome to the Plane Management application' ");
        System.out.println();

        // Initialize all seats as available
        initializeSeats();

        int option;
        do{
            System.out.println();
            displayMenu(); // Calls the displayMenu method to show the menu options to the user.

            System.out.println();
            System.out.print("Please select an option: ");
            try {
                option = scanner.nextInt(); // Attempt to read an integer option
            } catch (InputMismatchException e) {
                // If a non-integer value is entered, catch the exception
                option = -1; // Set option to an invalid value
                scanner.next(); // Consume the invalid input to avoid an infinite loop
            }

            switch (option) {
                case 1:
                    buy_seat();
                    break;
                case 2:
                    cancel_seat();
                    break;
                case 3:
                    find_first_available();

                    break;
                case 4:
                    show_seating_plan();
                    break;
                case 5:
                    print_tickets_info();
                    break;
                case 6:
                    search_ticket();
                    break;
                case 0:
                    System.out.println("End");
                    break;   //Exits the switch statement.
                default:     //Executes if none of the above cases match.
                    System.out.println("Invalid option");
            }
        } while (option != 0);
        /*
        Continues the loop if the user's choice is not 0, prompting the user for another input.
        If the user enters 0, the loop ends, and the program terminates.
        */
    }

}