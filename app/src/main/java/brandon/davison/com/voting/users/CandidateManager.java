package brandon.davison.com.voting.users;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import brandon.davison.com.voting.voting.VoteSettings;

public class CandidateManager {

    private ArrayList<Candidate> candidates = new ArrayList<>();

    public CandidateManager(VoteSettings settings) {
        readInCandidates(settings);
    }

    // Only used to insert dummy data into database
    private void insertCandidates() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference candidateRef = db.child("candidates");

        Candidate trump = new Candidate("Donald Trump", 1, false, 0);
        Candidate hilary = new Candidate("Hilary Clinton", 2, false, 0);

        candidateRef.child("1").setValue(trump.getModel());
        candidateRef.child("2").setValue(hilary.getModel());
    }

    private void readInCandidates(final VoteSettings settings) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference candidateRef = db.child("candidates");

        candidateRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String id = snapshot.child("id").getValue().toString();
                        String name = snapshot.child("name").getValue().toString();
                        String hasWon = snapshot.child("hasWon").getValue().toString();
                        String votesReceived = snapshot.child("votesReceived").getValue().toString();

                        Candidate candidate = new Candidate(name, Integer.parseInt(id),
                                Boolean.parseBoolean(hasWon), Integer.parseInt(votesReceived));

                        candidates.add(candidate);
                     //   Log.d("candidateTesting", "id: " + id);
                     //   Log.d("candidateTesting", "name: " + name);
                     //   Log.d("candidateTesting", "hasWon: " + hasWon);
                     //   Log.d("candidateTesting", "votesReceived: " + votesReceived);
                     //   Log.d("candidateTesting", " ");
                    }
                } catch (Exception e) {
                    Log.d("candidateTesting", "NULL ERRORS");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
