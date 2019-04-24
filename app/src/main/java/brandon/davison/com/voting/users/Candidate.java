package brandon.davison.com.voting.users;

import java.util.ArrayList;
import java.util.Map;

public class Candidate {

    private String name;
    private int id;
    private int votesReceived;
    private boolean hasWon;

    // Used for database put and get calls
    private Map<String, Object> model;

    public Candidate(String name, int id) {
        this.name = name;
        this.id = id;
        votesReceived = 0;
        hasWon = false;

        // update model
        model.put("name", name);
        model.put("id", id);
        model.put("votesReceived", 0);
        model.put("hasWon", "false");
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
    public void addVote(Voter voter) {
        votesReceived++;
    }

}
