package brandon.davison.com.voting;

import java.util.ArrayList;
import java.util.Date;

import brandon.davison.com.voting.users.Candidate;

public class VoteSettings {

    private ArrayList<Candidate> candidates;
    private int votesAvailable, votesToWin;
    private Date[] dateRange;

    public VoteSettings(int votesToWin, int votesAvailable, Date start, Date end) {
        this.votesToWin = votesToWin;
        this.votesAvailable = votesAvailable;
        candidates = new ArrayList<>();

        dateRange = new Date[2];
        dateRange[0] = start;
        dateRange[1] = end;
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

    public Date[] getDateRange() {
        return dateRange;
    }
}
