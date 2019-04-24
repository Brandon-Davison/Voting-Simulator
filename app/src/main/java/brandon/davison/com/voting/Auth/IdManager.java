package brandon.davison.com.voting.Auth;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

import brandon.davison.com.voting.users.Voter;
import brandon.davison.com.voting.voting.VoteSettings;

public class IdManager {

    private ArrayList<String> ids = new ArrayList<>();

    private MyIdValueEventListener listener;

    public IdManager() {
        listener = new MyIdValueEventListener();
    }

    public IdManager(VoteSettings settings) {
        Log.d("idTesting", "Is started: " + settings.getStarted());

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
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference votersRef = db.child("voters");

        votersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot t: dataSnapshot.getChildren()) {
                    //Log.d("idTesting", t.toString());
                    Log.d("idTesting", t.getValue().toString());
                    String password =  t.child("password").getValue().toString();
                    String id = t.child("id").getValue().toString();
                    String hasVoted = t.child("hasVoted").getValue().toString();
                    String name = t.child("name").getValue().toString();
                }
                Log.d("idTesting", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
