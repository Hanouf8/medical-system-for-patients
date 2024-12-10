package com.medical.healthcare.controller;

import com.medical.healthcare.model.Drug;
import com.medical.healthcare.model.Pharmacy;
import com.medical.healthcare.model.Prescription;
import com.medical.healthcare.model.User;
import com.medical.healthcare.repository.PharmacyRepository;
import com.medical.healthcare.service.DrugService;
import com.medical.healthcare.service.PrescriptionService;
import com.medical.healthcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping()
public class PatientController {
    @Autowired
    PharmacyRepository pharmacyRepository;

    private final UserService userService;
    private final PrescriptionService prescriptionService;
    private final DrugService drugService;

    public PatientController(UserService userService, PrescriptionService prescriptionService, DrugService drugService) {
        this.userService = userService;
        this.prescriptionService = prescriptionService;
        this.drugService = drugService;
    }

    @GetMapping("/patient")
    public String patient(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<Prescription> prescriptionList = prescriptionService.getAllPrescriptions();
        List<Prescription> prescriptionList1=new ArrayList<>();
        for (Prescription p: prescriptionList ) {
            if(p.getUser().getIdentificationNumber().equals(authentication.getName())){
                prescriptionList1.add(p);
            }
        }
        model.addAttribute("prescriptions", prescriptionList1);

        User user=userService.findByIdentificationNumber(authentication.getName());
        model.addAttribute("user",user);

        Drug drug =new Drug();
        model.addAttribute("drug",drug);

        List<Drug> drugList = drugService.getAllDrugs();
        model.addAttribute("drugs", drugList);

        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        model.addAttribute("pharmacies", pharmacyList);
        return "patient";
    }



}
