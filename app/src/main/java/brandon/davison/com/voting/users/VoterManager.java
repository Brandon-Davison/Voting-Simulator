package brandon.davison.com.voting.users;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

import brandon.davison.com.voting.voting.VoteSettings;

public class VoterManager {

    private ArrayList<String> ids = new ArrayList<>();
    private ArrayList<Voter> voters = new ArrayList<>();

    // change to Singleton class to prevent reading in from database upon each new activity (if time permits)
    public VoterManager(VoteSettings settings) {
        if (!settings.getStarted()) { // start new election and generate login id keys
            generateIds(settings.getVotesAvailable());
        } else { // election is ongoing. Read in IDS from database
            readIds(settings.getVotesAvailable());
        }
    }

    // Returns if a Voter can register
    public boolean canRegister(String id) {
        for (Voter voter : voters) {
            if (voter.getId().equals(id) && voter.getPassword().equals("")) {
                return true;
            }
        }
        return false;
    }

    // Set name and password for associated id
    public void registerVoter(String id, String name, String password) {
        for (int i = 0; i < voters.size(); i++) {
            if (voters.get(i).getId().equals(id)) {
                voters.get(i).setPassword(password);
                voters.get(i).setName(name);
            }
        }
        updateVoterInDb(id);
    }

    // Updates voters data in db
    public void updateVoterInDb(String id) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("voters");

        for (int i = 0; i < voters.size(); i++) {
            if (voters.get(i).getId().equals(id)) {
                db.child(voters.get(i).getId()).updateChildren(voters.get(i).getModel());
            }
        }
    }

    public boolean validCredentials(String id, String password) {
        for (int i = 0; i < voters.size(); i++) {
            if (voters.get(i).getId().equals(id) && voters.get(i).getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Read in generated ids from database
    public void readIds(int numberIds) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference votersRef = db.child("voters");

        votersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot t: dataSnapshot.getChildren()) {
                    try {
                        Log.d("idTesting", t.getValue().toString());
                        String password =  t.child("password").getValue().toString();
                        String id = t.child("id").getValue().toString();
                        String hasVoted = t.child("hasVoted").getValue().toString();
                        String name = t.child("name").getValue().toString();

                        Voter newVoter = new Voter(id, name, password, Boolean.parseBoolean(hasVoted));
                        voters.add(newVoter);
                    } catch (Exception e) {
                        Log.d("idTesting", "NULL ERRORS");
                    }
                }
                Log.d("idTesting", dataSnapshot.getValue().toString());
                Log.d("idTesting", "Size: " + voters.size());
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
            Voter voter = new Voter(ids.get(i), "", "", false);
            db.child(ids.get(i)).setValue(voter.getModel());

            Log.d("idTesting", "Id " + (i + 1) + ": " + ids.get(i).toString());
        }
    }

    public Voter getVoter(String id) {
        for (Voter t : voters) {
            if (t.getId().substring(0, 5).equals(id)) {
                return t;
            }
        }
        return null;
    }

    public ArrayList<Voter> getVoters() {
        return voters;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

}
