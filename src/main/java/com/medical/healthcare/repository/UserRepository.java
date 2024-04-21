package com.medical.healthcare.repository;

import com.medical.healthcare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	User findByEmail(String email);
	User findByIdentificationNumber(String identificationNumber);



}