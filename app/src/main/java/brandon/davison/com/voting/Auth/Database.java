package brandon.davison.com.voting.Auth;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import brandon.davison.com.voting.users.Voter;
import brandon.davison.com.voting.users.VoterEntryModel;

public class Database {

    private ArrayList<UUID> ids;
    private FirebaseDatabase db;

    public Database() {
        ids = new ArrayList<>();
        db = FirebaseDatabase.getInstance();

        Voter newVoter = new Voter(UUID.randomUUID(), "brandon", false);

        VoterEntryModel entry2 = new VoterEntryModel(newVoter.getName(), newVoter.hasVoted());

        Map<String, Object> entry = new HashMap<>();
        entry.put("name", newVoter.getName());
        entry.put("hasVoted", newVoter.hasVoted());

        /* Write test */
        DatabaseReference ref = db.getReference();

        ref.child("voters").child(newVoter.getId().toString()).setValue(newVoter.getEntry());

        /* Read test */

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("Databasetest", "Read: " + dataSnapshot.getValue() + " from db");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Databasetest", "Failed to read from db");
            }
        });

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

    // gets Ids from database
    public void getIds() {

    }

}