// package com.garima.fitnesscentre.models;

// import org.springframework.data.jpa.repository.JpaRepository;

// import com.garima.fitnesscentre.models.Data.Category;

// public interface CategoryRepository extends JpaRepository<Category,Integer>
// {
//     Category findByName(String name);

//    Category findBySlug(String slug);

      
    
// }
package com.garima.fitnesscentre.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.garima.fitnesscentre.models.Data.Category;



@Repository
public class CategoryRepository {
    @Autowired
    private DataSource dataSource;

    public Category findByName(String name) {
        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "SELECT * FROM categories WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, name);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Category category = new Category();
                        category.setId(resultSet.getInt("id"));
                        category.setName(resultSet.getString("name"));
                        category.setSlug(resultSet.getString("slug"));
                        return category;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Category findBySlug(String slug) {
        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "SELECT * FROM categories WHERE slug = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, slug);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Category category = new Category();
                        category.setId(resultSet.getInt("id"));
                        category.setName(resultSet.getString("name"));
                        category.setSlug(resultSet.getString("slug"));
                        return category;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Category> findAll() {
    List<Category> categories = new ArrayList<>();
    try (Connection connection = dataSource.getConnection()) {
        String selectQuery = "SELECT * FROM categories";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Category category = new Category();
                    category.setId(resultSet.getInt("id"));
                    category.setName(resultSet.getString("name"));
                    category.setSlug(resultSet.getString("slug"));
                    categories.add(category);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return categories;
}
public boolean save(Category category) {
    try (Connection connection = dataSource.getConnection()) {
        String insertQuery = "INSERT INTO categories (name, slug) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getSlug());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public Category getReferenceById(int id) {
    try (Connection connection = dataSource.getConnection()) {
        String selectQuery = "SELECT * FROM categories WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Category category = new Category();
                    category.setId(resultSet.getInt("id"));
                    category.setName(resultSet.getString("name"));
                    category.setSlug(resultSet.getString("slug"));
                    return category;
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
        String deleteQuery = "DELETE FROM categories WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }

    // You can add more repository methods for Category as needed.
}
}
