package brandon.davison.com.voting.users;

import java.util.UUID;

public class Voter {

    private UUID id;
    private String name;
    private boolean hasVoted;

    public Voter(UUID id, String name) {
        this.id = id;
        this.name = name;
        hasVoted = false;
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
    }

}
