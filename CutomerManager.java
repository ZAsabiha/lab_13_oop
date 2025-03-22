// CustomerManager.java
import java.util.*;

public class CustomerManager {
    private static final List<Customer> customerCollection = new ArrayList<>();

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

    public void displayCustomersData() {
        CustomerUtils.displayHeader();
        for (Customer c : customerCollection) {
            System.out.println(CustomerUtils.formatCustomerData(c));
        }
    }
}
