package com.garima.fitnesscentre.models;


import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.garima.fitnesscentre.models.Data.Trainer;


@Repository
public class TrainerRepository {
    private final JdbcTemplate jdbcTemplate;

    public TrainerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Trainer save(Trainer trainer) {
        String insertSql = "INSERT INTO trainers (name, gender, city, state, phone_number, centre_id) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertSql, trainer.getName(), trainer.getGender(), trainer.getCity(), trainer.getState(), trainer.getPhoneNumber(), trainer.getCentreId());
    
        // Retrieve the generated ID from the database
        Integer generatedId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    
        if (generatedId != null) {
            trainer.setId(generatedId);
        } else {
            // Handle the case where the ID could not be retrieved
            // You can throw an exception, log an error, or handle it as needed.
            // For example:
            throw new RuntimeException("Failed to retrieve generated ID for Trainer");
        }
    
        return trainer;
    }
    
    public List<Trainer> findAll() {
        String selectAllSql = "SELECT * FROM trainers";
        return jdbcTemplate.query(selectAllSql, (rs, rowNum) -> new Trainer(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("gender"),
            rs.getString("city"),
            rs.getString("state"),
            rs.getString("phone_number"),
            rs.getString("centre_id")
        ));
    }

    public Trainer findById(int id) {
        String selectByIdSql = "SELECT * FROM trainers WHERE id = ?";
        return jdbcTemplate.queryForObject(selectByIdSql, (rs, rowNum) -> new Trainer(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("gender"),
            rs.getString("city"),
            rs.getString("state"),
            rs.getString("phone_number"),
            rs.getString("centre_id")
        ), id);
    }

    public void deleteById(int id) {
        String deleteSql = "DELETE FROM trainers WHERE id = ?";
        jdbcTemplate.update(deleteSql, id);
    }

    public Trainer update(Trainer trainer) {
        String updateSql = "UPDATE trainers SET name = ?, gender = ?, city = ?, state = ?, phone_number = ?, centre_id = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, trainer.getName(), trainer.getGender(), trainer.getCity(), trainer.getState(), trainer.getPhoneNumber(), trainer.getCentreId(), trainer.getId());
        return trainer;
    }
    public Trainer getReferenceById(int id) {
        String sql = "SELECT * FROM trainers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Trainer trainer = new Trainer();
            trainer.setId(rs.getInt("id"));
            trainer.setName(rs.getString("name"));
            // Add more properties as needed
            return trainer;
        }, id);
    }
    

}