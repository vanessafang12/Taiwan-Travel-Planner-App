package persistence;

import model.Recommendation;
import model.Location;
import model.Rating;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//Several Number of codes are from the example provided, which will be notified before each method. 
//Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
//Represents a reader that reads recommendation and rating from JSON data stored in file
public class JsonReader {

    private String source;

    // Code from example 
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // Part of the code is from example 
    // EFFECTS: reads recommendation from file and returns it;
    // throws IOException if an error occurs reading data from this file
    public Recommendation read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRecommendation(jsonObject);
    }

    // Part of the code is from example
    // EFFECTS: reads rating from file and trturns it;
    // throws IOException if an error occurs reading data from this file 
    public Rating check() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRating(jsonObject);
    }

    // Code from example 
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        } 

        return contentBuilder.toString();
    }

    // Part of the code is from example 
    // EFFECTS: parses recommendation from JSON object and returns it if the key is correct and not null, else reset
    private Recommendation parseRecommendation(JSONObject jsonObject) {
        Recommendation r = new Recommendation();
        if (jsonObject.has("visitedLocations") && !jsonObject.isNull("visitedLocations")) {
            addVisitedLocations(r, jsonObject);
        } else {
            r.reset();
        }
        return r;
    }   

    // Part of the code is from example 
    // EFFECTS: parses rating from JSON object and returns it
    private Rating parseRating(JSONObject jsonObject) {
        Rating r = new Rating();
        r.changeRating(jsonObject.getInt("rating"));
        return r;
    }

    
    // MODIFIES: r
    // EFFECTS: parses locations from JSON object and adds only names to recommendation
    private void addVisitedLocations(Recommendation r, JSONObject jsonObject) {
        
        JSONArray jsonArray = jsonObject.getJSONArray("visitedLocations");

        for (int i = 0; i < jsonArray.length(); i++) {
            String locationName = jsonArray.getString(i);  
            r.visitedLocation(new Location(locationName, "", "", 0)); 
        }
    }
    
}
