package com.healthcare.service;

import com.healthcare.model.Appointment;
import com.healthcare.model.Doctor;
import com.healthcare.model.Patient;
import com.healthcare.repository.AppointmentRepositoryImpl;
import com.healthcare.repository.DoctorRepositoryImpl;
import com.healthcare.repository.PatientRepositoryImpl;

import java.util.List;

public class AppointmentService {
    private final AppointmentRepositoryImpl appointmentRepository;
    private final DoctorRepositoryImpl doctorRepository;
    private final PatientRepositoryImpl patientRepository;

    public AppointmentService(AppointmentRepositoryImpl appointmentRepository, DoctorRepositoryImpl doctorRepository, PatientRepositoryImpl patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public void createAppointment(Appointment appointment) {
        appointmentRepository.create(appointment);
    }

    public Appointment getAppointmentById(int appointmentId) {
        return appointmentRepository.getAppointmentById(appointmentId);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.getAllAppointments();
    }

    public void deleteAppointmentById(int appointmentId) {
//        appointmentRepository.delete(appointmentId);
        Appointment appointment = appointmentRepository.getAppointmentById(appointmentId);

        if (appointment != null) {
            Doctor doctor = doctorRepository.findById(appointment.getDoctor().getDoctorId()); // ✅ Fetch doctor eagerly
            Patient patient = patientRepository.getPatientById(appointment.getPatient().getPatientId()); // ✅ Fetch patient eagerly

            //delete the appointment first
            appointmentRepository.delete(appointmentId);

            // Check if this was the last appointment between the doctor and patient
            boolean hasOtherAppointments = appointmentRepository.hasOtherAppointmentsBetween(doctor.getDoctorId(), patient.getPatientId());

            if (hasOtherAppointments) {
                // If no other appointments exist, remove the relationship
                doctor.getPatients().remove(patient);
                patient.getDoctors().remove(doctor);
                doctorRepository.updateDoctor(doctor);
                patientRepository.updatePatient(patient);
            }

            System.out.println("Appointment deleted successfully");
        }else{
            System.out.println("Appointment not found");
        }

    }

    public void updateAppointment(Appointment appointment) {
        Appointment existingAppointment = appointmentRepository.getAppointmentById(appointment.getAppointmentId());

        if (existingAppointment != null) {
            Doctor oldDoctor = doctorRepository.findById(existingAppointment.getDoctor().getDoctorId());
            Patient oldPatient = patientRepository.getPatientById(existingAppointment.getPatient().getPatientId());
            Doctor newDoctor = doctorRepository.findById(appointment.getDoctor().getDoctorId());
            Patient newPatient = patientRepository.getPatientById(appointment.getPatient().getPatientId());

            if(!oldDoctor.equals(newDoctor)) {
                // Check if old doctor-patient relationship should be removed
                boolean hasOtherAppointments = appointmentRepository.hasOtherAppointmentsBetween(oldDoctor.getDoctorId(), newPatient.getPatientId());

                if (!hasOtherAppointments) {
                    oldDoctor.getPatients().remove(oldPatient);
                    oldPatient.getDoctors().remove(oldDoctor);
                    doctorRepository.updateDoctor(oldDoctor);
                    patientRepository.updatePatient(oldPatient);
                }

                // Add the new doctor-patient relationship
                newDoctor.getPatients().add(newPatient);
                newPatient.getDoctors().add(newDoctor);
                doctorRepository.updateDoctor(newDoctor);
                patientRepository.updatePatient(newPatient);
            }

            if(!oldPatient.equals(newPatient)) {
                boolean hasOtherAppointments = appointmentRepository.hasOtherAppointmentsBetween(oldPatient.getPatientId(), newPatient.getPatientId());
                if (!hasOtherAppointments) {

                    oldDoctor.getPatients().remove(oldPatient);
                    oldPatient.getDoctors().remove(oldDoctor);

                    doctorRepository.updateDoctor(oldDoctor);
                    patientRepository.updatePatient(oldPatient);
                }
                newDoctor.getPatients().add(newPatient);
                newPatient.getDoctors().add(newDoctor);

                doctorRepository.updateDoctor(newDoctor);
                patientRepository.updatePatient(newPatient);
            }
            // Update the appointment itself
            appointmentRepository.update(appointment);

        }else{
            System.out.println("Appointment not found");
        }
    }

    public boolean hasOtherAppointmentsBetween(int doctorId, int patientId){
        return appointmentRepository.hasOtherAppointmentsBetween(doctorId, patientId);

    }
}
