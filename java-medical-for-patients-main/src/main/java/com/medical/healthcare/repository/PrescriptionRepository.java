package com.medical.healthcare.repository;

import com.medical.healthcare.model.Prescription;
import com.medical.healthcare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepositoryImplementation<Prescription,Long> {
    @Query("SELECT p FROM Prescription p JOIN p.user u WHERE p.date = :date")
    List<Prescription> searchPrescriptions(@Param("date") String date);
    @Transactional
    @Modifying
    @Query("DELETE FROM Prescription p WHERE p.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
//    @Query("select p from Prescription p where p.id = ?id")
//    Prescription findPrespectionById(@Param("id") Long id);



    @Modifying
    @Query("UPDATE Prescription p SET p.drug.id = :drugId, p.user.id = :userId WHERE p.id = :id")
    void updateDrugAndUserIdById(@Param("id") Long id, @Param("drugId") Long drugId, @Param("userId") Long userId);
}
