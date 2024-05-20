package com.garima.fitnesscentre.models;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.garima.fitnesscentre.models.Data.Centre;
// import com.garima.fitnesscentre.models.data.Centre;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CentreRepository {

    @Autowired
    private DataSource dataSource;

    public Centre save(Centre centre) {
        try (Connection connection = dataSource.getConnection()) {
            String insertQueryString = "INSERT INTO centres (city, state, phone_number) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQueryString,
                    Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, centre.getCity());
                preparedStatement.setString(2, centre.getState());
                preparedStatement.setString(3, centre.getPhoneNumber());
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            centre.setId(generatedKeys.getInt(1));
                            return centre;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Centre> findAll() {
        List<Centre> centres = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "SELECT * FROM centres";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Centre centre = new Centre();
                        centre.setId(resultSet.getInt("id"));
                        centre.setCity(resultSet.getString("city"));
                        centre.setState(resultSet.getString("state"));
                        centre.setPhoneNumber(resultSet.getString("phone_number"));
                        centres.add(centre);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return centres;
    }

    public Centre findByCity(int id, String city) {
        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "SELECT * FROM centres WHERE id != ? and city = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, city);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Centre centre = new Centre();
                        centre.setId(resultSet.getInt("id"));
                        centre.setCity(resultSet.getString("city"));
                        centre.setState(resultSet.getString("state"));
                        centre.setPhoneNumber(resultSet.getString("phone_number"));
                        return centre;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Centre getReferenceById(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "SELECT * FROM centres WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Centre centre = new Centre();
                        centre.setId(resultSet.getInt("id"));
                        centre.setCity(resultSet.getString("city"));
                        centre.setState(resultSet.getString("state"));
                        centre.setPhoneNumber(resultSet.getString("phone_number"));      
                        return centre;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteById(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String deleteQuery = "DELETE FROM centres WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, id);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}