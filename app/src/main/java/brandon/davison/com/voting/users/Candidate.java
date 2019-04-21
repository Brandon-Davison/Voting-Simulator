package brandon.davison.com.voting.users;

import java.util.ArrayList;

public class Candidate {

    private String name;
    private int id;
    private int votesNeeded, votesReceived;
    private boolean hasWon;
    private ArrayList<Voter> voters;

    public Candidate(String name, int id, int votesNeeded) {
        this.name = name;
        this.id = id;
        this.votesNeeded = votesNeeded;
        votesReceived = 0;
        hasWon = false;
        voters = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getid() {
        return id;
    }

    public int getVotesNeeded() {
        return votesNeeded;
    }

    public int getVotesReceived() {
        return votesReceived;
    }

    public boolean hasWon() {
        return hasWon;
    }

    public ArrayList<Voter> getVoters() {
        return voters;
    }

    // Adds vote and returns true if candidate won election
    public boolean addVote(Voter voter) {
        voters.add(voter);
        votesReceived++;

        if (votesReceived == votesNeeded) {
            hasWon = true;
            return true;
        }
        return false;
    }

}
