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
import com.medical.healthcare.service.DrugServiceImpl;
import com.medical.healthcare.service.PrescriptionService;
import com.medical.healthcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    public PrescriptionRepository prescriptionRepository;

    @Autowired
    public PharmacyRepository pharmacyRepository;
    @Autowired
    public DrugRepository drugRepository;
    private final UserService userService;
    private final PrescriptionService prescriptionService;
    private final DrugService drugService;

    public DoctorController(UserService userService, PrescriptionService prescriptionService, DrugService drugService) {
        this.userService = userService;
        this.prescriptionService = prescriptionService;
        this.drugService = drugService;
    }
    @GetMapping("/doctor")
    public String showUserList(Model model) {
        List<User> userList = userService.getAllUsers();
        model.addAttribute("users", userList);

        PrescriptionDto prescriptionDto = new PrescriptionDto();
        model.addAttribute("prescriptionDto", prescriptionDto);



        Drug drug = new Drug(); // Add this line to create the "drug" object
        drug.setPharmacy(new Pharmacy());
        model.addAttribute("drug", drug); // Add the "drug" object to the model

        List<Drug> drugList = drugService.getAllDrugs();
        model.addAttribute("drugs", drugList);

        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        model.addAttribute("pharmacies", pharmacyList);

        List<Prescription> prescriptionList = prescriptionService.getAllPrescriptions();
        model.addAttribute("prescriptions", prescriptionList);


        return "doctor";
    }

    @GetMapping("/doctor/drug")
    public String showDrug(Model model) {
        Drug drug = new Drug();
        drug.setPharmacy(new Pharmacy());
        model.addAttribute("drug", drug); // Add the "drug" object to the model

        List<Drug> drugList = drugService.getAllDrugs();
        model.addAttribute("drugs", drugList);

        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        model.addAttribute("pharmacies", pharmacyList);

        return "doctor";
    }

    @PostMapping("/doctor/drug")
    public String createDrug(@ModelAttribute("drug") Drug drug, BindingResult result, Model model) {

        Pharmacy pharmacy = drug.getPharmacy();
        pharmacyRepository.save(pharmacy);

        drug.setPharmacy(pharmacy);
        drugRepository.save(drug);

        // Add the updated "drug" object back to the model
        model.addAttribute("drug", drug);

        return "redirect:/doctor";
    }




    @PostMapping("/doctor/delete/user/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        // Delete related prescriptions
        prescriptionRepository.deleteByUserId(id);

        // Delete the user
        userService.deleteById(id);

        // Redirect the user to the item list page or any other page
        return "redirect:/doctor";
    }
    @PostMapping("/doctor/drug/delete/{id}")
    public String deleteDrug(@PathVariable("id") Long id) {

        Drug drug=drugService.getDrugById(id);
        drugRepository.deleteById(id);
        pharmacyRepository.deleteById(drug.getPharmacy().getId());

        return "redirect:/doctor";
    }


    @GetMapping("/doctor/drug/delete/")
    public String deleteDrug(Model model){
        Drug drug = new Drug(); // Add this line to create the "drug" object
        drug.setPharmacy(new Pharmacy());
        model.addAttribute("drug", drug); // Add the "drug" object to the model

        List<Drug> drugList = drugService.getAllDrugs();
        model.addAttribute("drugs", drugList);

        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        model.addAttribute("pharmacies", pharmacyList);
        return "doctor";
    }


    @PostMapping("/doctor/delete/prescription/delete/{id}")
    public String deletePrescription(@PathVariable("id") Long id) {
//        prescriptionRepository.deleteByUserId(id);
        prescriptionService.deletePrescription(id);
        return "redirect:/doctor"; // Redirect to the pharmacist page after deletion
    }

    @PostMapping("/doctor")
    public String createPrescriptionNew( @ModelAttribute("prescriptionDto")PrescriptionDto prescriptionDto,
                                      BindingResult result
    ){
        User existingUser= userService.findByIdentificationNumber(prescriptionDto.getIdentificationNumber());
        Drug existingDrug= drugService.getDrugByName(prescriptionDto.getDrugName());
        if(existingUser!=null&& existingDrug!=null){
            Prescription prescription=new Prescription();
            prescription.setUser(existingUser);
            prescription.setDate(new Date());
            prescription.setDrug(existingDrug);
            prescriptionService.createPrescription(prescription);
        }
        return "redirect:/doctor";
    }

    //update prescription


    ///create-prescription

    @GetMapping("/doctor/create-prescription")
    public String showCreatePrescriptionForm(Model model) {
        PrescriptionDto prescriptionDto = new PrescriptionDto();
        model.addAttribute("prescriptionDto", prescriptionDto);
        return "create-prescription";
    }

    @PostMapping("/doctor/create-prescription")
    public String createPrescription( @ModelAttribute("prescriptionDto")PrescriptionDto prescriptionDto,
                                      BindingResult result
            ){
          User existingUser= userService.findByIdentificationNumber(prescriptionDto.getIdentificationNumber());
          Drug existingDrug= drugService.getDrugByName(prescriptionDto.getDrugName());

          if(existingUser!=null&& existingDrug!=null){
              Prescription prescription=new Prescription();
              prescription.setUser(existingUser);
              prescription.setDate(prescriptionDto.getDate());
              prescription.setDrug(existingDrug);
              prescriptionService.createPrescription(prescription);

        }

              return "create-prescription";

    }




}
