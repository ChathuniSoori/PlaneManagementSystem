import java.io.FileWriter;
import java.io.IOException;
public class Ticket {
    private int row;
    private int seat;
    private int prices;
    private Person person;

    public Ticket(int row, int seat, int prices, Person person) {
        this.row = row;
        this.seat = seat;
        this.prices = prices;
        this.person = person;
    }
    // Getter methods

    // Method to save the ticket information to a file
    public void save() {
        String filename = (char) ('A' + row) + Integer.toString(seat) + ".txt";

        //https://www.geeksforgeeks.org/filewriter-class-in-java/
        try (FileWriter writer = new FileWriter(filename)) {


            writer.write("Ticket Information:\n");
            writer.write("Seat: " + (char) ('A' + row) + seat + "\n");
            writer.write("Passenger's Full Name: " + person.getName() + " " + person.getSurname() + "\n");
            writer.write("Email: " +person.getEmail()+"\n");
            writer.write("Price: £" + prices + "\n");
            System.out.println("Ticket saved to file: " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the ticket to file: " + filename);
            e.printStackTrace();
            //e.printStackTrace() method is used to print the stack trace of an exception to the standard error stream
            //https://www.educative.io/answers/what-is-the-printstacktrace-method-in-java
        }
    }


    // Getters and setters
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getPrice() {
        return prices;
    }

    public void setPrice(int price) {
        this.prices = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    // Method to print ticket information
    public void printTicketInfo() {
        System.out.println("Ticket Information:");
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: £" + prices);
        System.out.println("Person Information:");
        person.printInfo();
    }
}

