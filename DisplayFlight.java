import java.util.List;

public interface DisplayFlight {

    void displayRegisteredUsersForAllFlight();

    void displayRegisteredUsersForASpecificFlight(String flightNum);

    void displayFlightsRegisteredByOneUser(String userID);

}