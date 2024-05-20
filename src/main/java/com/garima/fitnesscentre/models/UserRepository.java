package com.garima.fitnesscentre.models;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garima.fitnesscentre.models.Data.User;


public interface UserRepository extends JpaRepository< User,Integer>{

    User  findByUsername(String username);
} 
