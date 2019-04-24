package brandon.davison.com.voting.users;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import brandon.davison.com.voting.voting.VoteSettings;

public class CandidateManager {

    private ArrayList<Candidate> candidates = new ArrayList<>();

    public CandidateManager(VoteSettings settings) {

    }

    public void readInCandidates() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("candidates");

    }

}
