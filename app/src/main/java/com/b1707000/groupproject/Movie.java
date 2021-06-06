package com.b1707000.groupproject;

public class Movie {
    private String id;
    private String filmName;
    private String publishDate;
    private String studio;
    private String description;
    private String category;
    private int amount;
    private String imgURL;

    public Movie(){

    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getFilmName() {
        return filmName;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getStudio() {
        return studio;
    }
}
