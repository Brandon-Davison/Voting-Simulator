package brandon.davison.com.voting.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Candidate {

    private String name;
    private int votesReceived, id;
    private boolean hasWon;

    // Used for database put and get calls
    private HashMap<String, Object> model;

    public Candidate(String name, int id, boolean hasWon, int votesReceived) {
        this.name = name;
        this.id = id;
        this.votesReceived = votesReceived;
        this.hasWon = hasWon;
        model = new HashMap<>();

        // update model
        model.put("name", name);
        model.put("id", id);
        model.put("votesReceived", 0);
        model.put("hasWon", hasWon);
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getid() {
        return id;
    }

    public int getVotesReceived() {
        return votesReceived;
    }

    public boolean hasWon() {
        return hasWon;
    }

    // Adds vote and returns true if candidate won election
    public void addVote() {
        votesReceived++;
        model.put("votesReceived", votesReceived);
    }

    /* Setters */
    public void setName(String name) {
        this.name = name;
    }

    public void setVotesReceived(int votesReceived) {
        this.votesReceived = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public HashMap<String, Object> getModel() {
        return model;
    }

}
