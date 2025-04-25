package model;

// Represent the user requirement for a location with the city, type, and estimated spending 
public class Requirement {

    private String type; 
    private int spending;
    private String city;

    //EFFECTS: constructs an Requirement 
    public Requirement(String city, String type, int spending) {
        this.city = city;
        this.type = type;
        this.spending = spending;
    }

    //EFFECTS: This will return the require city 
    public String getCity() {
        return city;
    }
    
    //EFFECTS: This will return the require type of location
    public String getType() {
        return type;
    }
    
    //EFFECTS: This will return the require estimated spending per person
    public int getSpedning() {
        return spending;
    }

}
