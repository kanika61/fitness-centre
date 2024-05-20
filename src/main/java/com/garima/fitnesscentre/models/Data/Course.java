package com.garima.fitnesscentre.models.Data;

public class Course {
    private int id;
    private String name;
    private String description;
    private String slug;
    private String image;
    private String price;
    private String categoryId;
    private String startDate;
    private String endDate;
    private String trainerId;
    private int duration;

    public Course() {}

    public Course(int id, String name, String description, String slug, String image, String price,
                 String categoryId, String startDate, String endDate, String trainerId, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.image = image;
        this.price = price;
        this.categoryId = categoryId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.trainerId = trainerId;
        this.duration = duration;
    }

    // Getters and Setters for each attribute

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
