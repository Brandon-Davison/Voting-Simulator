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

public class Database {

    private ArrayList<UUID> ids;
    private boolean temp; // used to get around returning value from inner classes

    public Database() {
        ids = new ArrayList<>();

        readSettingsFromDb();
    }

    public void readSettingsFromDb() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        DatabaseReference settingsRef = ref.child("settings");
        Log.d("SDFLSDFSsf", "read from db: " + settingsRef.getDatabase());

        settingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                Log.d("SDFLSDFSsf", "read from db: " + dataSnapshot.getValue());
                Log.d("SDFLSDFSsf", "read from db: " + dataSnapshot.child("votesAvailable").getValue());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Writes a VoterEntryModel to the database TODO: pull ID from generated UUIDs on db
    public void putVoter(String name) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        Voter voter = new Voter(UUID.randomUUID(), name);
        ref.child("voters").child(voter.getId().toString()).setValue(voter.getEntry());
    }

    // Generates ids for a new election
    public void generateIds(int numIds) {
        for (int i = 0; i < numIds; i++) {
            ids.add(UUID.randomUUID());
        }
    }

    public void putIds() {
        for (UUID id : ids) {
            // put ID into ID table
        }
    }

}
