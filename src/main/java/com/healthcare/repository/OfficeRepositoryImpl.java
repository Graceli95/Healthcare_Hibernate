package com.healthcare.repository;

import com.healthcare.model.Doctor;
import com.healthcare.model.Office;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class OfficeRepositoryImpl {
    private final SessionFactory sessionFactory;

    public OfficeRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createOffice(Office office){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            //Ensure the doctor is managed
            Doctor managedDoctor = session.get(Doctor.class, office.getDoctor().getDoctorId());
            if (managedDoctor == null) {
                throw new IllegalArgumentException("Doctor with ID " + office.getDoctor().getDoctorId() + " does not exist");
            }
            office.setDoctor(managedDoctor); //Use the managed entity
            session.persist(office);
            transaction.commit();
        }
    }

    public Office getOfficeById(int officeId){
        try (Session session = sessionFactory.openSession()) {
            return session.get(Office.class, officeId);
        }

    }

    public void updateOffice(Office office){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Ensure the new doctor is managed (if changed)
            if(office.getDoctor() != null){
                Doctor managedDoctor = session.get(Doctor.class, office.getDoctor().getDoctorId());
                office.setDoctor(managedDoctor);
            }
            session.merge(office);
            transaction.commit();

        }
    }

    public void deleteOffice(int officeId){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Office office = session.get(Office.class, officeId);
            if (office != null) {
                if (office.getDoctor() != null) {
                    office.getDoctor().setOffice(null); // Break the association
                    session.merge(office.getDoctor()); // Persist the change in the database
                }
                session.remove(office);
            }
            transaction.commit();
        }

    }

    public List<Office> getAllOffices(){
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Office", Office.class).list();
        }
    }


}
