//package com.healthcare.service;
//
//public class DoctorService {
//}
package com.healthcare.service;

import com.healthcare.model.Doctor;
import com.healthcare.repository.DoctorRepositoryImpl;
import com.healthcare.repository.PatientRepositoryImpl;
import com.healthcare.model.Patient;


import java.util.List;

public class DoctorService {

    private final DoctorRepositoryImpl doctorRepository;

    public DoctorService(DoctorRepositoryImpl doctorRepository) {

        this.doctorRepository = doctorRepository;
    }

    public void createDoctor(Doctor doctor) {

        doctorRepository.createDoctor(doctor);
    }

    public Doctor getDoctor(int id) {
        return doctorRepository.getDoctorId(id);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.getAllDoctors();
    }

    public void updateDoctor(Doctor doctor) {
       doctorRepository.updateDoctor(doctor);
    }

    public void deleteDoctor(int id) {
        doctorRepository.deleteDoctor(id);
    }

    public void addPatientToDoctor(int doctorId, Patient patient){
        doctorRepository.addPatientToDoctor(doctorId, patient);
    }
    public void removePatientFromDoctor(int doctorId, Patient patient){
        doctorRepository.removePatientFromDoctor(doctorId, patient);
    }
}
