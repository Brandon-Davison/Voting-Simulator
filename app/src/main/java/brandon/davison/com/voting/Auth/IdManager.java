package brandon.davison.com.voting.Auth;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

import brandon.davison.com.voting.users.Voter;
import brandon.davison.com.voting.voting.VoteSettings;

public class IdManager {

    private ArrayList<String> ids = new ArrayList<>();

    public IdManager(VoteSettings settings) {
        if (!settings.getStarted()) { // start new election and generate login id keys
            generateIds(settings.getVotesAvailable());
        } else { // election is ongoing. Read in IDS from database
            readIds(settings.getVotesAvailable());
        }
    }

    // Returns if an ID is in the list of generated IDS
    public boolean isValidID(String id) {
        if (ids.size() == 0) return false;

        return ids.contains(id);
    }

    // Read in generated ids from database
    public void readIds(int numberIds) {
        Log.d("idTesting", "Reading IDS... ");
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("voters");



    }

    // Generate login id keys and save them to database
    private void generateIds(int numberIds) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("voters");

        for (int i = 0; i < numberIds; i++) {
            ids.add(UUID.randomUUID().toString());
            Voter voter = new Voter(ids.get(i), "", "");
            db.child(ids.get(i)).setValue(voter.getModel());

            Log.d("idTesting", "Id " + (i +1) + ": " + ids.get(i).toString());
        }
    }

}
