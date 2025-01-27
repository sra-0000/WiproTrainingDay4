package wiprotrainingday2;
import java.util.*;

//Abstract Vehicle class
abstract class Vehicle {
 private String registrationNumber;
 private String model;
 private double rentalRate; // Rate per day
 private boolean isRented;

 public Vehicle(String registrationNumber, String model, double rentalRate) {
     this.registrationNumber = registrationNumber;
     this.model = model;
     this.rentalRate = rentalRate;
     this.isRented = false;
 }

 public String getRegistrationNumber() {
     return registrationNumber;
 }

 public String getModel() {
     return model;
 }

 public double getRentalRate() {
     return rentalRate;
 }

 public boolean isRented() {
     return isRented;
 }

 public void rent() {
     isRented = true;
 }

 public void returnVehicle() {
     isRented = false;
 }

 @Override
 public String toString() {
     return this.getClass().getSimpleName() +
             "[Reg: " + registrationNumber + ", Model: " + model + ", Rate: $" + rentalRate + "/day, Rented: " + isRented + "]";
 }
}

//Car subclass
class Car extends Vehicle {
 public Car(String registrationNumber, String model, double rentalRate) {
     super(registrationNumber, model, rentalRate);
 }
}

//Bike subclass
class Bike extends Vehicle {
 public Bike(String registrationNumber, String model, double rentalRate) {
     super(registrationNumber, model, rentalRate);
 }
}

//Customer class
class Customer {
 private String name;
 private String contact;

 // Constructor
 public Customer(String name, String contact) {
     this.name = name;
     this.contact = contact;
 }

 public String getName() {
     return name;
 }

 public String getContact() {
     return contact;
 }

 @Override
 public String toString() {
     return "Customer[Name: " + name + ", Contact: " + contact + "]";
 }
}

//Rental class
class Rental {
 private Vehicle vehicle;
 private Customer customer;
 private int rentalDays;

 public Rental(Vehicle vehicle, Customer customer, int rentalDays) {
     this.vehicle = vehicle;
     this.customer = customer;
     this.rentalDays = rentalDays;
 }

 public double calculateRentalCost() {
     return rentalDays * vehicle.getRentalRate();
 }

 @Override
 public String toString() {
     return "Rental[Vehicle: " + vehicle.getModel() +
             ", Customer: " + customer.getName() +
             ", Days: " + rentalDays +
             ", Cost: $" + calculateRentalCost() + "]";
 }
}

//RentalSystem class
class RentalSystem {
 private Map<String, Vehicle> vehicles = new HashMap<>();
 private List<Rental> rentals = new ArrayList<>();

 // Add a vehicle to the system
 public void addVehicle(Vehicle vehicle) {
     vehicles.put(vehicle.getRegistrationNumber(), vehicle);
     System.out.println("Vehicle added successfully: " + vehicle);
 }

 // Rent a vehicle
 public void rentVehicle(String registrationNumber, Customer customer, int rentalDays) {
     Vehicle vehicle = vehicles.get(registrationNumber);

     if (vehicle == null) {
         System.out.println("Vehicle not found.");
         return;
     }

     if (vehicle.isRented()) {
         System.out.println("Vehicle is already rented.");
         return;
     }

     vehicle.rent();
     Rental rental = new Rental(vehicle, customer, rentalDays);
     rentals.add(rental);
     System.out.println("Vehicle rented successfully: " + rental);
 }

 // Return a vehicle
 public void returnVehicle(String registrationNumber) {
     Vehicle vehicle = vehicles.get(registrationNumber);

     if (vehicle == null) {
         System.out.println("Vehicle not found.");
         return;
     }

     if (!vehicle.isRented()) {
         System.out.println("Vehicle is not currently rented.");
         return;
     }

     vehicle.returnVehicle();
     System.out.println("Vehicle returned successfully: " + vehicle.getModel());
 }

 // Display all vehicles
 public void displayVehicles() {
     System.out.println("Available Vehicles:");
     for (Vehicle vehicle : vehicles.values()) {
         System.out.println(vehicle);
     }
 }

 // Display all rentals
 public void displayRentals() {
     System.out.println("Current Rentals:");
     if (rentals.isEmpty()) {
         System.out.println("No active rentals.");
     } else {
         for (Rental rental : rentals) {
             System.out.println(rental);
         }
     }
 }
}

//Main class
public class VehicleRentalSystem {
 public static void main(String[] args) {
     RentalSystem rentalSystem = new RentalSystem();
     Scanner scanner = new Scanner(System.in);

     while (true) {
         System.out.println("\nVehicle Rental System Menu:");
         System.out.println("1. Add Vehicle");
         System.out.println("2. Rent Vehicle");
         System.out.println("3. Return Vehicle");
         System.out.println("4. Display Vehicles");
         System.out.println("5. Display Rentals");
         System.out.println("6. Exit");
         System.out.print("Choose an option: ");

         int choice = scanner.nextInt();
         scanner.nextLine(); // Consume the newline

         switch (choice) {
             case 1:
                 System.out.print("Enter vehicle type (car/bike): ");
                 String type = scanner.nextLine().toLowerCase();

                 System.out.print("Enter registration number: ");
                 String reg = scanner.nextLine();

                 System.out.print("Enter model: ");
                 String model = scanner.nextLine();

                 System.out.print("Enter rental rate per day: ");
                 double rate = scanner.nextDouble();

                 if (type.equals("car")) {
                     rentalSystem.addVehicle(new Car(reg, model, rate));
                 } else if (type.equals("bike")) {
                     rentalSystem.addVehicle(new Bike(reg, model, rate));
                 } else {
                     System.out.println("Invalid vehicle type.");
                 }
                 break;

             case 2:
                 System.out.print("Enter registration number of vehicle to rent: ");
                 String rentReg = scanner.nextLine();

                 System.out.print("Enter customer name: ");
                 String custName = scanner.nextLine();

                 System.out.print("Enter customer contact: ");
                 String custContact = scanner.nextLine();

                 System.out.print("Enter rental days: ");
                 int days = scanner.nextInt();

                 rentalSystem.rentVehicle(rentReg, new Customer(custName, custContact), days);
                 break;

             case 3:
                 System.out.print("Enter registration number of vehicle to return: ");
                 String returnReg = scanner.nextLine();
                 rentalSystem.returnVehicle(returnReg);
                 break;

             case 4:
                 rentalSystem.displayVehicles();
                 break;

             case 5:
                 rentalSystem.displayRentals();
                 break;

             case 6:
                 System.out.println("Exiting...");
                 scanner.close();
                 return;

             default:
                 System.out.println("Invalid option. Please try again.");
         }
     }
 }
}
