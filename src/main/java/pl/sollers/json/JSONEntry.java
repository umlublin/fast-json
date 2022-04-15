package pl.sollers.json;

public class JSONEntry {
    private String key;
    private JSONParser value;

    public JSONEntry(String key, JSONParser value) throws ParseException {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public JSONParser getValue() {
        return value;
    }

}
