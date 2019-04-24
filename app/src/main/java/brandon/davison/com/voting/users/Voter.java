package brandon.davison.com.voting.users;

import java.util.HashMap;
import java.util.Map;

public class Voter {

    private String id;
    private String name;
    private boolean hasVoted;
    private String password;

    // Used for database put and get calls
    private Map<String, Object> model;

    public Voter(String id, String name, String password) {
        model = new HashMap<>();

        this.id = id;
        this.name = name;
        hasVoted = false;
        this.password = password;

        // Update model
        model.put("id", id);
        model.put("name", name);
        model.put("password", password);
        model.put("hasVoted", false);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public void setVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
        model.put("hasVoted", hasVoted);
    }

    public void setPassword(String password) {
        this.password = password;
        model.put("password", password);
    }

    public Map<String, Object> getModel() {
        return model;
    }

}
