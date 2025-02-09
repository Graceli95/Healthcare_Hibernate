package com.healthcare.model;

/**
 * 📌 What Does mappedBy Actually Do?
 * The mappedBy attribute in a bidirectional JPA relationship removes redundancy by specifying which entity owns the relationship.
 *
 * Without mappedBy: JPA assumes both entities are independent and may create extra join tables or duplicate foreign keys.
 * With mappedBy: JPA knows that one entity owns the relationship and the other just refers to it.
 *
 * The mappedBy attribute in JPA (Java Persistence API) is used in bidirectional relationships to specify the owner side of the relationship.
 * It tells Hibernate which entity field manages the relationship, avoiding duplicate foreign key columns in the database.
 * 📌 When to Use mappedBy?
 * If you have a bidirectional one-to-many, many-to-one, or many-to-many relationship, one side must be the owner, and the other side must use mappedBy.
 *    The owning side has the foreign key.
 *    The inverse side (non-owning side) uses mappedBy to refer to the field in the owning side.
 *
 * 🔥 Common Issues with mappedBy
 * 1. Foreign key column missing or unexpected join table?
 *    Ensure mappedBy is only used on the inverse side.
 *    The owning side should have @JoinColumn or @JoinTable.
 * 2. Wrong field name in mappedBy?
 *    The value of mappedBy should match the field name in the owning entity, not the table column name.
 * 3. Using mappedBy in a unidirectional relationship?
 *      Unidirectional relationships don’t need mappedBy.
 *      Just use @OneToMany or @ManyToOne with @JoinColumn.
 *
 *🛠️ Summary
 * Relationship Type	Owning Side (Has Foreign Key)	                        Inverse Side (Uses mappedBy)
 * One-to-Many	        @ManyToOne @JoinColumn in child	                        @OneToMany(mappedBy = "fieldName") in parent
 * Many-to-One	        @ManyToOne @JoinColumn in entity with foreign key	    No need for mappedBy
 * Many-to-Many	        @ManyToMany @JoinTable in one entity	                @ManyToMany(mappedBy = "fieldName") in the other
 *
 *
 * 🎯 When Should You Use mappedBy?
 * Relationship	    Owning Side (Has @JoinColumn or @JoinTable)	         Inverse Side (Uses mappedBy)
 * One-to-Many	    Child entity (@ManyToOne)	                         Parent entity (@OneToMany(mappedBy = "fieldName"))
 * Many-to-One	    Child entity (@ManyToOne @JoinColumn)	             No mappedBy needed
 * Many-to-Many	    The entity defining @JoinTable	                    Other entity uses mappedBy = "fieldName"

 */

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    @OneToOne(mappedBy = "doctor", cascade = {CascadeType.PERSIST, CascadeType.MERGE})  //the use of mappedBy in here, means that office owns the relationship, doctor doesn't own this relationship. doctor is the inverse side,office is the owning side
    private Office office;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private Set<Appointment> appointments = new HashSet<>();


    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY) //many to many creates a join table
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
