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
    private final SessionFactory sessionFactory;

    public DoctorRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createDoctor(Doctor doctor) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(doctor);
            transaction.commit();
        }
    }

    public Doctor findById(int id){ //method (JOIN FETCH) gets Doctor + patients immediately in one query.
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "SELECT d FROM Doctor d LEFT JOIN FETCH d.patients WHERE d.doctorId = :id", Doctor.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

//    public Doctor findById(int doctorId) { //method (session.get()) only loads the Doctor, and patients remain lazy.
//        try (Session session = sessionFactory.openSession()) {
//            return session.get(Doctor.class, doctorId);
//        }
//
//    }

    public void updateDoctor(Doctor doctor){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(doctor);
            transaction.commit();
        }
    }

    public void deleteDoctor(int doctorId){
        try (Session session = sessionFactory.openSession()) { //1. Opens a new session using sessionFactory.openSession().Since it's a new session, all entities retrieved from the database start in a "detached" state (i.e., they are not actively tracked by Hibernate).
            Transaction transaction = session.beginTransaction(); //2. We start a new transaction because session.remove(doctor); is a write operation (delete).All database changes must be wrapped inside a transaction.
            Doctor doctor = session.get(Doctor.class, doctorId); //3. Uses session.get() to fetch the Doctor entity by its doctorId.If the doctor exists in the database, it is now in the persistent state.Otherwise, doctor will be null

            if(doctor != null){ //4. Check if the Doctor Exists, If doctor does not exist (null), we skip deletion. If doctor exists, we check for associated relationships before deletion.
                if(doctor.getOffice() != null){ //5. Handle the Office Relationship
                       doctor.getOffice().setDoctor(null); //This part is crucial because Doctor is in a bidirectional relationship with Office, The Doctor entity has a one-to-one relationship with Office,
                       //Before deleting the Doctor, we set its office field to null to break the relationship.
                       //This prevents Hibernate from trying to delete the associated Office (if it's not supposed to be deleted).
                    session.merge(doctor.getOffice()); //is used to update the detached Office entity before the Doctor is removed.
                    //The Office entity was retrieved as part of Doctor, but since it is not managed in the current session, it is detached.
                    //session.merge(doctor.getOffice()); ensures the Office entity is reattached to the session so that Hibernate can update it (doctor field set to null) before committing.
                   }
                   session.remove(doctor); //Now that the Office entity is updated, we safely remove the Doctor.Since Office no longer holds a reference to Doctor, Hibernate won't complain about foreign key constraints.
            }
            transaction.commit(); //This finalizes the changes and persists them to the database
        }
    }

    /**
     * Why is session.merge() Needed?
     * Hibernate follows a session scope model, meaning:
     *
     * 1. Entities fetched in a session are in a "persistent" state (tracked by Hibernate).
     * 2. Entities retrieved before a session begins are "detached" and need to be "reattached" using merge().
     * Since we retrieved Office via Doctor but didn't explicitly fetch it in the session, it is in a detached state.
     * Calling merge():
     *
     * 1. Reattaches Office to the session.
     * 2. Applies the update (doctor set to null).
     * 3. Prevents "detached entity passed to persist" error.
     *
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
