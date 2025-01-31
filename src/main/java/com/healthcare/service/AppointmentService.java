package com.healthcare.service;

import com.healthcare.model.Appointment;
import com.healthcare.repository.AppointmentRepositoryImpl;

import java.util.List;

public class AppointmentService {
    public final AppointmentRepositoryImpl appointmentRepository;

    public AppointmentService(AppointmentRepositoryImpl appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
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
        appointmentRepository.delete(appointmentId);
    }

    public void updateAppointment(Appointment appointment) {
        appointmentRepository.update(appointment);
    }
}
