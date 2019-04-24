package brandon.davison.com.voting.users;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import brandon.davison.com.voting.voting.VoteSettings;

public class CandidateManager implements PropertyChangeListener {

    private ArrayList<Candidate> candidates = new ArrayList<>();
    private CandidateEventListener listener = new CandidateEventListener();

    public CandidateManager(VoteSettings settings) {
        listener.addChangeListener(this);
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

        candidateRef.addListenerForSingleValueEvent(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Log.d("candidateTesting", "Property Changed");

        if (evt.getPropertyName().equals("name")) {
            String name = evt.getNewValue().toString();
            Log.d("candidateTesting", "name: " + name);
        }
        if (evt.getPropertyName().equals("id")) {
            String id = evt.getNewValue().toString();
            Log.d("candidateTesting", "id: " + id);
        }
        if (evt.getPropertyName().equals("hasWon")) {
            String hasWon = evt.getNewValue().toString();
            Log.d("candidateTesting", "hasWon: " + hasWon);
        }
        if (evt.getPropertyName().equals("votesReceived")) {
            String votesReceived = evt.getNewValue().toString();
            Log.d("candidateTesting", "votesReceived: " + votesReceived);
        }
    }

    private void addCandidate(Candidate newCandidate) {
        candidates.add(newCandidate);
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

}
