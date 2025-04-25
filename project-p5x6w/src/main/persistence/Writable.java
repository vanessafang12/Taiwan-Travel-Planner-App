package persistence;

import org.json.JSONObject;

//serialized object into Json
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}