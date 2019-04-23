package brandon.davison.com.voting.Auth;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

import brandon.davison.com.voting.voting.VoteSettings;

public class IdManager {

    private ArrayList<UUID> ids = new ArrayList<>();

    public IdManager(VoteSettings settings) {
        if (!settings.getStarted()) { // start new election and generate login id keys
            generateIds(settings.getVotesAvailable());
        } else { // election is ongoing. Read in IDS from database

        }
    }

    // Returns if an ID is in the list of generated IDS
    public boolean isValidID(UUID id) {
        if (ids.size() == 0) return false;

        return ids.contains(id);
    }

    // Read in generated ids from database
    public void readIds(int numberIds) {

    }

    // Generate login id keys
    private void generateIds(int numberIds) {
        for (int i = 0; i < numberIds; i++) {
            ids.add(UUID.randomUUID());
            Log.d("idTesting", "Id " + (i +1) + ": " + ids.get(i).toString());
        }
        writeIds();
    }

    // Write generated IDS to database
    private void writeIds() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("ids");

        for (int i = 1; i <= ids.size(); i++) {
            db.child(String.valueOf(i)).setValue(ids.get(i));
        }
    }

}
