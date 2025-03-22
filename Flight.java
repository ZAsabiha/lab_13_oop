import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Flight extends FlightDistance {

    private final String flightSchedule;
    private final String flightNumber;
    private final String fromCity;
    private final String toCity;
    private final String gate;
    private double distanceInMiles;
    private double distanceInKm;
    private String flightTime;
    private int availableSeats;
    private final List<Customer> registeredCustomers;

    private static final List<Flight> flightList = new ArrayList<>();
    private static int nextFlightDay = 0;

    public Flight(String flightSchedule, String flightNumber, int availableSeats,
                  String[][] chosenDestinations, String[] distances, String gate) {
        this.flightSchedule = flightSchedule;
        this.flightNumber = flightNumber;
        this.availableSeats = availableSeats;
        this.fromCity = chosenDestinations[0][0];
        this.toCity = chosenDestinations[1][0];
        this.distanceInMiles = Double.parseDouble(distances[0]);
        this.distanceInKm = Double.parseDouble(distances[1]);
        this.flightTime = calculateFlightTime(distanceInMiles);
        this.registeredCustomers = new ArrayList<>();
        this.gate = gate.toUpperCase();
    }

    public static void generateFlightSchedule(int numOfFlights) {
        RandomGenerator generator = new RandomGenerator();
        for (int i = 0; i < numOfFlights; i++) {
            String[][] destinations = generator.randomDestinations();
            String[] distances = calculateDistance(
                Double.parseDouble(destinations[0][1]),
                Double.parseDouble(destinations[0][2]),
                Double.parseDouble(destinations[1][1]),
                Double.parseDouble(destinations[1][2])
            );
            String flightSchedule = createNewFlightTime();
            String flightNumber = generator.randomFlightNumbGen(2, 1).toUpperCase();
            int seats = generator.randomNumOfSeats();
            String gate = generator.randomFlightNumbGen(1, 30);

            flightList.add(new Flight(flightSchedule, flightNumber, seats, destinations, distances, gate));
        }
    }

    public void addCustomer(Customer customer) {
        this.registeredCustomers.add(customer);
    }

    public String calculateFlightTime(double distance) {
        int hours = (int) (distance / 450); // Assuming speed of 450 mph
        int minutes = (int) ((distance % 450) / 7.5);
        minutes = roundToNearestFive(minutes);

        return String.format("%02d:%02d", hours, minutes);
    }

    private int roundToNearestFive(int minutes) {
        return (minutes + 2) / 5 * 5;
    }

    public String fetchArrivalTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm a ");
        LocalDateTime departure = LocalDateTime.parse(flightSchedule, formatter);

        String[] flightTimeParts = flightTime.split(":");
        int hours = Integer.parseInt(flightTimeParts[0]);
        int minutes = Integer.parseInt(flightTimeParts[1]);

        LocalDateTime arrival = departure.plusHours(hours).plusMinutes(minutes);
        return arrival.format(DateTimeFormatter.ofPattern("EE, dd-MM-yyyy HH:mm a"));
    }

    public static String createNewFlightTime() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(++nextFlightDay).plusHours(nextFlightDay);
        dateTime = roundToNearestQuarter(dateTime);
        return dateTime.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm a"));
    }

    private static LocalDateTime roundToNearestQuarter(LocalDateTime dateTime) {
        int minutes = dateTime.getMinute();
        int roundedMinutes = (minutes + 7) / 15 * 15;
        return dateTime.withMinute(roundedMinutes).withSecond(0);
    }

    public static void displayFlightSchedule() {
        System.out.printf("%-5s%-30s%-12s%-18s%-20s%-20s%-15s%-10s%-8s%n",
                "Num", "Flight Schedule", "Flight No", "Seats Available", "From", "To", "Arrival Time", "Time", "Gate");
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < flightList.size(); i++) {
            Flight flight = flightList.get(i);
            System.out.printf("%-5d%-30s%-12s%-18d%-20s%-20s%-15s%-10s%-8s%n",
                    i + 1, flight.flightSchedule, flight.flightNumber,
                    flight.availableSeats, flight.fromCity, flight.toCity,
                    flight.fetchArrivalTime(), flight.flightTime, flight.gate);
        }
    }

    // Getters
    public String getFlightNumber() { return flightNumber; }
    public int getAvailableSeats() { return availableSeats; }
    public String getFlightSchedule() { return flightSchedule; }
    public String getFromCity() { return fromCity; }
    public String getToCity() { return toCity; }
}
