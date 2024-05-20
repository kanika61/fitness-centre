package com.garima.fitnesscentre.models;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.garima.fitnesscentre.models.Data.Page;

import java.sql.*;

@Repository
public class PageRepository {
    @Autowired
    private DataSource dataSource;

    public List<Page> findAllByOrderBySortingAsc() {
        List<Page> pages = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "SELECT * FROM pages ORDER BY sorting ASC";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Page page = new Page();
                        page.setId(resultSet.getInt("id"));
                        page.setTitle(resultSet.getString("title"));
                        page.setSlug(resultSet.getString("slug"));
                        page.setContent(resultSet.getString("content"));
                        page.setSorting(resultSet.getInt("sorting"));
                        pages.add(page);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pages;
    }

    public Page findBySlug(String slug) {
        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "SELECT * FROM pages WHERE slug = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, slug);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Page page = new Page();
                        page.setId(resultSet.getInt("id"));
                        page.setTitle(resultSet.getString("title"));
                        page.setSlug(resultSet.getString("slug"));
                        page.setContent(resultSet.getString("content"));
                        page.setSorting(resultSet.getInt("sorting"));
                        return page;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Page findBySlug(int id, String slug) {
        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "SELECT * FROM pages WHERE id != ? and slug = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, slug);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Page page = new Page();
                        page.setId(resultSet.getInt("id"));
                        page.setTitle(resultSet.getString("title"));
                        page.setSlug(resultSet.getString("slug"));
                        page.setContent(resultSet.getString("content"));
                        page.setSorting(resultSet.getInt("sorting"));
                        return page;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Page getReferenceById(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String selectQuery = "SELECT * FROM pages WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Page page = new Page();
                        page.setId(resultSet.getInt("id"));
                        page.setTitle(resultSet.getString("title"));
                        page.setSlug(resultSet.getString("slug"));
                        page.setContent(resultSet.getString("content"));
                        page.setSorting(resultSet.getInt("sorting"));
                        return page;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Page save(Page page) {
        try (Connection connection = dataSource.getConnection()) {
            String insertQueryString = "INSERT INTO pages (title, slug, content, sorting) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQueryString,
            Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, page.getTitle());
                preparedStatement.setString(2, page.getSlug());
                preparedStatement.setString(3, page.getContent());
                preparedStatement.setInt(4, page.getSorting());
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            page.setId(generatedKeys.getInt(1));
                            return page;
                        }
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
            String deleteQuery = "DELETE FROM pages WHERE id = ?";
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