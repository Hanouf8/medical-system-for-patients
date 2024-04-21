package com.medical.healthcare.service;

import com.medical.healthcare.model.Drug;
import com.medical.healthcare.repository.DrugRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class DrugServiceImpl implements DrugService {

    private final DrugRepository drugRepository;

    public DrugServiceImpl(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    @Override
    public List<Drug> getAllDrugs() {
        return drugRepository.findAll();
    }

    @Override
    public Drug getDrugById(Long id) {
        return drugRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drug"));
    }

    @Override
    public Drug createDrug(Drug drug) {
        return drugRepository.save(drug);
    }

    @Override
    public void deleteDrug(Long id) {
        drugRepository.deleteById(id);
    }

    @Override
    public List<Drug> searchDrugs(String query) {
        return drugRepository.findByNameContaining(query);
    }

    @Override
    public Drug getDrugByName(String drugName){
        return drugRepository.findByName(drugName);
    }
}
