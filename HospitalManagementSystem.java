package wiprotrainingday2;
import java.util.*;

//Class to represent a Patient
class Patient {
 private int id;
 private String name;
 private int age;

 public Patient(int id, String name, int age) {
     this.id = id;
     this.name = name;
     this.age = age;
 }

 public int getId() {
     return id;
 }

 public String getName() {
     return name;
 }

 public int getAge() {
     return age;
 }

 @Override
 public String toString() {
     return "Patient[ID: " + id + ", Name: " + name + ", Age: " + age + "]";
 }
}

//Class to represent a Doctor
class Doctor {
 private int id;
 private String name;
 private String specialization;

 public Doctor(int id, String name, String specialization) {
     this.id = id;
     this.name = name;
     this.specialization = specialization;
 }

 public int getId() {
     return id;
 }

 public String getName() {
     return name;
 }

 public String getSpecialization() {
     return specialization;
 }

 @Override
 public String toString() {
     return "Doctor[ID: " + id + ", Name: " + name + ", Specialization: " + specialization + "]";
 }
}

//Class to represent an Appointment
class Appointment {
 private int id;
 private Patient patient;
 private Doctor doctor;
 private String date;

 public Appointment(int id, Patient patient, Doctor doctor, String date) {
     this.id = id;
     this.patient = patient;
     this.doctor = doctor;
     this.date = date;
 }

 @Override
 public String toString() {
     return "Appointment[ID: " + id + ", Patient: " + patient.getName() + 
            ", Doctor: " + doctor.getName() + ", Date: " + date + "]";
 }
}

//Main Hospital Management System Class
public class HospitalManagementSystem {
 private Map<Integer, Patient> patients = new HashMap<>();
 private Map<Integer, Doctor> doctors = new HashMap<>();
 private Map<Integer, Appointment> appointments = new HashMap<>();
 private static int appointmentCounter = 1;

 // Method to add a new patient
 public void addPatient(int id, String name, int age) {
     if (patients.containsKey(id)) {
         System.out.println("Patient ID already exists.");
         return;
     }
     patients.put(id, new Patient(id, name, age));
     System.out.println("Patient added successfully.");
 }

 // Method to add a new doctor
 public void addDoctor(int id, String name, String specialization) {
     if (doctors.containsKey(id)) {
         System.out.println("Doctor ID already exists.");
         return;
     }
     doctors.put(id, new Doctor(id, name, specialization));
     System.out.println("Doctor added successfully.");
 }

 // Method to schedule an appointment
 public void scheduleAppointment(int patientId, int doctorId, String date) {
     try {
         Patient patient = patients.get(patientId);
         Doctor doctor = doctors.get(doctorId);

         if (patient == null) throw new IllegalArgumentException("Invalid patient ID.");
         if (doctor == null) throw new IllegalArgumentException("Invalid doctor ID.");

         Appointment appointment = new Appointment(appointmentCounter++, patient, doctor, date);
         appointments.put(appointment.getId(), appointment);
         System.out.println("Appointment scheduled: " + appointment);

     } catch (IllegalArgumentException e) {
         System.out.println("Error scheduling appointment: " + e.getMessage());
     }
 }

 // Method to display all appointments
 public void displayAppointments() {
     if (appointments.isEmpty()) {
         System.out.println("No appointments scheduled.");
     } else {
         System.out.println("Scheduled Appointments:");
         for (Appointment appointment : appointments.values()) {
             System.out.println(appointment);
         }
     }
 }

 public static void main(String[] args) {
     HospitalManagementSystem hms = new HospitalManagementSystem();
     Scanner scanner = new Scanner(System.in);

     while (true) {
         System.out.println("\nHospital Management System Menu:");
         System.out.println("1. Add Patient");
         System.out.println("2. Add Doctor");
         System.out.println("3. Schedule Appointment");
         System.out.println("4. Display Appointments");
         System.out.println("5. Exit");
         System.out.print("Choose an option: ");

         int choice = scanner.nextInt();
         switch (choice) {
             case 1:
                 System.out.print("Enter patient ID: ");
                 int patientId = scanner.nextInt();
                 scanner.nextLine(); // Consume newline
                 System.out.print("Enter patient name: ");
                 String patientName = scanner.nextLine();
                 System.out.print("Enter patient age: ");
                 int patientAge = scanner.nextInt();
                 hms.addPatient(patientId, patientName, patientAge);
                 break;

             case 2:
                 System.out.print("Enter doctor ID: ");
                 int doctorId = scanner.nextInt();
                 scanner.nextLine(); // Consume newline
                 System.out.print("Enter doctor name: ");
                 String doctorName = scanner.nextLine();
                 System.out.print("Enter doctor specialization: ");
                 String doctorSpecialization = scanner.nextLine();
                 hms.addDoctor(doctorId, doctorName, doctorSpecialization);
                 break;

             case 3:
                 System.out.print("Enter patient ID: ");
                 int pId = scanner.nextInt();
                 System.out.print("Enter doctor ID: ");
                 int dId = scanner.nextInt();
                 scanner.nextLine(); // Consume newline
                 System.out.print("Enter appointment date (e.g., YYYY-MM-DD): ");
                 String date = scanner.nextLine();
                 hms.scheduleAppointment(pId, dId, date);
                 break;

             case 4:
                 hms.displayAppointments();
                 break;

             case 5:
                 System.out.println("Exiting...");
                 scanner.close();
                 return;

             default:
                 System.out.println("Invalid option. Try again.");
         }
     }
 }
}
