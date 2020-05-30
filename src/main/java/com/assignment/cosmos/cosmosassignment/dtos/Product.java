package com.assignment.cosmos.cosmosassignment.dtos;

public class Product {

    private String id;
    private String productId;
    private String productName;
    private String manufacturer;
    private Integer numberOfViews;
    private Integer numberOfAnsweredQuestions;
    private String averageReviewRating;
    private String categorySubcategory;

    public Product() {
    }

    public Product(String productId, String productName, String manufacturer, Integer numberOfViews, Integer numberOfAnsweredQuestions, String averageReviewRating, String categorySubcategory) {
        this.id = productId;
        this.productId = productId;
        this.productName = productName;
        this.manufacturer = manufacturer;
        this.numberOfViews = numberOfViews;
        this.numberOfAnsweredQuestions = numberOfAnsweredQuestions;
        this.averageReviewRating = averageReviewRating;
        this.categorySubcategory = categorySubcategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(Integer numberOfViews) {
        this.numberOfViews = numberOfViews;
    }

    public Integer getNumberOfAnsweredQuestions() {
        return numberOfAnsweredQuestions;
    }

    public void setNumberOfAnsweredQuestions(Integer numberOfAnsweredQuestions) {
        this.numberOfAnsweredQuestions = numberOfAnsweredQuestions;
    }

    public String getAverageReviewRating() {
        return averageReviewRating;
    }

    public void setAverageReviewRating(String averageReviewRating) {
        this.averageReviewRating = averageReviewRating;
    }

    public String getCategorySubcategory() {
        return categorySubcategory;
    }

    public void setCategorySubcategory(String categorySubcategory) {
        this.categorySubcategory = categorySubcategory;
    }
}
