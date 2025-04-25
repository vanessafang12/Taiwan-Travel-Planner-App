package model;

// Represent a location with name, city, type, and estimated spending 
public class Location {

    private String city;
    private String type; 
    private int spending;
    private String name;

    //EFFECTS: constructs an Location
    public Location(String name, String city, String type, int spending) {
        this.name = name;
        this.city = city;
        this.type = type;
        this.spending = spending;
    }

    //EFFECTS: This will return the city for the location
    public String getCity() {
        return city;
    }

    //EFFECTS: This will return the type of location
    public String getType() {
        return type; 
    }

    //EFFECTS: This will return the estimated spending per person
    public int getSpending() {
        return spending;
    }

    //EFFECTS: This will return the name of the location 
    public String getName() {
        return name;
    }
}
