package brandon.davison.com.voting.voting;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* Used to read and write VoteSettings to the database to start a new election */
public class VoteSettingsModel {

    private int votesAvailable, votesToWin;
    private Date[] dateRange;
    private Map<String, Object> entry;

    public VoteSettingsModel(int votesToWin, int votesAvailable, Date start, Date end) {
        this.votesToWin = votesToWin;
        this.votesAvailable = votesAvailable;
        dateRange[0] = start;
        dateRange[1] = end;

        // create and update new entry
        entry = new HashMap<>();
        entry.put("votesToWin", votesToWin);
        entry.put("votesAvailable", votesAvailable);
        // TODO: put date start and end
    }

    public static boolean startNewElection() {
        // TODO: query db to determine if a new election should start
        return false;
    }

    public Map<String, Object> getEntry() {
        return entry;
    }

}
