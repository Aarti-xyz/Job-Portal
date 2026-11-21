package com.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application,Integer>{

    boolean existsByUserEmailAndJobTitle(String userEmail,String jobTitle);

    List<Application> findByUserEmail(String userEmail);

    long countByStatus(String status);

}