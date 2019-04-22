package brandon.davison.com.voting.users;

import java.util.Map;
import java.util.UUID;

public class Voter {

    private UUID id;
    private String name;
    private boolean hasVoted;

    // Used for database put and get calls
    private VoterEntryModel model;

    public Voter(UUID id, String name) {
        this.id = id;
        this.name = name;
        hasVoted = false;

        model = new VoterEntryModel(name, false);
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public void setVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
        model.updateHasVoted(hasVoted);
    }

    public Map<String, Object> getEntry() {
        return model.getEntry();
    }

}
