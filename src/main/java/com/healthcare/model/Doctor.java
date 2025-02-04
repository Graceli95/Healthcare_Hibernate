package com.healthcare.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@ToString(exclude={"patients", "appointments"})
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

@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
private Set<Appointment> appointments = new HashSet<>();

@OneToOne(mappedBy = "doctor", cascade = CascadeType.ALL)  //the use of mappedBy in Hibernate to indicate the owner of the relationship.
private Office office;

@ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
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
