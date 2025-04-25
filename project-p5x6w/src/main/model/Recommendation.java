package model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

import java.util.ArrayList;

// Provide the recommend location based on the requirements the user input, and put already visit location into a list 
public class Recommendation implements Writable {

    private List<String> listOfVisitedName; 
    private static List<Location> locationList = new ArrayList<>();

    //default location list 
    static {
        locationList.add(new Location("Taipei 101", "Taipei", "Landmark", 200));
        locationList.add(new Location("Raohe Night Market", "Taipei", "Night Market", 250));
        locationList.add(new Location("Taipei Dome", "Taipei", "Landmark", 0));
        locationList.add(new Location("Lungshan Temple", "Taipei", "Historical Landmark", 0));
        locationList.add(new Location("228 Peace Memorial Park", "Taipei", "Park", 0));
        locationList.add(new Location("National Taiwan University", "Taipei", "Landmark", 0));
        locationList.add(new Location("Taipei Zoo", "Taipei", "Landmark", 50));
        locationList.add(new Location("Maokong", "Taipei", "Landmark", 200));
        locationList.add(new Location("Taipei Main Station", "Taipei", "Historical Landmark", 0));
        locationList.add(new Location("Taipei City Mall", "Taipei", "Shopping Center", 0));
        locationList.add(new Location("Xinyi District", "Taipei", "Shopping Center", 0)); 
        locationList.add(new Location("Taipei Music Center", "Taipei", "Museum", 350));
        locationList.add(new Location("Shalun Beach", "NewTaipei", "Beach", 0));
        locationList.add(new Location("Jiufen Old Street", "NewTaipei", "Historical Landmark", 0));
        locationList.add(new Location("National Palace Museum", "Taipei", "Museum", 250));
        locationList.add(new Location("Baisha Beach", "NewTaipei", "Beach", 0));
        locationList.add(new Location("Ju Ming Museum", "NewTaipei", "Museum", 250));
        locationList.add(new Location("Yingge Historic Ceramics Street", "NewTaipei", "Historical Landmark", 0));
        locationList.add(new Location("Lehua Night Market", "NewTaipei", "Night Market", 250));
        locationList.add(new Location("Daan Park", "Taipei", "Park", 0));
        locationList.add(new Location("Zhongshan District", "Taipei", "Shopping Center", 0));
        locationList.add(new Location("Dihua Street", "Taipei", "Historical Landmark", 0));
        locationList.add(new Location("Grand Hotel Taipei", "Taipei", "Historical Landmark", 100));
        locationList.add(new Location("Beitou Hot Spring Museum", "Taipei", "Museum", 250));
        locationList.add(new Location("Xingtian Temple", "Taipei", "Historical Landmark", 0));
    }

    //EFFECTS: constructs an new recommendation, and a new listOfVisitedName
    public Recommendation() {
        listOfVisitedName = new ArrayList<>();
    } 

    //Provide the user with a recommended location that satisfied all requirements 
    //REQUIRES: locationList.size > 0, estimated spending >= 0
    //EFFECTS: Check through the locationList to see which location meet the requirement the user input
    //including the city, type of location, and =< estimated spending, return the name of the first location that 
    //matches this requirement and check if it's in the already visited list, return null if no such location is find 
    public String checkLocation(Requirement requirement) {
        for (Location l : locationList) {
            if (l.getCity().equals(requirement.getCity()) && !listOfVisitedName.contains(l.getName())) {
                if (l.getType().equals(requirement.getType())) {
                    if (l.getSpending() <= requirement.getSpedning()) {
                        visitedLocation(l);
                        return l.getName();
                    }  
                } 
            }
        }
        return null; 
    }

    //MODIFIES: THIS
    //EFFECTS: Add the location to already visited list if the name of this location 
    //is not yet in the list
    public void visitedLocation(Location location) {
        if (!listOfVisitedName.contains(location.getName())) {
            listOfVisitedName.add(location.getName());
        }
    }

    //EFFECTS: return the list of location name the user already visited, return null is the list is empty
    public List<String> getAlreadyList() {
        if (listOfVisitedName.isEmpty()) {
            return null;
        } else {
            return listOfVisitedName;
        }
    } 

    //EFFECTS: Reset the already visit list 
    public void reset() {
        listOfVisitedName = new ArrayList<>();
    }

    //EFFECTS: returns this object as a JSONObject with a "location" key, getting
    //each location from getAlreadyList
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        for (String location : listOfVisitedName) {  
            jsonArray.put(location);
        }
        
        json.put("visitedLocations", jsonArray);  
        return json;
    }
}

