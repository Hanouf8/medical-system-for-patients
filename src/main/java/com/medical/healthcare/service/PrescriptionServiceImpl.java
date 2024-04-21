package com.medical.healthcare.service;


import com.medical.healthcare.model.Prescription;
import com.medical.healthcare.model.User;
import com.medical.healthcare.repository.PrescriptionRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    @Override
    public Prescription getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription"));
    }

    @Override
    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    @Override
    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }

    @Override
    public List<Prescription> searchPrescriptions(String query) {
        return prescriptionRepository.searchPrescriptions(query);
    }


}