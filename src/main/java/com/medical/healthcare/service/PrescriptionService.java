package com.medical.healthcare.service;

import com.medical.healthcare.model.Prescription;
import com.medical.healthcare.model.User;

import java.util.List;

public interface PrescriptionService {
    List<Prescription> getAllPrescriptions();
    Prescription getPrescriptionById(Long id);
    Prescription createPrescription(Prescription prescription);
    void deletePrescription(Long id);
    List<Prescription> searchPrescriptions(String query) ;

}