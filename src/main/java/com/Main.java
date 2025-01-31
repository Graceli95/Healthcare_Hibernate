package com;

import com.healthcare.model.Appointment;
import com.healthcare.model.Doctor;
import com.healthcare.model.Patient;
import com.healthcare.repository.AppointmentRepositoryImpl;
import com.healthcare.repository.DoctorRepositoryImpl;
import com.healthcare.service.AppointmentService;
import com.healthcare.service.DoctorService;
import com.healthcare.service.PatientService;
import com.healthcare.repository.PatientRepositoryImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;



public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SessionFactory sessionFactory = new Configuration().configure("patient.cfg.xml").buildSessionFactory();

        PatientRepositoryImpl patientRepository = new PatientRepositoryImpl(sessionFactory);
        PatientService patientService = new PatientService(patientRepository);

        DoctorRepositoryImpl doctorRepository = new DoctorRepositoryImpl(sessionFactory);
        DoctorService doctorService = new DoctorService(doctorRepository);

        AppointmentRepositoryImpl appointmentRepository = new AppointmentRepositoryImpl(sessionFactory);
        AppointmentService appointmentService = new AppointmentService(appointmentRepository);

        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Manage Patients");
            System.out.println("2. Manage Doctors");
            System.out.println("3. Manage Appointments");
            System.out.println("4. Exit");
            int category = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (category) {
                case 1:
                    managePatients(patientService, scanner);
                    break;
                case 2:
                    manageDoctors(doctorService, scanner);
                    break;
                case 3:
                    manageAppointments(appointmentService, scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    sessionFactory.close(); // Close sessionFactory once here
                    return; // Exit program
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void managePatients(PatientService patientService, Scanner scanner) {
        while (true) {
            System.out.println("\nManage Patients:");


            System.out.println("1. Create Patient");
            System.out.println("2. Read Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Application calls the service layer, not the repository directly
                    Patient newPatient = new Patient();
                    System.out.print("Enter first name: ");
                    newPatient.setFirstName(scanner.nextLine());
                    System.out.print("Enter last name: ");
                    newPatient.setLastName(scanner.nextLine());
                    System.out.print("Enter date of birth (YYYY-MM-DD): ");
                    newPatient.setDateOfBirth(scanner.nextLine());
                    System.out.print("Enter email: ");
                    newPatient.setEmail(scanner.nextLine());
                    System.out.print("Enter phone number: ");
                    newPatient.setPhoneNumber(scanner.nextLine());
                    patientService.createPatient(newPatient);  // Use service here
                    System.out.println("Patient created successfully.");
                    break;
                case 2:
                    // Application calls the service layer, not the repository directly
                    System.out.print("Enter Patient ID: ");
                    int patientId = scanner.nextInt();
                    scanner.nextLine();
                    Patient patient = patientService.getPatientById(patientId);  // Use service here
                    if (patient != null) {
                        System.out.println("Patient ID: " + patient.getPatientId());
                        System.out.println("Name: " + patient.getFirstName() + " " + patient.getLastName());
                        System.out.println("Date of Birth: " + patient.getDateOfBirth());
                        System.out.println("Email: " + patient.getEmail());
                        System.out.println("Phone: " + patient.getPhoneNumber());
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;
                case 3:
                    // Application calls the service layer, not the repository directly
                    System.out.print("Enter Patient ID: ");
                    patientId = scanner.nextInt();
                    scanner.nextLine();  // consume newline
                    patient = patientService.getPatientById(patientId);  // Use service here
                    if (patient != null) {
                        System.out.print("Enter new first name: ");
                        patient.setFirstName(scanner.nextLine());
                        System.out.print("Enter new last name: ");
                        patient.setLastName(scanner.nextLine());
                        System.out.print("Enter new date of birth (YYYY-MM-DD): ");
                        patient.setDateOfBirth(scanner.nextLine());
                        System.out.print("Enter new email: ");
                        patient.setEmail(scanner.nextLine());
                        System.out.print("Enter new phone number: ");
                        patient.setPhoneNumber(scanner.nextLine());
                        patientService.updatePatient(patient);  // Use service here
                        System.out.println("Patient updated successfully.");
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;
                case 4:
                    // Application calls the service layer, not the repository directly
                    System.out.print("Enter Patient ID: ");
                    patientId = scanner.nextInt();
                    patientService.deletePatient(patientId);  // Use service here
                    System.out.println("Patient deleted successfully.");
                    break;
                case 5:
                    return; // Go back to main menu
                default:
                    System.out.println("Invalid choice.");
            }


        }

    }

    private static void manageDoctors(DoctorService doctorService, Scanner scanner) {
        while (true) {
            System.out.println("\nManage Doctors:");


            System.out.println("1. Create Doctor");
            System.out.println("2. Read Doctor");
            System.out.println("3. Update Doctor");
            System.out.println("4. Delete Doctor");

            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1:
                    // Application calls the service layer, not the repository directly
                    Doctor newDoctor = new Doctor();
                    System.out.print("Enter first name: ");
                    newDoctor.setFirstName(scanner.nextLine());
                    System.out.print("Enter last name: ");
                    newDoctor.setLastName(scanner.nextLine());
                    System.out.print("Enter specialty: ");
                    newDoctor.setSpecialty(scanner.nextLine());
                    System.out.print("Enter email: ");
                    newDoctor.setEmail(scanner.nextLine());
                    doctorService.createDoctor(newDoctor);  // Use service here
                    System.out.println("Doctor created successfully.");
                    break;
                case 2:
                    // Application calls the service layer, not the repository directly
                    System.out.print("Enter Doctor ID: ");
                    int doctorId = scanner.nextInt();
                    Doctor doctor = doctorService.getDoctor(doctorId);  // Use service here
                    if (doctor != null) {
                        System.out.println("Doctor ID: " + doctor.getDoctorId());
                        System.out.println("Name: " + doctor.getFirstName() + " " + doctor.getLastName());

                        System.out.println("Email: " + doctor.getEmail());

                    } else {
                        System.out.println("Doctor not found.");
                    }
                    break;
                case 3:
                    // Application calls the service layer, not the repository directly
                    System.out.print("Enter DoctorID: ");
                    doctorId = scanner.nextInt();
                    scanner.nextLine();  // consume newline
                    doctor = doctorService.getDoctor(doctorId);  // Use service here
                    if (doctor != null) {
                        System.out.print("Enter new first name: ");
                        doctor.setFirstName(scanner.nextLine());
                        System.out.print("Enter new last name: ");
                        doctor.setLastName(scanner.nextLine());
                        System.out.print("Enter new email: ");
                        doctor.setEmail(scanner.nextLine());
                        doctorService.updateDoctor(doctor);  // Use service here
                        System.out.println("Doctor updated successfully.");
                    } else {
                        System.out.println("Doctor not found.");
                    }
                    break;
                case 4:
                    // Application calls the service layer, not the repository directly
                    System.out.print("Enter Doctor ID: ");
                    doctorId = scanner.nextInt();
                    doctorService.deleteDoctor(doctorId);  // Use service here
                    System.out.println("Doctor deleted successfully.");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }

        }
    }


    private static void manageAppointments(AppointmentService appointmentService, Scanner scanner) {

        while (true) {
            System.out.println("\nManage Appointments:");
            System.out.println("1.Create a new appointment");
            System.out.println("2.Search for an existing appointment");
            System.out.println("3.Update an existing appointment");
            System.out.println("4.Delete an existing appointment");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Application calls the service layer, not the repository directly
                    Appointment newAppointment = new Appointment();
                    System.out.println("Enter Patient Id");
                    newAppointment.setPatientId(scanner.nextInt());
                    scanner.nextLine();
                    System.out.println("Enter Doctor Id");
                    newAppointment.setDoctorId(scanner.nextInt());
                    scanner.nextLine();
                    System.out.println("Enter Appointment Date (YYYY-MM-DD):");
                    newAppointment.setAppointmentDate(scanner.nextLine());
                    System.out.println("Enter appointment's Notes description");
                    newAppointment.setNotes(scanner.nextLine());
                    appointmentService.createAppointment(newAppointment); // Use service here
                    System.out.println("Appointment created successfully");
                    break;
                case 2:
                    // Application calls the service layer, not the repository directly
                    System.out.println("Enter appointment's ID");
                    int appointmentId = scanner.nextInt();
                    Appointment appointment = appointmentService.getAppointmentById(appointmentId);
                    if (appointment != null) {
                        System.out.println("Appointment Id: " + appointment.getAppointmentId());
                        System.out.println("Patient ID: " + appointment.getPatientId());
                        System.out.println("Doctor ID: " + appointment.getDoctorId());
                        System.out.println("Appointment Date: " + appointment.getAppointmentDate());
                        System.out.println("Notes: " + appointment.getNotes());
                        System.out.println("find an existing appointment");
                    } else {
                        System.out.println("Appointment not found.");
                    }
                    break;

                case 3:
                    System.out.println("Enter appointment's ID");
                    int appointmentIdi = scanner.nextInt();
                    scanner.nextLine();
                    Appointment appointment1 = appointmentService.getAppointmentById(appointmentIdi);

                    if (appointment1 != null) {
                        System.out.println("Enter new Patient Id");
                        appointment1.setPatientId(scanner.nextInt());
                        scanner.nextLine();
                        System.out.println("Enter new Doctor Id");
                        appointment1.setDoctorId(scanner.nextInt());
                        scanner.nextLine();
                        System.out.println("Enter new Appointment Date (YYYY-MM-DD):");
                        appointment1.setAppointmentDate(scanner.nextLine());
                        System.out.println("Enter new appointment's Notes description");
                        appointment1.setNotes(scanner.nextLine());
                        appointmentService.updateAppointment(appointment1); // Use service here
                        System.out.println("Appointment updated successfully");


                    } else {
                        System.out.println("Appointment not found.");
                    }
                    break;
                case 4:
                    System.out.println("Enter appointment's ID");
                    appointmentId = scanner.nextInt();
                    appointmentService.deleteAppointmentById(appointmentId);
                    System.out.println("Appointment deleted successfully"); // Use service here
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid Choice");


            }
        }


    }
}

