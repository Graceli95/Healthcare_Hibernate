package com.healthcare.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"patient", "doctor"})

@Table(name="Appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //with @Id and @GeneratedValue(strategy = GenerationType.IDENTITY) to indicate that this is the primary key and will auto-increment in the database.
    @Column(name="AppointmentId")
    private int appointmentId;


    @Column(name="AppointmentDate")
    private String appointmentDate;

    @Column(name="Notes")
    private String notes;

    @ManyToOne
    @JoinColumn(name = "DoctorID")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name="PatientID")
    private Patient patient;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return appointmentId == that.appointmentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(appointmentId);
    }

}
