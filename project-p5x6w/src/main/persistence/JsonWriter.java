package persistence;

import model.Recommendation;
import model.Rating;
import org.json.JSONObject;

import java.io.*;

//Several Number of codes are from the example provided, which will be notified before each method. 
//Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a writer that writes JSON representation of recommendation and rating to file
public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    //Code from example
    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //Code from example
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of recommendation to file
    public void write(Recommendation r) {
        JSONObject json = r.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of rating to gile
    public void writeRating(Rating r) {
        JSONObject json = r.toJson();
        saveToFile(json.toString(TAB));
    }

    //Code from example
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }
    
    //Code from example
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
