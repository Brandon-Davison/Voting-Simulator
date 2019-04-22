package brandon.davison.com.voting.users;

public class Admin {

    private String username;
    private int password;

    public Admin(String username, int password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public int getPassword() {
        return password;
    }

}
