package com.medical.healthcare.repository;

import com.medical.healthcare.model.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
    List<Drug> findByNameContaining(String query);
    Drug findByName(String name);
    @Modifying
    @Query("DELETE FROM Drug d WHERE d.id = :id")
    void deleteById(@Param("id") Long id);
}
