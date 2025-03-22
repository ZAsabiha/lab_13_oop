import java.util.Map;

public class RolesAndPermissions {

    private static final Map<String, String> adminCredentials = Map.of(
        "admin1", "password1",
        "admin2", "password2",
        "admin3", "password3"
    );

   
    public int isPrivilegedUserOrNot(String username, String password) {
        return adminCredentials.containsKey(username) && adminCredentials.get(username).equals(password)
                ? 1 : -1;
    }

    public String isPassengerRegistered(String email, String password) {
        Customer customer = Customer.getCustomerByEmail(email);
        if (customer != null && customer.getPassword().equals(password)) {
            return "1-" + customer.getUserID();
        }
        return "0";
    }
}

