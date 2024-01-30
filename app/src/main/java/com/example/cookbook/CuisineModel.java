package com.example.cookbook;

public class CuisineModel {
    private String category;
    private String imageUrl;
    private String userId;

    public CuisineModel() {
    }

    public CuisineModel(String category, String imageUrl, String userId) {
        this.category = category;
        this.imageUrl = imageUrl;
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
