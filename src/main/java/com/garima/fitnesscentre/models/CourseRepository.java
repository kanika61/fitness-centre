  package com.garima.fitnesscentre.models;
// package com.garima.fitnesscentre.models;



// import java.util.List;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;

// import com.garima.fitnesscentre.models.data.Course;


// public interface CourseRepository extends JpaRepository<Course, Integer>{
    
//     @Query("SELECT p FROM Course p WHERE p.id != :id and p.slug = :slug and p.startDate = :startDate and p.duration = :duration")
//     Course findBySlug(int id, String slug, String startDate, int duration);

//     Page<Course> findAll(Pageable pageable);

//     List<Course> findAllByCategoryId(String categoryId, Pageable pageable);

//     long countByCategoryId(String categoryId);
//}

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.garima.fitnesscentre.models.Data.Course;



@Repository
public class CourseRepository {
    @Autowired
    private DataSource dataSource;

    public Course findBySlug(int id, String slug, String startDate, int duration) {
        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "SELECT * FROM courses WHERE id != ? AND slug = ? AND start_date = ? AND duration = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, slug);
                preparedStatement.setString(3, startDate);
                preparedStatement.setInt(4, duration);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Course course = new Course();
                        course.setId(resultSet.getInt("id"));
                        course.setName(resultSet.getString("name"));
                        course.setDescription(resultSet.getString("description"));
                        course.setSlug(resultSet.getString("slug"));
                        course.setImage(resultSet.getString("image"));
                        course.setPrice(resultSet.getString("price"));
                        course.setCategoryId(resultSet.getString("category_id"));
                        course.setStartDate(resultSet.getString("start_date"));
                        course.setEndDate(resultSet.getString("end_date"));
                        course.setTrainerId(resultSet.getString("trainer_id"));
                        course.setDuration(resultSet.getInt("duration"));
                        return course;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "SELECT * FROM courses";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Course course = new Course();
                        course.setId(resultSet.getInt("id"));
                        course.setName(resultSet.getString("name"));
                        course.setDescription(resultSet.getString("description"));
                        course.setSlug(resultSet.getString("slug"));
                        course.setImage(resultSet.getString("image"));
                        course.setPrice(resultSet.getString("price"));
                        course.setCategoryId(resultSet.getString("category_id"));
                        course.setStartDate(resultSet.getString("start_date"));
                        course.setEndDate(resultSet.getString("end_date"));
                        course.setTrainerId(resultSet.getString("trainer_id"));
                        course.setDuration(resultSet.getInt("duration"));
                        courses.add(course);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    public boolean save(Course course) {
      try (Connection connection = dataSource.getConnection()) {
          String insertQuery = "INSERT INTO courses (name, description, slug, image, price, category_id, start_date, end_date, trainer_id, duration) " +
                              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
          try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
              preparedStatement.setString(1, course.getName());
              preparedStatement.setString(2, course.getDescription());
              preparedStatement.setString(3, course.getSlug());
              preparedStatement.setString(4, course.getImage());
              preparedStatement.setString(5, course.getPrice());
              preparedStatement.setString(6, course.getCategoryId());
              preparedStatement.setString(7, course.getStartDate());
              preparedStatement.setString(8, course.getEndDate());
              preparedStatement.setString(9, course.getTrainerId());
              preparedStatement.setInt(10, course.getDuration());
  
              int rowsAffected = preparedStatement.executeUpdate();
              return rowsAffected > 0;
          }
      } catch (SQLException e) {
          e.printStackTrace();
          return false;
      }
  }

    public Long count() {
        return null;
    }
    public Course getReferenceById(int id) {
      try (Connection connection = dataSource.getConnection()) {
          String selectQuery = "SELECT * FROM courses WHERE id = ?";
          try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
              preparedStatement.setInt(1, id);
              try (ResultSet resultSet = preparedStatement.executeQuery()) {
                  if (resultSet.next()) {
                      Course course = new Course();
                      course.setId(resultSet.getInt("id"));
                      course.setName(resultSet.getString("name"));
                      course.setDescription(resultSet.getString("description"));
                      course.setSlug(resultSet.getString("slug"));
                      course.setImage(resultSet.getString("image"));
                      course.setPrice(resultSet.getString("price"));
                        course.setCategoryId(resultSet.getString("category_id"));
                        course.setStartDate(resultSet.getString("start_date"));
                        course.setEndDate(resultSet.getString("end_date"));
                        course.setTrainerId(resultSet.getString("trainer_id"));
                         course.setDuration(resultSet.getInt("duration"));
                        
                      return course;
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
        String deleteQuery = "DELETE FROM courses WHERE id = ?";
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
public List<Course> findAllByCategoryId(String categoryId, Pageable pageable) {
    List<Course> courses = new ArrayList<>();
    try (Connection connection = dataSource.getConnection()) {
        String selectQuery = "SELECT * FROM courses WHERE category_id = ? LIMIT ? OFFSET ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, categoryId);
            preparedStatement.setInt(2, pageable.getPageSize());
            preparedStatement.setInt(3, pageable.getPageNumber() * pageable.getPageSize());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setId(resultSet.getInt("id"));
                    course.setName(resultSet.getString("name"));
                    course.setDescription(resultSet.getString("description"));
                    course.setSlug(resultSet.getString("slug"));
                    course.setImage(resultSet.getString("image"));
                    course.setPrice(resultSet.getString("price"));
                    course.setCategoryId(resultSet.getString("category_id"));
                    course.setStartDate(resultSet.getString("start_date"));
                    course.setEndDate(resultSet.getString("end_date"));
                    course.setTrainerId(resultSet.getString("trainer_id"));
                    course.setDuration(resultSet.getInt("duration"));
                    courses.add(course);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return courses;
}
public long countByCategoryId(String categoryId) {
    try (Connection connection = dataSource.getConnection()) {
        String countQuery = "SELECT COUNT(*) FROM courses WHERE category_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(countQuery)) {
            preparedStatement.setString(1, categoryId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0; // Return 0 if an error occurs or no courses are found.
}
public List<Course> findAll(Pageable pageable) {
    List<Course> courses = new ArrayList<>();
    try (Connection connection = dataSource.getConnection()) {
        String selectQuery = "SELECT * FROM courses LIMIT ? OFFSET ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, pageable.getPageSize());
            preparedStatement.setInt(2, pageable.getPageNumber() * pageable.getPageSize());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Course course = new Course();
                    course.setId(resultSet.getInt("id"));
                    course.setName(resultSet.getString("name"));
                    course.setDescription(resultSet.getString("description"));
                    course.setSlug(resultSet.getString("slug"));
                    course.setImage(resultSet.getString("image"));
                    course.setPrice(resultSet.getString("price"));
                    course.setCategoryId(resultSet.getString("category_id"));
                    course.setStartDate(resultSet.getString("start_date"));
                    course.setEndDate(resultSet.getString("end_date"));
                    course.setTrainerId(resultSet.getString("trainer_id"));
                    course.setDuration(resultSet.getInt("duration"));
                    courses.add(course);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return courses;
}



    
  

    // Add other repository methods as needed.
}
