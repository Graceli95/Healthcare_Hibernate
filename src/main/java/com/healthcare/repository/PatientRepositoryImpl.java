package com.healthcare.repository;

import com.healthcare.model.Doctor;
import com.healthcare.model.Patient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PatientRepositoryImpl{

    private SessionFactory sessionFactory;

    public PatientRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createPatient(Patient patient) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(patient);
            transaction.commit();
        }
    }

//    public Patient getPatientById(int id) {
//        try (Session session = sessionFactory.openSession()) {
//            return session.get(Patient.class, id);
//        }
//    }
    public Patient getPatientById(int id) {
        try (Session session = sessionFactory.openSession()) {
            //This method retrieves a Patient entity by its id, along with its associated doctors.
            return  session.createQuery("select p from Patient p " +
                    "left join fetch p.doctors " +
                    "where p.patientId = :id", Patient.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    public void updatePatient(Patient patient) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(patient);
            transaction.commit();

        }
    }

    public void deletePatient(int patientId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Patient patient = session.get(Patient.class, patientId);
            if (patient != null) {
                session.remove(patient);
            }
            transaction.commit();
        }
    }

//    public List<Patient> getAllPatients() { //this method is when you only need patient details.
//        try (Session session = sessionFactory.openSession()) {
//            return session.createQuery("from Patient", Patient.class).list();
//        }
//    }
    public List<Patient> getAllPatients() {
        //This method retrieves all Patient entities along with their related appointments and doctors.
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select distinct p from Patient p" +
                    " left join fetch p.appointments " +
                    "left join fetch p.doctors", Patient.class)
                    .list();
        }
    }

    public void addDoctorToPatient(int patientId, Doctor doctor) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Patient patient = session.get(Patient.class, patientId);
            if (patient != null && !patient.getDoctors().contains(doctor)) {
                patient.getDoctors().add(doctor);
                session.merge(patient);
            }
            transaction.commit();

        }
    }

    public void removeDoctorFromPatient(int patientId, Doctor doctor){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Patient patient = session.get(Patient.class, patientId);
            if (patient != null && patient.getDoctors().contains(doctor)) {
                patient.getDoctors().remove(doctor);
                session.merge(patient);
            }
            transaction.commit();

        }
    }
}
