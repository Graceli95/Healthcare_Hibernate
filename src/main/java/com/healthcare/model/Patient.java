package com.healthcare.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "Patients")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude="doctors")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PatientID")
    private int patientId;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "DateOfBirth")
    private String dateOfBirth;

    @Column(name = "Email")
    private String email;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Appointment> appointments = new HashSet<>();

    @ManyToMany(mappedBy = "patients", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private Set<Doctor> doctors = new HashSet<>();



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return patientId == patient.patientId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(patientId);
    }
}
/**
 *@OneToMany with CascadeType.ALL
 *
 * @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
 * private Set<Appointment> appointments = new HashSet<>();
 *
 * Relationship: A Patient has multiple Appointments.
 * Mapped By: patient (meaning the Appointment entity has a patient field referring to this Patient).
 * CascadeType.ALL: This means that any operation (PERSIST, MERGE, REMOVE, REFRESH, DETACH) performed on Patient will cascade to Appointment entities.
 * If a Patient is saved, its appointments are also saved.
 * If a Patient is deleted, all their appointments are deleted as well.
 *
 * ✅ Reason for CascadeType.ALL:
 *
 * The lifecycle of Appointment is completely dependent on Patient. If a patient is removed, their appointments should also be removed. !!!!!!
 *
 */
/**
 * @ManyToMany with CascadeType.PERSIST
 *
 * @ManyToMany(mappedBy = "patients", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
 * private Set<Doctor> doctors = new HashSet<>();
 * Relationship: Many Patients can be associated with many Doctors.
 * Mapped By: patients (meaning the Doctor entity has a patients collection referring to this Patient).
 * CascadeType.PERSIST: This means that when a Patient is persisted, any new Doctor in the doctors set will also be persisted automatically.
 * However, other operations like REMOVE, MERGE, or DETACH do not cascade.
 * ✅ Reason for CascadeType.PERSIST:
 *
 * A Doctor is an independent entity. If a Patient is deleted, we don't want to delete the doctor from the database.!!!!!!!!!
 * But when creating a new patient with new associated doctors, those doctors should be saved automatically.!!!!!!!
 */
