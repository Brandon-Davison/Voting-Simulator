package brandon.davison.com.voting.users;

public class Voter {

    private int id;
    private String name;
    private boolean hasVoted;

    public Voter(int id, String name) {
        this.id = id;
        this.name = name;
        hasVoted = false;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public void setVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

}
