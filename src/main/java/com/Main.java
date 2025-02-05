package com;

import com.healthcare.model.Appointment;
import com.healthcare.model.Doctor;
import com.healthcare.model.Office;
import com.healthcare.model.Patient;
import com.healthcare.repository.AppointmentRepositoryImpl;
import com.healthcare.repository.DoctorRepositoryImpl;
import com.healthcare.repository.OfficeRepositoryImpl;
import com.healthcare.service.AppointmentService;
import com.healthcare.service.DoctorService;
import com.healthcare.service.OfficeService;
import com.healthcare.service.PatientService;
import com.healthcare.repository.PatientRepositoryImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


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

        OfficeRepositoryImpl officeRepository = new OfficeRepositoryImpl(sessionFactory);
        OfficeService officeService = new OfficeService(officeRepository);

        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Manage Patients");
            System.out.println("2. Manage Doctors");
            System.out.println("3. Manage Appointments");
            System.out.println("4. Manage Offices");
            System.out.println("5. Exit");

            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    managePatients(patientService, scanner);
                    break;
                case 2:
                    manageDoctors(doctorService, scanner);
                    break;
                case 3:
                    manageAppointments(appointmentService, doctorService, patientService, scanner);
                    break;
                case 4:
                    manageOffices(officeService, doctorService, scanner);
                    break;
                case 5:
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
            System.out.println("5. Return to Main Menu");
            System.out.println("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); //consume newline

            switch (choice) {
                case 1: //create a new patient
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
                case 2:  //read patient details by ID
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
                case 3:  // update patient details
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
                case 4:  //delete a patient by ID
                    // Application calls the service layer, not the repository directly
                    System.out.print("Enter Patient ID: ");
                    patientId = scanner.nextInt();
                    patientService.deletePatient(patientId);  // Use service here
                    System.out.println("Patient deleted successfully.");
                    break;
                case 5:
                    return; // Go back to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
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
            System.out.println("5. Return to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); //consume newline


            switch (choice) {
                case 1:  // Code to create a new doctor
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
                case 2:  // read doctor details by ID
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
                case 3: //update doctor details
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
                case 4: //delete a doctor by ID
                    // Application calls the service layer, not the repository directly
                    System.out.print("Enter Doctor ID: ");
                    doctorId = scanner.nextInt();
                    doctorService.deleteDoctor(doctorId);  // Use service here
                    System.out.println("Doctor deleted successfully.");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again");
            }

        }
    }

    private static void manageAppointments(AppointmentService appointmentService,DoctorService doctorService, PatientService patientService, Scanner scanner) {
        System.out.println("\nManage Appointments");
        System.out.println("1. Create Appointment");
        System.out.println("2. Read Appointment");
        System.out.println("3. Update Appointment");
        System.out.println("4. Delete Appointment");
        System.out.println("5. List All Appointments");

        int choice = scanner.nextInt();
        scanner.nextLine();  // consume newline

        switch (choice) {
            case 1:

                Appointment newAppointment = new Appointment();
                System.out.print("Enter patient ID: ");
                Patient patient = new Patient();
                patient.setPatientId(scanner.nextInt());
                newAppointment.setPatient(patient);
                System.out.print("Enter doctor ID: ");
                Doctor doctor = new Doctor();
                doctor.setDoctorId(scanner.nextInt());
                newAppointment.setDoctor(doctor);
                scanner.nextLine();  // consume newline
                System.out.print("Enter appointment date (YYYY-MM-DD): ");
                newAppointment.setAppointmentDate(scanner.nextLine());
                System.out.print("Enter notes: ");
                newAppointment.setNotes(scanner.nextLine());
                appointmentService.createAppointment(newAppointment);
                System.out.println("Appointment created successfully.");
                doctorService.addPatientToDoctor(doctor.getDoctorId(),patient);
                patientService.addDoctorToPatient(patient.getPatientId(),doctor);
                break;
            case 2:
                System.out.print("Enter Appointment ID: ");
                int appointmentId = scanner.nextInt();
                Appointment appointment = appointmentService.getAppointmentById(appointmentId);
                if (appointment != null) {
                    System.out.println("Appointment ID: " + appointment.getAppointmentId());
                    System.out.println("Patient ID: " + appointment.getPatient().getPatientId());
                    System.out.println("Doctor ID: " + appointment.getDoctor().getDoctorId());
                    System.out.println("Appointment Date: " + appointment.getAppointmentDate());
                    System.out.println("Notes: " + appointment.getNotes());

                } else {
                    System.out.println("Appointment not found.");
                }
                break;
            case 3:
                System.out.print("Enter Appointment ID: ");
                appointmentId = scanner.nextInt();
                scanner.nextLine();  // consume newline
                appointment = appointmentService.getAppointmentById(appointmentId);
                Doctor originalDoctor = appointment.getDoctor();
                Patient originalPatient = appointment.getPatient();
                if (appointment != null) {
                    System.out.print("Enter new patient ID: ");
                    patient = new Patient();
                    patient.setPatientId(scanner.nextInt());
                    appointment.setPatient(patient);
                    System.out.print("Enter new doctor ID: ");
                    doctor = new Doctor();
                    doctor.setDoctorId(scanner.nextInt());
                    appointment.setDoctor(doctor);
                    scanner.nextLine();  // consume newline
                    System.out.print("Enter new appointment date (YYYY-MM-DD): ");
                    appointment.setAppointmentDate(scanner.nextLine());
                    System.out.print("Enter new notes: ");
                    appointment.setNotes(scanner.nextLine());
                    doctorService.addPatientToDoctor(doctor.getDoctorId(),patient);
                    patientService.addDoctorToPatient(patient.getPatientId(),doctor);
                    if (!appointmentService.hasOtherAppointmentsBetween(
                            originalDoctor.getDoctorId(), originalPatient.getPatientId())) {
                        doctorService.removePatientFromDoctor(originalDoctor.getDoctorId(), originalPatient);
                        patientService.removeDoctorFromPatient(originalPatient.getPatientId(), originalDoctor);
                    }

                    appointmentService.updateAppointment(appointment);

                    System.out.println("Appointment updated successfully.");



                }

                else {
                    System.out.println("Appointment not found.");
                }
                break;
            case 4:
                System.out.print("Enter Appointment ID: ");
                appointmentId = scanner.nextInt();
                scanner.nextLine();
                Appointment appointment1 = appointmentService.getAppointmentById(appointmentId);
                if (appointment1 != null) {
                    Doctor doctorToCheck = doctorService.getDoctor(appointment1.getDoctor().getDoctorId());  // ✅ Fetch doctor eagerly
                    Patient patientToCheck = patientService.getPatientById(appointment1.getPatient().getPatientId()); // ✅ Fetch patient eagerly
                    // Doctor doctorToCheck=appointment1.getDoctor();
                    //Patient patientToCheck=appointment1.getPatient();

                    //delete the appointment first
                    appointmentService.deleteAppointmentById(appointmentId);

                    // Check if this was the last appointment between the doctor and patient
                    boolean hasOtherAppointments = appointmentService.hasOtherAppointmentsBetween(doctorToCheck.getDoctorId(), patientToCheck.getPatientId());

                    if(!hasOtherAppointments) {
                        // If no other appointments exist, remove the relationship
                        doctorToCheck.getPatients().remove(patientToCheck);
                        patientToCheck.getDoctors().remove(doctorToCheck);
                        doctorService.updateDoctor(doctorToCheck);
                        patientService.updatePatient(patientToCheck);
                    }
                    System.out.println("Appointment deleted successfully.");
                } else {
                    System.out.println("Appointment not found.");
                }

//                appointmentService.deleteAppointmentById(appointmentId);
//                if (!appointmentService.hasOtherAppointmentsBetween(
//                        doctorToCheck.getDoctorId(), patientToCheck.getPatientId())) {
//                    doctorService.removePatientFromDoctor(doctorToCheck.getDoctorId(), patientToCheck);
//                    patientService.removeDoctorFromPatient(patientToCheck.getPatientId(), doctorToCheck);
//                }


                break;
            case 5:
                System.out.println("Listing All Appointments:");
                for (Appointment a : appointmentService.getAllAppointments()) {
                    System.out.println(a);
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void manageOffices(OfficeService officeService, DoctorService doctorService, Scanner scanner) {
        System.out.println("\nManage Offices");
        System.out.println("1. Create a new Office");
        System.out.println("2. Read an Office's details by ID");
        System.out.println("3. Update an Office details");
        System.out.println("4. Delete an Office by ID");
        System.out.println("5. List All Offices");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {

            case 1:
                Office newOffice = new Office();


                System.out.println("Enter Office location: ");
                newOffice.setLocation(scanner.nextLine());
                System.out.println("Enter Office Phone number: ");
                newOffice.setPhone(scanner.nextLine());

                System.out.println("Enter Doctor ID: ");
                Doctor doctor = doctorService.getDoctor(scanner.nextInt());

                scanner.nextLine();

                newOffice.setDoctor(doctor);
                officeService.crateOffice(newOffice);
                System.out.println("Office created successfully.");
                break;

            case 2: // Code to read office details by ID
                // Application calls the service layer, not the repository directly
                System.out.print("Enter Office ID: ");
                int officeId = scanner.nextInt();
                Office office = officeService.getOfficeById(officeId);
                if (office != null) {
                    System.out.println("Location: " + office.getLocation());
                    System.out.println("Phone number: " + office.getPhone());
                    System.out.println("Doctor ID: " + office.getDoctor().getDoctorId());
                    System.out.println("Read Office details successfully.");
                }else{
                    System.out.println("Office not found.");
                }
                break;
            case 3:
                System.out.print("Enter Office ID: ");
                officeId = scanner.nextInt();
                scanner.nextLine(); //consume newline
                office = officeService.getOfficeById(officeId);
                if (office != null) {
                    System.out.println("Enter new Office location: ");
                    office.setLocation(scanner.nextLine());
                    System.out.println("Enter new Office phone number: ");
                    office.setPhone(scanner.nextLine());
                    System.out.println("Enter Doctor ID: ");
//                    Doctor doctor1 = new Doctor();
//                    doctor1.setDoctorId(scanner.nextInt());
//                    office.setDoctor(doctor1);
                    office.getDoctor().setDoctorId(scanner.nextInt());
                    officeService.updateOffice(office); //use service here
                    System.out.println("Office updated successfully.");
                }else{
                    System.out.println("Office not found.");
                }
                break;
            case 4:
                System.out.print("Enter Office ID: ");
                officeId = scanner.nextInt();
                officeService.deleteOffice(officeId);
                System.out.println("Office deleted successfully.");
                break;
            case 5:
                System.out.println("Listing All Offices:");
                for (Office a : officeService.getAllOffices()) {
                    System.out.println(a);
                    System.out.println(a.getDoctor().getDoctorId());
                } break;
            default:
                System.out.println("Invalid choice.");
        }
    }







}

