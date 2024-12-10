package com.medical.healthcare.service;

import com.medical.healthcare.model.Drug;

import java.util.List;

public interface DrugService {
    List<Drug> getAllDrugs();
    Drug getDrugById(Long id);
    Drug createDrug(Drug drug);
    void deleteDrug(Long id);
    List<Drug> searchDrugs(String query);

    Drug getDrugByName(String drugName);
}

