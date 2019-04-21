package brandon.davison.com.voting;

import java.util.ArrayList;

import brandon.davison.com.voting.users.Candidate;

public class VoteSettings {

    private ArrayList<Candidate> candidates;
    private int votesAvailable, votesToWin;

    public VoteSettings(int votesToWin, int votesAvailable) {
        this.votesToWin = votesToWin;
        this.votesAvailable = votesAvailable;
        candidates = new ArrayList<>();
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public int getVotesAvailable() {
        return votesAvailable;
    }

    public int getVotesToWin() {
        return votesToWin;
    }

}
