package com;

import com.healthcare.model.Appointment;
import com.healthcare.repository.AppointmentRepositoryImpl;
import com.healthcare.service.AppointmentService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main_Appointment {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure("patient.cfg.xml").buildSessionFactory();
        AppointmentRepositoryImpl appointmentRepository  = new AppointmentRepositoryImpl(sessionFactory);

        AppointmentService appointmentService =  new AppointmentService(appointmentRepository);

        Scanner scanner = new Scanner(System.in);
        System.out.println("1.Create a new appointment");
        System.out.println("2.Search for an existing appointment");
        System.out.println("3.Update an existing appointment");
        System.out.println("4.Delete an existing appointment");

        int choice = scanner.nextInt();
        scanner.nextLine();

        try{
            switch (choice) {
                case 1:
                    // Application calls the service layer, not the repository directly
                    Appointment newAppointment = new Appointment();
//
                    System.out.println("Enter Patient Id");
                    newAppointment.setPatientId(scanner.nextInt());
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
//                            //Appointment appointment = appointmentService.getAppointmentById(appointmentId);
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
                default:
                    System.out.println("Invalid Choice");


            }

            }finally {
            scanner.close();
            sessionFactory.close();
        }




    }





}


