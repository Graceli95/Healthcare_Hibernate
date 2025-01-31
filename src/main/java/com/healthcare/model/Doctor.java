package com.healthcare.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@ToString(exclude="patients")
@Table(name="Doctors")

public class Doctor {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "DoctorId")
    private int doctorId;

@Column(name="FirstName")
    private String firstName;
@Column(name="LastName")
    private String lastName;
@Column(name="Specialty")
    private String specialty;
@Column(name="Email")
    private String Email;

@OneToMany(mappedBy = "doctor")
private Set<Appointment> appointments = new HashSet<>();


@ManyToMany
@JoinTable(
        name="Doctor_Patient",
        joinColumns = @JoinColumn(name="DoctorId"),
        inverseJoinColumns = @JoinColumn(name="PatientId")
)
private Set<Patient> patients = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return doctorId == doctor.doctorId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(doctorId);
    }
}
