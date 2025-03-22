import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FlightReservation implements DisplayClass {

    private Flight flight = new Flight();
    private int flightIndexInFlightList;

    public void bookFlight(String flightNo, int numOfTickets, String userID) {
        for (Flight f1 : flight.getFlightList()) {
            if (flightNo.equalsIgnoreCase(f1.getFlightNumber())) {
                handleBooking(f1, numOfTickets, userID);
                return;
            }
        }
        System.out.println("Invalid Flight Number...! No flight with the ID \"" + flightNo + "\" was found...");
    }

    private void handleBooking(Flight flight, int numOfTickets, String userID) {
        for (Customer customer : Customer.customerCollection) {
            if (userID.equals(customer.getUserID())) {
                flight.setNoOfSeatsInTheFlight(flight.getNoOfSeats() - numOfTickets);

                if (!flight.isCustomerAlreadyAdded(flight.getListOfRegisteredCustomersInAFlight(), customer)) {
                    flight.addNewCustomerToFlight(customer);
                }

                if (isFlightAlreadyAddedToCustomerList(customer.getFlightsRegisteredByUser(), flight)) {
                    addNumberOfTicketsToAlreadyBookedFlight(customer, numOfTickets);
                    customer.addExistingFlightToCustomerList(flightIndexInFlightList, numOfTickets);
                } else {
                    customer.addNewFlightToCustomerList(flight);
                    addNumberOfTicketsForNewFlight(customer, numOfTickets);
                }

                System.out.printf("\n %50s You've booked %d tickets for Flight \"%5s\"...", "", numOfTickets, flight.getFlightNumber().toUpperCase());
                return;
            }
        }
    }

    public void cancelFlight(String userID) {
        Scanner read = new Scanner(System.in);
        for (Customer customer : Customer.customerCollection) {
            if (userID.equals(customer.getUserID()) && !customer.getFlightsRegisteredByUser().isEmpty()) {
                processFlightCancellation(read, customer);
                return;
            }
        }
        System.out.println("No registered flights found for user ID: " + userID);
    }

    private void processFlightCancellation(Scanner read, Customer customer) {
        displayFlightsRegisteredByOneUser(customer.getUserID());
        System.out.print("Enter the Flight Number to cancel: ");
        String flightNum = read.nextLine();
        System.out.print("Enter the number of tickets to cancel: ");
        int numOfTickets = read.nextInt();

        Iterator<Flight> flightIterator = customer.getFlightsRegisteredByUser().iterator();
        int index = 0;

        while (flightIterator.hasNext()) {
            Flight flight = flightIterator.next();
            if (flightNum.equalsIgnoreCase(flight.getFlightNumber())) {
                int numOfTicketsForFlight = customer.getNumOfTicketsBookedByUser().get(index);
                numOfTickets = validateTicketsInput(read, numOfTickets, numOfTicketsForFlight);

                if (numOfTickets == numOfTicketsForFlight) {
                    flight.setNoOfSeatsInTheFlight(flight.getNoOfSeats() + numOfTicketsForFlight);
                    customer.getNumOfTicketsBookedByUser().remove(index);
                    flightIterator.remove();
                } else {
                    flight.setNoOfSeatsInTheFlight(flight.getNoOfSeats() + numOfTickets);
                    customer.getNumOfTicketsBookedByUser().set(index, numOfTicketsForFlight - numOfTickets);
                }
                return;
            }
            index++;
        }
        System.out.println("ERROR!!! Couldn't find Flight with ID " + flightNum.toUpperCase());
    }

    private int validateTicketsInput(Scanner read, int numOfTickets, int maxTickets) {
        while (numOfTickets > maxTickets) {
            System.out.print("ERROR!!! Number of tickets cannot exceed " + maxTickets + ". Please enter again: ");
            numOfTickets = read.nextInt();
        }
        return numOfTickets;
    }

    private void addNumberOfTicketsToAlreadyBookedFlight(Customer customer, int numOfTickets) {
        int updatedTickets = customer.getNumOfTicketsBookedByUser().get(flightIndexInFlightList) + numOfTickets;
        customer.getNumOfTicketsBookedByUser().set(flightIndexInFlightList, updatedTickets);
    }

    private void addNumberOfTicketsForNewFlight(Customer customer, int numOfTickets) {
        customer.getNumOfTicketsBookedByUser().add(numOfTickets);
    }

    private boolean isFlightAlreadyAddedToCustomerList(List<Flight> flightList, Flight flight) {
        for (Flight f : flightList) {
            if (f.getFlightNumber().equalsIgnoreCase(flight.getFlightNumber())) {
                flightIndexInFlightList = flightList.indexOf(f);
                return true;
            }
        }
        return false;
    }

    @Override
    public void displayFlightsRegisteredByOneUser(String userID) {
        for (Customer customer : Customer.customerCollection) {
            if (userID.equals(customer.getUserID())) {
                customer.getFlightsRegisteredByUser().forEach(flight ->
                        System.out.println(toString(1, flight, customer)));
            }
        }
    }

    @Override
    public void displayRegisteredUsersForAllFlight() {
        for (Flight flight : flight.getFlightList()) {
            List<Customer> c = flight.getListOfRegisteredCustomersInAFlight();
            if (!c.isEmpty()) {
                displayHeaderForUsers(flight, c);
            }
        }
    }

    @Override
    public void displayRegisteredUsersForASpecificFlight(String flightNum) {
        for (Flight flight : flight.getFlightList()) {
            if (flight.getFlightNumber().equalsIgnoreCase(flightNum)) {
                displayHeaderForUsers(flight, flight.getListOfRegisteredCustomersInAFlight());
                return;
            }
        }
    }
}
