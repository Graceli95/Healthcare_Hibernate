package com.healthcare.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Data
@ToString(exclude="doctors")

@Entity
@Table(name = "Patients")
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

    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments = new HashSet<>();

    @ManyToMany(mappedBy = "patients")
    private Set<Doctor> doctors = new HashSet<>();



}
