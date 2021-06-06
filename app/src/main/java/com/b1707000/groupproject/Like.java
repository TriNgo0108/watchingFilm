package com.b1707000.groupproject;

import java.util.ArrayList;

public class Like {
    private ArrayList<String> filmIDs;

    public  Like(){

    }
    public Like(ArrayList<String> filmIDs){
        this.filmIDs = filmIDs;
    }


    public ArrayList<String> getFilmIDs() {
        return filmIDs;
    }
}
