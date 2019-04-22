package brandon.davison.com.voting.users;

import java.util.HashMap;
import java.util.Map;

/* Used to model voter data for get and put requests into the database. */
public class VoterEntryModel {

    private String name;
    private boolean hasVoted;
    private Map<String, Object> entry;

    public VoterEntryModel(String name, boolean hasVoted) {
        this.name = name;
        this.hasVoted = hasVoted;

        // create and update new Entry
        entry = new HashMap<>();
        entry.put("name", name);
        entry.put("hasVoted", hasVoted);
    }

    public Map<String, Object> getEntry() {
        return entry;
    }

    void updateHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
        entry.put("hasVoted", hasVoted);
    }

}
