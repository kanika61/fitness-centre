package com.garima.fitnesscentre.models;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garima.fitnesscentre.models.Data.Admin;


public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Admin  findByUsername(String username);
}
