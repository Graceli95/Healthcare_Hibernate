package com.healthcare.repository;

/*
Path: com.healthcaremanagement.repository
Class Name: DoctorRepositoryImpl
Methods:
Create Method: void createDoctor(Doctor doctor)
Use Hibernate’s session.save(doctor) method to insert a new doctor into the Doctors table.
Transaction: Wrap the save operation in a transaction (Transaction transaction = session.beginTransaction(); transaction.commit();). This ensures that the database operation is atomic.
Read Method: Doctor getDoctorById(int doctorId)
Use session.get(Doctor.class, doctorId) to retrieve a doctor by ID. This method returns null if the entity is not found.
No transaction is needed for this read operation, but you must ensure the session is properly opened and closed.
Update Method: void updateDoctor(Doctor doctor)
Use session.update(doctor) to update the existing doctor details in the database.
Transaction: Like the create method, wrap the update operation in a transaction to ensure changes are persisted correctly.
Delete Method: void deleteDoctor(int doctorId)
Use session.delete(doctor) to remove a doctor from the database.
Transaction: Deletion should also be wrapped in a transaction.
List All Method: List<Doctor> getAllDoctors()
Use HQL: session.createQuery("from Doctor", Doctor.class).list() to retrieve all doctors.
No transaction is needed for this operation, but ensure the session is opened and closed properly.
Good to Know:

Transactions: Always start a transaction before making changes to the database and commit after the operation to ensure data integrity.
HQL vs. SQL: Hibernate Query Language (HQL) is similar to SQL but operates on entities rather than tables, making it more object-oriented.
Session Management: Make sure to open and close sessions properly to avoid memory leaks.
 */

import com.healthcare.model.Doctor;
import com.healthcare.model.Patient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DoctorRepositoryImpl {
    private SessionFactory sessionFactory;

    public DoctorRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createDoctor(Doctor doctor) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(doctor);
            transaction.commit();
        }
    }

    public Doctor getDoctorId(int doctorId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Doctor.class, doctorId);
        }

    }
    public void updateDoctor(Doctor doctor){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(doctor);
            transaction.commit();
        }
    }
/*
Delete Method: void deleteDoctor(int doctorId)
Use session.delete(doctor) to remove a doctor from the database.
Transaction: Deletion should also be wrapped in a transaction.
 */
    public void deleteDoctor(int doctorId){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Doctor doctor = session.get(Doctor.class, doctorId);
            if (doctor != null) {
                session.delete(doctor);
            }
            transaction.commit();
        }
    }

    /*
    List All Method: List<Doctor> getAllDoctors()
Use HQL: session.createQuery("from Doctor", Doctor.class).list() to retrieve all doctors.
No transaction is needed for this operation, but ensure the session is opened and closed properly.
     */
    public List<Doctor> getAllDoctors(){
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Doctor", Doctor.class).list();
        }
    }

    public void addPatientToDoctor(int doctorId, Patient patient){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Doctor doctor = session.get(Doctor.class, doctorId);
            if (doctor != null && !doctor.getPatients().contains(patient)) {
                doctor.getPatients().add(patient);
                session.merge(doctor);
            }
            transaction.commit();
        }
    }

    public void removePatientFromDoctor(int doctorId, Patient patient){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Doctor doctor = session.get(Doctor.class, doctorId);
            if(doctor !=null && doctor.getPatients().contains(patient)){
                doctor.getPatients().remove(patient);
                session.merge(doctor);
            }
            transaction.commit();
        }
    }
}
