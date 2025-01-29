package com.healthcare.repository;

import com.healthcare.model.Appointment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class AppointmentRepositoryImpl {

    private SessionFactory sessionFactory;

    public AppointmentRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void create(Appointment appointment) {
       try(Session session = sessionFactory.openSession()){
           Transaction transaction = session.beginTransaction();
           session.save(appointment);
           transaction.commit();
       }


    }

    public Appointment getAppointmentById(int id) {
        try(Session session = sessionFactory.openSession()){
            return session.get(Appointment.class, id);
        }

    }

    public void update(Appointment appointment) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.update(appointment);
            transaction.commit();
        }
    }

    public void delete(int id) {
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Appointment appointment = session.get(Appointment.class,id);
            if(appointment != null){
                session.delete(appointment);
            }
            transaction.commit();
        }
    }

    public List<Appointment> getAllAppointments() {
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("from Appointment",Appointment.class).list();
        }
    }


}




