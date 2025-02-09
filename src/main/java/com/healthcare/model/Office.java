package com.healthcare.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Entity
@Table(name="Offices")
@Getter
@Setter
@NoArgsConstructor
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
    @Column(name="OfficeID")
    private int officeId;

    @Column(name = "Location")
    private String location;
    @Column(name="Phone")
    private String phone;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="DoctorId", nullable = true)  //allow DoctorID to be null
    private Doctor doctor;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Office office = (Office) o;
        return officeId == office.officeId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(officeId);
    }
}
