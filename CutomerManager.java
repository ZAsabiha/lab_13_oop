// CustomerManager.java
import java.util.*;

public class CustomerManager {
    public List<Flight> flightsRegisteredByUser;
    public List<Integer> numOfTicketsBookedByUser;
    public static final List<Customer> customerCollection = User.getCustomersCollection();

    public void addNewCustomer(String name, String email, String password, String phone, String address, int age) {
        RandomGenerator random = new RandomGenerator();
        random.randomIDGen();
        String userID = random.getRandomNumber();
        customerCollection.add(new Customer(name, email, password, phone, address, age, userID));
    }

    public void searchUser(String ID) {
        for (Customer c : customerCollection) {
            if (ID.equals(c.getUserID())) {
                System.out.println("Customer Found: " + c.getName());
                return;
            }
        }
        System.out.println("No customer found with ID: " + ID);
    }

    public void deleteUser(String ID) {
        customerCollection.removeIf(c -> ID.equals(c.getUserID()));
        System.out.println("Customer with ID " + ID + " deleted.");
    }


      public boolean isUniqueData(String emailID) {
        boolean isUnique = false;
        for (Customer c : customerCollection) {
            if (emailID.equals(c.getEmail())) {
                isUnique = true;
                break;
            }
        }
        return isUnique;
    }
      public void displayCustomersData(boolean showHeader) {
        displayHeader();
        Iterator<Customer> iterator = customerCollection.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            i++;
            Customer c = iterator.next();
            System.out.println(c.toString(i));
            System.out.printf(
                    "%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n",
                    "");
        }
    }

    void displayHeader() {
        System.out.println();
        System.out.printf(
                "%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n",
                "");
        System.out.printf(
                "%10s| SerialNum  |   UserID   | Passenger Names                  | Age     | EmailID\t\t       | Home Address\t\t\t     | Phone Number\t       |%n",
                "");
        System.out.printf(
                "%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n",
                "");
        System.out.println();

    }
        String randomIDDisplay(String randomID) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i <= randomID.length(); i++) {
            if (i == 3) {
                newString.append(" ").append(randomID.charAt(i));
            } else if (i < randomID.length()) {
                newString.append(randomID.charAt(i));
            }
        }
        return newString.toString();
    }
    void addNewFlightToCustomerList(Flight f) {
        this.flightsRegisteredByUser.add(f);
     
    }

    void addExistingFlightToCustomerList(int index, int numOfTickets) {
        int newNumOfTickets = numOfTicketsBookedByUser.get(index) + numOfTickets;
        this.numOfTicketsBookedByUser.set(index, newNumOfTickets);
    }

}
