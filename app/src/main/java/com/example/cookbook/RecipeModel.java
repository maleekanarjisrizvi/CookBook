package com.example.cookbook;

public class RecipeModel {

    String isFav;
    String category;
    String Title;
    String Ingredients;
    String Recipe;
    String imageUrl;

    public RecipeModel() {
    }

    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
    }

    public RecipeModel(String isFav, String category, String title, String ingredients, String recipe, String imageUrl) {
        this.isFav = isFav;
        this.category = category;
        Title = title;
        Ingredients = ingredients;
        Recipe = recipe;
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        category = category;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public String getRecipe() {
        return Recipe;
    }

    public void setRecipe(String recipe) {
        Recipe = recipe;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
