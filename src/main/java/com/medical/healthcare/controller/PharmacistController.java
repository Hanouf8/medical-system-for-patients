package com.medical.healthcare.controller;

import com.medical.healthcare.controller.dto.PrescriptionDto;
import com.medical.healthcare.model.Drug;
import com.medical.healthcare.model.Pharmacy;
import com.medical.healthcare.model.Prescription;
import com.medical.healthcare.model.User;
import com.medical.healthcare.repository.DrugRepository;
import com.medical.healthcare.repository.PharmacyRepository;
import com.medical.healthcare.repository.PrescriptionRepository;
import com.medical.healthcare.service.DrugService;
import com.medical.healthcare.service.PrescriptionService;
import com.medical.healthcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping()
public class PharmacistController {

    @Autowired
    PharmacyRepository pharmacyRepository;
    @Autowired
    DrugRepository drugRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final UserService userService;
    private final PrescriptionService prescriptionService;
    private final DrugService drugService;

    public PharmacistController(PrescriptionRepository prescriptionRepository,
                                UserService userService, PrescriptionService prescriptionService,
                                DrugService drugService) {
        this.prescriptionRepository = prescriptionRepository;
        this.userService = userService;
        this.prescriptionService = prescriptionService;
        this.drugService = drugService;
    }


    @GetMapping("/pharmacist/drug")
    public String showDrug(Model model) {
        Drug drug = new Drug();
        drug.setPharmacy(new Pharmacy());
        model.addAttribute("drug", drug); // Add the "drug" object to the model

        return "pharmacist";
    }

    @PostMapping("/pharmacist/drug")
    public String createDrug(@ModelAttribute("drug") Drug drug, BindingResult result, Model model) {

        Pharmacy pharmacy = drug.getPharmacy();
        pharmacyRepository.save(pharmacy);

        drug.setPharmacy(pharmacy);
        drugRepository.save(drug);

        // Add the updated "drug" object back to the model
        model.addAttribute("drug", drug);

        return "redirect:/pharmacist";
    }

    @GetMapping("/pharmacist")
    public String pharmacist(Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("users", userList);
        List<Prescription> prescriptionList = prescriptionService.getAllPrescriptions();
        model.addAttribute("prescriptions", prescriptionList);

        Drug drug = new Drug(); // Add this line to create the "drug" object
        drug.setPharmacy(new Pharmacy());
        model.addAttribute("drug", drug); // Add the "drug" object to the model

        List<Drug> drugList = drugService.getAllDrugs();
        model.addAttribute("drugs", drugList);

        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        model.addAttribute("pharmacies", pharmacyList);

        return "pharmacist";
    }


    @GetMapping("/pharmacist/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
//        prescriptionRepository.deleteByUserId(id);
        prescriptionService.deletePrescription(id);
        return "redirect:/pharmacist"; // Redirect to the pharmacist page after deletion
    }



    @GetMapping("/pharmacist/drug/delete/")
    public String deleteDrug(Model model){
        Drug drug = new Drug(); // Add this line to create the "drug" object
        drug.setPharmacy(new Pharmacy());
        model.addAttribute("drug", drug); // Add the "drug" object to the model

        List<Drug> drugList = drugService.getAllDrugs();
        model.addAttribute("drugs", drugList);

        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        model.addAttribute("pharmacies", pharmacyList);
        return "pharmacist";
    }


    @PostMapping("/pharmacist/drug/delete/{id}")
    public String deleteDrug(@PathVariable("id") Long id) {

        Drug drug=drugService.getDrugById(id);
        drugRepository.deleteById(id);
        pharmacyRepository.deleteById(drug.getPharmacy().getId());

        return "redirect:/pharmacist";
    }


}
