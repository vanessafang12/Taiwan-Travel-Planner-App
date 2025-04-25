package model;

import org.json.JSONObject;

import persistence.Writable;

//The system that allow the user to rate the app after visiting the location  
public class Rating implements Writable {

    private int rating;
    private int count;

    //EFFECTS: constructs an new Rating
    public Rating() {
        rating = 0;
        count = 0;
    }

    //REQUIRES: num should be in between [1,10]
    //MODIFIES: This
    //EFFECTS: Return the average rating among all users
    public void changeRating(int num) {
        count++;
        rating = (getRating() + num) / count;
    }

    //EFFECTS: Return the current rating
    public int getRating() {
        return rating;
    }

    //EFFECTS: Return this object as a JSONObject with a "Rating" key
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("rating", rating);
        return json;
    }

}
