package com.b1707000.groupproject;

public class Post {
    private  String userID;
    private String filmID;
    private String content;
    public Post(){

    }
    public  Post(String userID, String filmID, String content){
        this.userID = userID;
        this.filmID = filmID;
        this.content = content;
    }

    public String getUserID() {
        return userID;
    }

    public String getContent() {
        return content;
    }

    public String getFilmID() {
        return filmID;
    }
}
