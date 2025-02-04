package com.healthcare.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;


@Entity
@Table(name="Offices")
@Data
@ToString(exclude = "doctor")
public class Office {
    /*
    int officeId: Represents the unique identifier for each office.
String location: The location of the office.
String phone: The office's phone number.
Doctor doctor: A reference to the Doctor associated with this office (one-to-one relationship).
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int officeId;

    @Column(name = "Location")
    private String location;
    @Column(name="Phone")
    private String phone;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})  //which side do I need to set up as cascade
    @JoinColumn(name="DoctorId")
    private Doctor doctor;

}
